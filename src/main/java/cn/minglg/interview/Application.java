package cn.minglg.interview;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ClassName:Application
 * Package:cn.minglg.interview
 * Description:
 *
 * @author kfzx-minglg
 * Create 2025/6/13
 * @version 1.0
 */
@SpringBootApplication
@MapperScan("cn.minglg.interview.auth.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
