package cn.minglg.interview.auth.controller;

import cn.minglg.interview.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:UserController
 * Package:cn.minglg.interview.controller
 * Description: 处理用户
 *
 * @Author kfzx-minglg
 * @Create 2025/6/13
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final String publicKeyPem;

    @Autowired
    public UserController(UserService userService, String publicKeyPem, KeyPair keyPair) {
        this.userService = userService;
        this.publicKeyPem = publicKeyPem;
    }

    @GetMapping("/publicKey")
    public ResponseEntity<Object> getPublicKey() {
        Map<String, String> map = new HashMap<>();
        map.put("publicKey", publicKeyPem);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
/*
    @PostMapping("/aa")
    public ResponseEntity<Object> login(@RequestBody UserPayload payload, HttpServletRequest request) throws Exception {
        User user = new User();
        user.setUsername(payload.getUsername());
        user.setPasswordHash(payload.getPassword());
        user.setLastLoginIp(HttpRequestUtils.getClientIpAddress(request));
        Map<String, Object> body = this.userService.userLogin(user);
        String uuid = body.get("uuid") == null ? UUID.randomUUID().toString() : (String) body.remove("uuid");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", uuid);
        // 关键：声明暴露头
        headers.setAccessControlExposeHeaders(Collections.singletonList("Authorization"));
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserPayload payload, HttpServletRequest request) {
        User user = User.builder()
                .username(payload.getUsername())
                .passwordHash(payload.getPassword())
                .nickname(payload.getNickname() == null ? String.valueOf(UUID.randomUUID()) : payload.getNickname())
                .role(payload.getRole() == null ? UserRole.ROLE_JOB_SEEKER : payload.getRole())
                .status(UserStatus.NORMAL)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .lastLoginIp(HttpRequestUtils.getClientIpAddress(request))
                .build();
        return new ResponseEntity<>(this.userService.addUser(user), HttpStatus.OK);
    }
*/


}
