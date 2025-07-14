package cn.minglg.interview.auth.service.impl;

import cn.minglg.interview.auth.mapper.UserMapper;
import cn.minglg.interview.auth.pojo.User;
import cn.minglg.interview.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:UserServiceImpl
 * Package:cn.minglg.interview.service.impl
 * Description:
 *
 * @Author kfzx-minglg
 * @Create 2025/6/13
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final KeyPair keyPair;

    @Autowired
    public UserServiceImpl(UserMapper userMapper,
                           PasswordEncoder passwordEncoder,
                           KeyPair keyPair) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.keyPair = keyPair;
    }

/*
    @Override
    public Map<String, Object> addUser(User user) {
        Map<String, Object> resultMap = new HashMap<>();
        String message = "添加失败，无效的角色：%s！".formatted(user.getRoles().get(0).getRoleName());
        // 不允许添加管理员用户
        if (UserRole.ROLE_ADMIN == user.getRoles().get(0).getRoleName()) {
            resultMap.put("success", false);
            resultMap.put("message", message);
            return resultMap;
        }
        try {
            String decrypt = RsaUtils.decrypt(user.getPassword(), keyPair.getPrivate());
            String passwordHash = passwordEncoder.encode(decrypt.substring(0, decrypt.length() - 20));
            user.setPassword(passwordHash);
            userMapper.addUser(user);
            message = "添加成功！";
            resultMap.put("success", true);
        } catch (Exception e) {
            message = e.getMessage();
            resultMap.put("success", false);
        } finally {
            resultMap.put("message", message);
        }
        return resultMap;
    }
*/
    @Override
    public Map<String, Object> needLogin(String token, String params) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("message", params);
        if ("hh".equals(params)) {
            resultMap.put("success", false);
        } else {
            resultMap.put("success", true);
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> notNeedLogin(String params) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("message", params);
        if ("hh".equals(params)) {
            resultMap.put("success", false);
        } else {
            resultMap.put("success", true);
        }
        return resultMap;
    }

    /**
     * 根据用户名定位用户。在实际实现中，搜索
     * 可能区分大小写或不区分大小写，具体取决于
     * 已配置 implementation 实例。在这种情况下，
     * 返回的对象可能具有与 What 不同的 username 大小写
     * 实际上是被请求的..
     *
     * @param userName 标识需要其数据的用户的用户名。
     * @return 完全填充的用户记录（永不为空）
     * @throws UsernameNotFoundException 授予权限
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userMapper.getUserWithDetailsByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("登录账号不存在！");
        }
        return user;
    }

}
