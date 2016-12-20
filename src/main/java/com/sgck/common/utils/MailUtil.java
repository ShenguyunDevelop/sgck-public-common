package com.sgck.common.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil 
{
//	   public static int port = 25;
//
//	   public static String server = "smtp.exmail.qq.com";//邮件服务器
//
//	    //static String from = "smtp.exmail.qq.com";//发送者
//	   public static String from = "xiadan@shenguyun.com";//发送者
//
//	   public static String user = "xiadan@shenguyun.com";//发送者地址
//
//	   public static String password = "";//密码

	   public static void sendEmail(String server,String from,String user,String password,
			   String email, String subject, String body) {
	        try {
	            Properties props = new Properties();
//	            props.put("mail.smtp.host", server);
//	            props.put("mail.smtp.port", String.valueOf(port));
//	            props.put("mail.smtp.auth", "true");
	            Transport transport = null;
	            Session session = Session.getDefaultInstance(props, null);
	            transport = session.getTransport("smtp");
	            transport.connect(server, user, password);
	            MimeMessage msg = new MimeMessage(session);
	            msg.setSentDate(new Date());
	            InternetAddress fromAddress = new InternetAddress(from);
	            msg.setFrom(fromAddress);
	            InternetAddress[] toAddress = new InternetAddress[1];
	            toAddress[0] = new InternetAddress(email);
	            msg.setRecipients(Message.RecipientType.TO, toAddress);
	            msg.setSubject(subject, "UTF-8");    
	            msg.setText(body, "UTF-8");
	            msg.saveChanges();
	            transport.sendMessage(msg, msg.getAllRecipients());
	        } catch (NoSuchProviderException e) {
	            e.printStackTrace();
	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public static void main(String args[])
	    {
	    	sendEmail("smtp.exmail.qq.com","xiadan@shenguyun.com","xiadan@shenguyun.com",
	    			"yishh0914","xiadan2005@163.com","javasendemailtest","hello xiadan");
	    }

	

}
