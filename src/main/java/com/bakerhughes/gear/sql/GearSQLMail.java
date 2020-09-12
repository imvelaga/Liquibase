package com.bakerhughes.gear.sql;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

public class GearSQLMail {

	private static final Logger logger = Logger.getLogger(GearSQLMail.class);

	public Properties getAppPropFileContent() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		java.util.Properties properties = new java.util.Properties();
		try (InputStream resourceStream = loader.getResourceAsStream("application.properties")) {
			properties.load(resourceStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	public void sendEmail(String status, String hostName, String reportMessage) {

		Properties environment = getAppPropFileContent();

		String to = environment.getProperty("spring.mail.to");
		String from = environment.getProperty("spring.mail.from");
		String host = environment.getProperty("spring.mail.host");
		String port = environment.getProperty("spring.mail.port");
		String authEnable = environment.getProperty("spring.mail.properties.mail.smtp.auth");
		String tlsEnable = environment.getProperty("spring.mail.properties.mail.smtp.starttls.enable");

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", port);
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.auth", authEnable);
		properties.setProperty("mail.smtp.starttls.enable", tlsEnable);
		properties.setProperty("mail.debug", "false");
		Session session = Session.getDefaultInstance(properties);

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//			message.addRecipients(Message.RecipientType.CC, "shankar.velaga@gmail.com");
			message.setSubject("GEAR-SQL: CI/CD report in " + hostName);

			MimeMultipart multipart = new MimeMultipart("related");
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(getMessage(status, hostName, reportMessage), "text/html");
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport.send(message);
			logger.info("Log: Email triggered from GearSQLMail successfully");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	public static String getMessage(String status, String environment, String reportMessage) {
		String textStr = null;
		try {
			
			StringBuilder text = new StringBuilder();
			text.append("<body style='font-family: segoe ui symbol'><div>Hi! <br/><br/>Your request for GEAR-"
					+ "SQL: Execution of scripts using Liquibase has been <b>" + status + "</b> in " + environment
					+ " Environment.<br/><br/></div>");
			text.append("<b style='font-family: segoe ui symbol'>Report Details: <br/><br/></b>");
			text.append("<div>");
			text.append(reportMessage);
			text.append("</div>");
			text.append("<br/> Regards,<br/>GEAR Automation</body></html>");
			textStr = text.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return textStr;
	}

}
