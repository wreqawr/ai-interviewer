package cn.minglg.interview.pojo;

import cn.hutool.core.annotation.PropIgnore;
import cn.minglg.interview.constant.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * ClassName:User
 * Package:cn.minglg.interview.pojo
 * Description:
 *
 * @Author kfzx-minglg
 * @Create 2025/6/13
 * @Version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails, Serializable {
    /**
     * 核心字段
     */
    private Long userId;
    private String username;
    @PropIgnore
    private String password;
    private String nickname;
    private String email;
    private UserStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * 复合字段
     */
    private Company company;
    private List<Role> roles;

    /**
     * 返回授予用户的权限。。
     *
     * @return 权限，按自然键排序
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        this.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName().toString()));
            role.getPermissions().forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getPermissionCode())));
        });
        return authorities;
    }

    /**
     * 指示用户是启用还是禁用。已禁用的用户不能通过认证。
     * 如果用户已启用，则为true，否则为false
     */
    @Override
    public boolean isEnabled() {
        return "NORMAL".equalsIgnoreCase(this.status.toString());
    }

}
