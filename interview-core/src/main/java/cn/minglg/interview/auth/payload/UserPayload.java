package cn.minglg.interview.auth.payload;

import cn.minglg.interview.auth.constant.UserRole;
import cn.minglg.interview.auth.constant.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * ClassName:UserPayload
 * Package:cn.minglg.interview.payload
 * Description:
 *
 * @Author kfzx-minglg
 * @Create 2025/6/14
 * @Version 1.0
 */
@Data
public class UserPayload {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private UserRole role;
    private UserStatus status;

    /**
     * 时间字段
     */
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;

    /**
     * 地址
     */
    private String lastLoginIp;
}
