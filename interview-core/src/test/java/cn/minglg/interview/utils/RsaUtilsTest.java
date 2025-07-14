package cn.minglg.interview.utils;

import cn.minglg.interview.auth.utils.RsaUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

/**
 * ClassName:RsaUtilsTest
 * Package:cn.minglg.interview.utils
 * Description:
 *
 * @Author kfzx-minglg
 * @Create 2025/6/15
 * @Version 1.0
 */
@SpringBootTest
public class RsaUtilsTest {
    @Autowired
    private KeyPair keyPair;

    @Test
    public void test1() throws NoSuchAlgorithmException {
        String encryptMessage = "liVYuhu96DTpQ7QMRLWIUgJdJzD9xou0h8qNQl7rIPmZ4Ks4np95OOt5yBkXHyLdrzMi4uiXuBCXOnp55T+9c7fQhsLiBtxWnsgsIYJkR9vMZ8ZmEBemdlRhaszRMP36yKG8ddXhVgEl6Nq/9iIB5e3gmMHfNl1j/KB+6nqaeqbOfcKcbfSMKiAV5t6HWHD3FAOiq9uaGZeV5gP8W/LGBpDEZKBnqQXHEUckxbW+voYI7Z+IO5OmWeA/WI+xH7MhV1o6f41SR+mu1gZiqRIXOpnk3moxG8QEUqmmC8C5rh97QcgWm6e8bohLHOchEbqL127bYQjHSWMgd6ZbkWuO0g==";
        String decryptMessage;
        String decryptPassword;
        long requestTimestamp;
        try {
            decryptMessage = RsaUtils.decrypt(encryptMessage, keyPair.getPrivate());
            decryptPassword = decryptMessage.substring(0, decryptMessage.length() - 20);
            requestTimestamp = Long.parseLong(decryptMessage.substring(decryptMessage.length() - 20));
            System.out.println(decryptMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void test2() {
        String password = passwordEncoder.encode("Tom123");
        System.out.println(password);
    }
}
