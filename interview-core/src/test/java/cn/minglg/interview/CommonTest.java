package cn.minglg.interview;

import org.junit.jupiter.api.Test;

/**
 * ClassName:CommonTest
 * Package:cn.minglg.interview
 * Description:
 *
 * @Author kfzx-minglg
 * @Create 2025/6/22
 * @Version 1.0
 */
public class CommonTest {

    @Test
    public void test1() {
        int totalLength = 20;
        long current = System.currentTimeMillis();
        String s = String.valueOf(current);
        String padded = "0".repeat(Math.max(0, totalLength - s.length())) + s;
        System.out.println(padded);
        System.out.println(padded.length());
    }
}
