package cn.minglg.interview.utils;

import jakarta.servlet.http.HttpServletRequest;

/**
 * ClassName:HttpRequestUtils
 * Package:cn.minglg.interview.utils
 * Description:
 *
 * @Author kfzx-minglg
 * @Create 2025/6/15
 * @Version 1.0
 */
public class HttpRequestUtils {
    /**
     * 获取客户端真实IP（支持代理服务器）
     * 检查顺序:
     * 1. X-Forwarded-For (标准代理头)
     * 2. Proxy-Client-IP (Apache代理)
     * 3. WL-Proxy-Client-IP (WebLogic代理)
     * 4. HTTP_CLIENT_IP (可选代理头)
     * 5. 直接连接时的RemoteAddr
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        String[] headersToCheck = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR"
        };

        for (String header : headersToCheck) {
            String ip = request.getHeader(header);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                // 处理多个IP的情况 (代理链)
                return parseFirstIp(ip);
            }
        }

        // 没有代理头信息时使用默认方法
        return request.getRemoteAddr();
    }

    /**
     * 解析出第一个有效的IP地址（处理逗号分隔的多IP情况）
     */

    private static String parseFirstIp(String ipHeaderValue) {
        if (ipHeaderValue.contains(",")) {
            String[] ips = ipHeaderValue.split(",");
            for (String ip : ips) {
                String cleanIp = ip.trim();
                if (!cleanIp.isEmpty() && !"unknown".equalsIgnoreCase(cleanIp)) {
                    return cleanIp;
                }
            }
        }
        return ipHeaderValue.trim();
    }
}
