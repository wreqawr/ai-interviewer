package cn.minglg.interview.utils;

import cn.minglg.interview.auth.pojo.User;
import cn.minglg.interview.auth.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.KeyPair;

/**
 * ClassName:JWTUtilsTest
 * Package:cn.minglg.interview.utils
 * Description:
 *
 * @Author kfzx-minglg
 * @Create 2025/7/18
 * @Version 1.0
 */
@SpringBootTest
public class JWTUtilsTest {

    User user = User.builder().userId(111L).username("张三").password("123456").build();
    @Autowired
    private KeyPair keyPair;

    @Test
    public void test1() {
        // 创建JWT
        String token = JwtUtils.createJwt(user, 100000, keyPair);
        System.out.println(token);

        User result = JwtUtils.verifyJwt(token, keyPair);
        System.out.println(result);
    }

    @Test
    public void test2() {
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiIyODIwOTk2MDYzQHFxLmNvbSIsImlhdCI6MTc1MjgwOTIxNywiZXhwIjoxNzU4ODA5MjE3LCJjbGFpbXMiOiJ7XCJ1c2VySWRcIjoxMTEsXCJ1c2VybmFtZVwiOlwi5byg5LiJXCJ9In0.fQl_BFJh7ee6N3kIopbl2XbS3pGdVbZAimtqq7mLGgPBS7Pi9h6cHmPiwYOHpbidhVUI_91yPb5uFoO4qgxOni2kTNylDsLeLX0H5XXInoleM-ET7TnfsXUbUwZiC7JetNNLuXRMgJ67yZxEJBnHlk0bSl5hRdj37C6oA36YJENWSPDBmaxAKm8vlq71EHl5g-yuvFZUDqOs2ZGUozp8PoOU3EoRh6GU1bhyIzCxUHn4n_OtqtuJkCemOZM28Pcy_fmuahBJhiMc4aifOwJiJUXJqZA-bR90KoK0LoPXggVGGQFaQe0LDIuEUWbir4di9TFhPfes0htZmrA4WEUSSA";
        System.out.println(JwtUtils.verifyJwt(token, keyPair));
    }
}
