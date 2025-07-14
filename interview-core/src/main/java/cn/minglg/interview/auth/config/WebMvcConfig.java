package cn.minglg.interview.auth.config;

/**
 * ClassName:WebMvcConfig
 * Package:cn.minglg.interview.config
 * Description: 自定义WebMvcConfigurer，扩展默认功能
 *
 * @Author kfzx-minglg
 * @Create 2025/6/14
 * @Version 1.0
 */
/**
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final List<CustomInterceptor> handlerInterceptors;

    public WebMvcConfig(List<CustomInterceptor> handlerInterceptors) {
        this.handlerInterceptors = handlerInterceptors;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        this.handlerInterceptors.forEach(interceptor -> registry.addInterceptor(interceptor)
                .addPathPatterns(interceptor.getPathPatterns())
                .excludePathPatterns(interceptor.getExcludePathPatterns())
                .order(interceptor.getOrder())
        );
    }

}
 **/
