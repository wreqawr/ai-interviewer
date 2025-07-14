package cn.minglg.interview.auth.interceptors.impl;

import cn.minglg.interview.auth.annotation.RequireLogin;
import cn.minglg.interview.auth.interceptors.CustomInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * ClassName:UserLoginCheckInterceptor
 * Package:cn.minglg.interview.interceptors
 * Description:登录状态检查
 *
 * @Author kfzx-minglg
 * @Create 2025/6/15
 * @Version 1.0
 */

@Component
public class UserLoginCheckInterceptor implements CustomInterceptor {
    private final RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    public UserLoginCheckInterceptor(RedisTemplate<Object,Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果该方法是一个普通方法，没有与请求路径映射，直接放行
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        // 获取控制器方法名和所在类名
        Method method = handlerMethod.getMethod();
        Class<?> controllerClass = method.getDeclaringClass();
        // 类级别注解
        RequireLogin classAnnotation = controllerClass.getAnnotation(RequireLogin.class);
        // 方法级别注解
        RequireLogin methodAnnotation = method.getAnnotation(RequireLogin.class);
        // 计算最终是否需要登录
        boolean requiresLogin = Optional.ofNullable(methodAnnotation)
                .map(RequireLogin::required)
                .orElseGet(() ->
                        classAnnotation != null && classAnnotation.required()
                );
        if (!requiresLogin) {
            return true;
        }
        // 执行Token验证逻辑
        String authorization = request.getHeader("Authorization");
        boolean checkResult = redisTemplate.opsForHash().hasKey("user:security:login", authorization);
        if (!checkResult) {
            response.setContentType("application/json;charset=utf-8");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "请先登录");
        }
        return checkResult;

    }

    /**
     * 返回拦截器的顺序
     *
     * @return 拦截器的顺序
     */
    @Override
    public int getOrder() {
        return 1;
    }

    /**
     * 返回需要匹配的路径模式
     *
     * @return 匹配列表
     */
    @Override
    public String[] getPathPatterns() {
        return new String[]{"/user/**"};
    }
}
