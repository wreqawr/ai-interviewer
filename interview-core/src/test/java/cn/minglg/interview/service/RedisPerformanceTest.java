package cn.minglg.interview.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisPerformanceTest {

    @Autowired
    private RedisService redisService;

    private static final int TOTAL_KEYS = 1_000_000_0;
    private static final int THREAD_COUNT = 20;
    private static final int BATCH_SIZE = 1000;  // 每批处理100个键
    private static final String KEY_PREFIX = "key_";

    private final List<Object> allKeys = new ArrayList<>();

    /**
     * 批量化插入数据
     *
     * @param threadIndex 线程索引
     */
    public void insertBatch(int threadIndex) {
        int keysPerThread = TOTAL_KEYS / THREAD_COUNT;
        int start = threadIndex * keysPerThread;

        try {
            for (int batchStart = start; batchStart < start + keysPerThread; batchStart += BATCH_SIZE) {
                Map<String, String> batch = new HashMap<>(BATCH_SIZE);

                // 构建批次数据
                for (int j = 0; j < BATCH_SIZE && batchStart + j < start + keysPerThread; j++) {
                    String key = KEY_PREFIX + (batchStart + j + 1);
                    String value = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
                    batch.put(key, value);
                    synchronized (allKeys) {
                        allKeys.add(key);  // 记录键用于清理
                    }
                }

                // 批量写入Redis
                if (!batch.isEmpty()) {
                    redisService.mset(batch);
                }
            }
        } catch (Exception e) {
            System.err.println("线程 " + threadIndex + " 执行失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 多线程写入测试
     */
    @Test
    public void testBatchSet() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            int threadIndex = i;
            executor.execute(() -> {
                insertBatch(threadIndex);
                latch.countDown();
            });
        }

        latch.await();  // 等待所有线程完成
        executor.shutdown();
        System.out.println("成功插入 " + allKeys.size() + " 个键值对");
    }

    /**
     * 测试后清理数据
     */
    public void cleanup() {
        if (allKeys.isEmpty()) return;

        System.out.println("清理测试数据...");
        // 批量删除键
        redisService.del(allKeys);
        allKeys.clear();
    }
}