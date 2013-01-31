package com.awrank.web.model.utils.emailauthentication;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.security.MessageDigest;

public class SMTPAuthenticator extends Authenticator {
	
	private String username;
	private String password;
	
	public SMTPAuthenticator(String username, String password){
	
		this.username = username;
		this.password = password;
	}
	
	public PasswordAuthentication getPasswordAuthentication() {
		
		   return new PasswordAuthentication(username, password);
	}
	
	public static String getHashed256(String source) throws Exception{
		
		 MessageDigest md = MessageDigest.getInstance("SHA-256");
		 md.update(source.getBytes());
		 
		 byte byteData[] = md.digest();
		 
	        //convert the byte to hex format method 1
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	 
	        System.out.println("Hex format : " + sb.toString());
	        
	        return sb.toString();
	        /*
	        //convert the byte to hex format method 2
	        StringBuffer hexString = new StringBuffer();
	    	for (int i=0;i<byteData.length;i++) {
	    		String hex=Integer.toHexString(0xff & byteData[i]);
	   	     	if(hex.length()==1) hexString.append('0');
	   	     	hexString.append(hex);
	    	}
	    	
	    	return  hexString.toString();
	    	*/
	}

}
