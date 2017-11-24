package com.stq.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * Describe: 属性文件工具类
 */
public class PropertiesUtil {
    private static final String DEFAULT_PROPERTIES="application.properties";
    
    private static String OS_NAME = System.getProperty("os.name").toLowerCase();
    
    /**
     * 获取properties属性值
     * @param str
     * @return
     */
    public static String prop(String str){
        try {
            Resource resource = new ClassPathResource(DEFAULT_PROPERTIES);
            Properties props = PropertiesLoaderUtils.loadProperties(resource);
            return props.getProperty(str);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * 获取本机ip和服务器ip
     * @param str
     * @return
     */
    public static String remoteAddr(){
    	String SERVER_IP = null;
    	try {
        	if (OS_NAME.contains("mac")) {
        		return "http://"+InetAddress.getLocalHost().getHostAddress()+"/download";
        	}else if(OS_NAME.contains("windows")){
        		return "http://"+InetAddress.getLocalHost().getHostAddress()+"/download";
        	}else{
                Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
                InetAddress ip = null;
                while (netInterfaces.hasMoreElements()) {
                    NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                    ip = (InetAddress) ni.getInetAddresses().nextElement();
                    SERVER_IP = ip.getHostAddress();
                    if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()&& ip.getHostAddress().indexOf(":") == -1) {
                        SERVER_IP = ip.getHostAddress();
                        break;
                    } else {
                        ip = null;
                    }
                }
                return "http://"+SERVER_IP+"/download";
        	}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
        return null;
    }
}
