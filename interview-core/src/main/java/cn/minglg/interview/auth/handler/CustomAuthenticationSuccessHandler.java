package cn.minglg.interview.handler;

import cn.hutool.json.JSONUtil;
import cn.minglg.interview.pojo.User;
import cn.minglg.interview.properties.GlobalProperties;
import cn.minglg.interview.response.R;
import cn.minglg.interview.utils.JwtUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.KeyPair;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:CustomAuthenticationSuccessHandler
 * Package:cn.minglg.interview.handler
 * Description:
 *
 * @Author kfzx-minglg
 * @Create 2025/7/10
 * @Version 1.0
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final KeyPair keyPair;
    private final RedisTemplate<Object, Object> redisTemplate;
    private final GlobalProperties globalProperties;

    public CustomAuthenticationSuccessHandler(KeyPair keyPair, RedisTemplate<Object, Object> redisTemplate, GlobalProperties globalProperties) {
        this.keyPair = keyPair;
        this.redisTemplate = redisTemplate;
        this.globalProperties = globalProperties;
    }

    /**
     * 在用户成功通过身份验证时调用。
     *
     * @param request        导致身份验证成功的请求
     * @param response       响应
     * @param authentication 身份验证
     *                       身份验证过程。
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        User user = (User) authentication.getPrincipal();
        // 生成JWT令牌
        long expiration = globalProperties.getJwtExpirationMinutes();
        String securityKey = globalProperties.getSecurityKey();
        String token = JwtUtils.createJwt(user, expiration, keyPair);
        // 登录信息保存至redis
        redisTemplate.expire(securityKey, expiration, TimeUnit.MINUTES);
        redisTemplate.opsForHash().put(securityKey, user.getUserId(), token);
        R result = R.builder().code(200).message("登录成功，欢迎：" + user.getUsername()).data(token).build();
        response.getWriter().write(JSONUtil.toJsonStr(result));
    }
}
