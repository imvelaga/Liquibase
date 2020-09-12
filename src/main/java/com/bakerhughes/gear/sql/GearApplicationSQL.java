package com.bakerhughes.gear.sql;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GearApplicationSQL {

	static GearSQLMail gearSQLMail = new GearSQLMail();

	private static final Logger logger = Logger.getLogger(GearApplicationSQL.class);

	public static void main(String[] args) throws AddressException, MessagingException {
		String triggerStatus = null;
		try {
			SpringApplication.run(GearApplicationSQL.class, args);
			triggerStatus = "Succuss";
			logger.info("Log: gear-SQL project execution completed in " + System.getenv("Environment") + " Environment");
			gearSQLMail.sendEmail("Completed", System.getenv("Environment"), "All ChangeSets configured has been executed successfully");
		} catch (Throwable tx) {
			triggerStatus = tx.getMessage();
			logger.info("Log: gear-SQL project execution failed in " + System.getenv("Environment") + " Environment");
			gearSQLMail.sendEmail("Failed", System.getenv("Environment"), triggerStatus);
		}
	}

}
