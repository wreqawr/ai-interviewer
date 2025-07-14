package cn.minglg.interview.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

/**
 * ClassName:UserService
 * Package:cn.minglg.interview.service
 * Description:
 *
 * @Author kfzx-minglg
 * @Create 2025/6/13
 * @Version 1.0
 */
public interface UserService extends UserDetailsService {

    /**
     * 用户登录
     *
     * @param user 用户信息
     * @return 登录结果
     */
    //Map<String, Object> userLogin(User user) throws Exception;

    /**
     * 添加用户
     *
     * @param user 用户信息
     * @return 执行结果
     */
    //Map<String, Object> addUser(User user);

    Map<String, Object> needLogin(String token, String params);

    Map<String, Object> notNeedLogin(String params);
}
