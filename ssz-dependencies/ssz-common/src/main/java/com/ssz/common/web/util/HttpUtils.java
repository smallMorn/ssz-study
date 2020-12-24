//package com.ssz.common.web.util;
//
//
//import lombok.extern.slf4j.Slf4j;
//
//import javax.servlet.http.HttpServletRequest;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//
//@Slf4j
//public class HttpUtils {
//
//    public static final String HEADER_AUTHORIZATION = "Authorization";
//    public static final String HEADER_DEVICE_CODE = "DeviceCode";
//
//    /**
//     * 获取客户端的IP地址
//     *
//     * @return
//     */
//    public static String getIpAddr(HttpServletRequest request) {
//        String ipAddress = StringUtils.EMPTY;
//        ipAddress = request.getHeader("x-forwarded-for");
//        if (StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
//            ipAddress = request.getHeader("Proxy-Client-IP");
//        }
//        if (StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
//            ipAddress = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
//            ipAddress = request.getRemoteAddr();
//            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
//                // 根据网卡取本机配置的IP
//                InetAddress inet = null;
//                try {
//                    inet = InetAddress.getLocalHost();
//                } catch (UnknownHostException e) {
//                    log.error("未知主机", e);
//                }
//                ipAddress = inet.getHostAddress();
//            }
//
//        }
//
//        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
//        if (StringUtils.isNotBlank(ipAddress) && ipAddress.length() > 15) {
//            if (ipAddress.indexOf(StringUtils.COMMA_EN) > 0) {
//                ipAddress = ipAddress.substring(0, ipAddress.indexOf(StringUtils.COMMA_EN));
//            }
//        }
//        return ipAddress;
//    }
//
//}
