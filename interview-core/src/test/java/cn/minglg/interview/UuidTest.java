import cn.minglg.interview.mapper.UserMapper;
import cn.minglg.interview.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * ClassName:UuidTest
 * Package:PACKAGE_NAME
 * Description:
 *
 * @Author kfzx-minglg
 * @Create 2025/6/14
 * @Version 1.0
 */
@SpringBootTest
public class UuidTest {
    @Test
    public void test1() {
        for (int i = 0; i < 10; i++) {
            UUID uuid = UUID.randomUUID();
            System.out.println(uuid);
        }

    }

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test2() {
        User user = new User();
        user.setUsername("test");
        user.setPasswordHash("123456");
        user.setNickname("testAAAA");
        System.out.println(userMapper.addUser(user));
    }
}
