package cn.minglg.interview.auth.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * ClassName:CustomInterceptor
 * Package:cn.minglg.interview.interceptors
 * Description: 创建自定义拦截器接口，并扩展功能，可获取顺序、匹配路径、排除路径等
 *
 * @Author kfzx-minglg
 * @Create 2025/6/19
 * @Version 1.0
 */
public interface CustomInterceptor extends HandlerInterceptor {
    /**
     * 返回拦截器的顺序
     *
     * @return 拦截器的顺序
     */
    int getOrder();

    /**
     * 返回需要匹配的路径模式
     *
     * @return 匹配列表
     */
    String[] getPathPatterns();

    /**
     * 返回需要排除的路径模式
     *
     * @return 排除列表
     */
    default String[] getExcludePathPatterns() {
        return new String[0];
    }

}
