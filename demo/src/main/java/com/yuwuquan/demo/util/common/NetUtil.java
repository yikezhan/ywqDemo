package com.yuwuquan.demo.util.common;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetUtil {

	private static Set<String> noIpAddress = new HashSet<>();
	
	static{
		noIpAddress.add("127.0.0.1");
		noIpAddress.add("192.168.56.1");
	}
	
	public static String getLocalIp() {
		
		String localIP="127.0.0.1";
		try {
			Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
			while (enumeration.hasMoreElements()) {
			    NetworkInterface networkInterface = enumeration.nextElement();
			    if (networkInterface.isUp()) {
			        Enumeration<InetAddress> addressEnumeration = networkInterface.getInetAddresses();
			        while (addressEnumeration.hasMoreElements()) {
			        	InetAddress address = addressEnumeration.nextElement();
			        	if(address instanceof Inet4Address){
			        		localIP = address.getHostAddress();
			        		if(!noIpAddress.contains(localIP)){
			        			return localIP;
			        		}
			        	}
			        }
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return localIP;
	}

	/**
	 * 解析出url请求的路径，包括页面
	 * 
	 * @param strURL
	 *            url地址
	 * @return url路径
	 */
	public static String urlPage(String strURL) {
		String strPage = null;
		String[] arrSplit = null;

		strURL = strURL.trim().toLowerCase();

		arrSplit = strURL.split("[?]");
		if (strURL.length() > 0) {
			if (arrSplit.length > 1) {
				if (arrSplit[0] != null) {
					strPage = arrSplit[0];
				}
			}
		}

		return strPage;
	}

	/**
	 * 去掉url中的路径，留下请求参数部分
	 * 
	 * @param strURL
	 *            url地址
	 * @return url请求参数部分
	 */
	private static String truncateUrlPage(String strURL) {
		String strAllParam = null;
		String[] arrSplit = null;

		strURL = strURL.trim().toLowerCase();

		arrSplit = strURL.split("[?]");
		if (strURL.length() > 1) {
			if (arrSplit.length > 1) {
				if (arrSplit[1] != null) {
					strAllParam = arrSplit[1];
				}
			}
		}

		return strAllParam;
	}

	/**
	 * 解析出url参数中的键值对 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
	 * 
	 * @param URL
	 *            url地址
	 * @return url请求参数部分
	 */
	public static Map<String, String> parseURLRequest(String URL) {
		Map<String, String> mapRequest = new HashMap<String, String>();

		String[] arrSplit = null;

		String strUrlParam = truncateUrlPage(URL);
		if (strUrlParam == null) {
			return mapRequest;
		}
		// 每个键值为一组 www.2cto.com
		arrSplit = strUrlParam.split("[&]");
		for (String strSplit : arrSplit) {
			String[] arrSplitEqual = null;
			arrSplitEqual = strSplit.split("[=]");

			// 解析出键值
			if (arrSplitEqual.length > 1) {
				// 正确解析
				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

			} else {
				if (arrSplitEqual[0] != "") {
					// 只有参数没有值，不加入
					mapRequest.put(arrSplitEqual[0], "");
				}
			}
		}
		return mapRequest;
	}
	
	public static String getHttpReferer(HttpServletRequest request) {
		if(request == null){
			return "";
		}else{
			String result = request.getHeader("Referer");
	    	if(result == null){
	    		return "";
	    	}else{
	    		return result;
	    	}
		}
    }
	
	/**
	 * 判断是否是局域网地址
	 * @param ip
	 * @return
	 */
	public static boolean isLocalAreaNet(String ip) {
		String reg = "(10|172|192)\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})";//正则表达式=。 =、懒得做文字处理了、
	    Pattern p = Pattern.compile(reg);
	    Matcher matcher = p.matcher(ip);
	    return matcher.find();
	}
   
}
