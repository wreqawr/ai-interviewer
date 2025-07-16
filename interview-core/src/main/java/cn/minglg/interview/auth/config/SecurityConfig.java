package cn.minglg.interview.auth.config;

import cn.minglg.interview.auth.filter.CustomAuthenticationFilter;
import cn.minglg.interview.auth.filter.JwtTokenFilter;
import cn.minglg.interview.auth.handler.CustomAuthenticationFailureHandler;
import cn.minglg.interview.auth.handler.CustomAuthenticationSuccessHandler;
import cn.minglg.interview.auth.properties.GlobalProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.security.KeyPair;
import java.util.List;

/**
 * ClassName:SecurityConfig
 * Package:cn.minglg.interview.config
 * Description:
 *
 * @Author kfzx-minglg
 * @Create 2025/6/16
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final KeyPair keyPair;
    private final GlobalProperties globalProperties;
    private final JwtTokenFilter jwtTokenFilter;

    public SecurityConfig(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler,
                          CustomAuthenticationFailureHandler customAuthenticationFailureHandler,
                          KeyPair keyPair, GlobalProperties globalProperties,
                          JwtTokenFilter jwtTokenFilter) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
        this.keyPair = keyPair;
        this.globalProperties = globalProperties;
        this.jwtTokenFilter = jwtTokenFilter;
    }


    @Bean
    public CorsConfigurationSource configurationSource() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();

        //跨域配置
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //允许任何来源，http://localhost:10492/
        corsConfiguration.setAllowedOrigins(List.of("*"));
        //允许任何请求方法，post、get、put、delete
        corsConfiguration.setAllowedMethods(List.of("*"));
        //允许任何的请求头 (jwt)
        corsConfiguration.setAllowedHeaders(List.of("*"));

        //注册跨域配置
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter(AuthenticationManager authenticationManager) {
        String loginUri = this.globalProperties.getLoginUri();
        long timeoutSeconds = this.globalProperties.getTimeoutSeconds();
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter(loginUri, timeoutSeconds, keyPair, authenticationManager);
        filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
        return filter;
    }


    /**
     * 配置 Spring Security 放行所有请求，前后端分离防止302重定向
     *
     * @param http
     * @return
     */
    @Bean("securityFilterChain")
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomAuthenticationFilter customAuthenticationFilter, CorsConfigurationSource configurationSource) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(globalProperties.getWhiteListPatterns().toArray(new String[0]))
                        .permitAll()  // 白名单内请求，无需认证
                        .anyRequest().authenticated() // 其他所有请求走认证
                )
                // 关闭CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // 允许跨域（后续使用nginx反向代理无需配置跨域）
                .cors(cors -> {
                    cors.configurationSource(configurationSource);
                })
                // 前后端分离，无需session
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 自定义认证过滤器，替换框架默认的UsernamePasswordAuthenticationFilter
                .addFilterAt(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // 关键位置：在 SecurityContextHolderFilter之前添加jwtTokenFilter
                .addFilterBefore(jwtTokenFilter, SecurityContextHolderFilter.class)
                .build();
    }

    /**
     * 创建BCrypt密码编码器
     */
    @Bean("passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
