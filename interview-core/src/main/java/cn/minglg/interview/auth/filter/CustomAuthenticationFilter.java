package cn.minglg.interview.auth.filter;

import cn.minglg.interview.auth.exception.InvalidUsernameOrPasswordException;
import cn.minglg.interview.utils.RsaUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.KeyPair;
import java.util.Map;

/**
 * ClassName:CustomAuthenticationFilter
 * Package:cn.minglg.interview.filter
 * Description:自定义用户名密码过滤器
 *
 * @Author kfzx-minglg
 * @Create 2025/7/10
 * @Version 1.0
 */
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final String REQUEST_METHOD = "POST";
    private final long timeoutSeconds;
    private final KeyPair keyPair;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomAuthenticationFilter(String loginUri, long timeoutSeconds, KeyPair keyPair, AuthenticationManager authenticationManager) {
        this.timeoutSeconds = timeoutSeconds;
        this.keyPair = keyPair;
        setAuthenticationManager(authenticationManager);
        setFilterProcessesUrl(loginUri);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!REQUEST_METHOD.equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        // 从JSON请求体中解析用户名和密码
        Map<String, String> authData = null;
        try {
            authData = objectMapper.readValue(request.getInputStream(), Map.class);
        } catch (Exception e) {
            throw new AuthenticationServiceException("Failed to parse authentication request body");
        }
        String username = authData.get("username");
        username = (username != null) ? username.trim() : "";
        String encryptMessage = authData.get("password");
        encryptMessage = (encryptMessage != null) ? encryptMessage : "";
        String decryptMessage;
        String decryptPassword;
        long requestTimestamp;

        try {
            decryptMessage = RsaUtils.decrypt(encryptMessage, keyPair.getPrivate());
            decryptPassword = decryptMessage.substring(0, decryptMessage.length() - 20);
            requestTimestamp = Long.parseLong(decryptMessage.substring(decryptMessage.length() - 20));
        } catch (Exception e) {
            throw new InvalidUsernameOrPasswordException("用户名或密码不正确！");
        }
        long currentTimestamp = System.currentTimeMillis() / 1000;
        if (currentTimestamp - requestTimestamp > timeoutSeconds) {
            throw new InvalidUsernameOrPasswordException("用户名或密码不正确！");
        }
        UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username,
                decryptPassword);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
