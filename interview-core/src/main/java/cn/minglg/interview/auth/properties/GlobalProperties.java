package cn.minglg.interview.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ClassName:GlobalProperties
 * Package:cn.minglg.interview.properties
 * Description:
 *
 * @Author kfzx-minglg
 * @Create 2025/7/13
 * @Version 1.0
 */
@Component
@ConfigurationProperties(prefix = "global")
@Data
public class GlobalProperties {
    private String loginUri;
    private String securityKey;
    private List<String> greenChannelUri;
    private long timeoutSeconds;
    private long loginTimeoutSeconds;
    private long jwtExpirationMinutes;
}
