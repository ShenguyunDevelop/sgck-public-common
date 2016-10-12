package com.sgck.common.utils;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import com.sgck.common.log.DSLogger;

public class LocalIPAddressUtil {

	public static String getLocalIP(){
		String ip;
	    try {
	        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
	        while (interfaces.hasMoreElements()) {
	            NetworkInterface iface = interfaces.nextElement();
	            // filters out 127.0.0.1 and inactive interfaces
	            /*if (iface.isLoopback() || !iface.isUp())
	                continue;*/

	            Enumeration<InetAddress> addresses = iface.getInetAddresses();
	            while(addresses.hasMoreElements()) {
	                InetAddress addr = addresses.nextElement();
	                if (addr instanceof Inet6Address){
	                	continue;
	                }
	               
	                ip = addr.getHostAddress();
	                if(ip.startsWith("127")) {
	                	continue;
	                }

	                return ip;
	            }
	        }
	    } catch (SocketException e) {
	        //throw new RuntimeException(e);
	    	DSLogger.error("getLocalIP",e);
	    }
	    
	    return "127.0.0.1";
	}
	
	public static void main(String[] args){
		
		try{
			String localIP = LocalIPAddressUtil.getLocalIP();
			System.out.println("local ip is " + localIP);
		}catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
