package com.sgck.common.utils;

public class Base64Util {

	public static final String   BASE64_SG_CHARS   =  "fckD3V74jteRWu2+5i6slzLmFTYASygIOvh0KBX/rEJnwNbaG1pCqUZQ8M9dPoxH=";
	public static final String   BASE64_CHARS   =  "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
	
	
	public static String getSgBase64Encode(String str){
		
 		String re = "";
		for (int i=0;i<str.length();i++){
			
			int   index = BASE64_CHARS.indexOf(  str.charAt(i));
			String chStr = BASE64_SG_CHARS.substring(index, index + 1);
			re += chStr;
		}
		return re;
	}
}
