package com.notify.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.notify.entity.NotificationRequest;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class NotificationService {

	@Autowired
	private JavaMailSenderImpl javaMailSenderImpl;
	
	
	private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
	
//	@Value("${17343612819}")
//	private String twilioPhoneNumber;

//	public String sendNotification(NotificationRequest notificationRequest) {
//		try {
//			SimpleMailMessage message = new SimpleMailMessage();
//			message.setTo(notificationRequest.getTo());
//			message.setSubject(notificationRequest.getSubject());
//			message.setText(notificationRequest.getBody());
//			javaMailSenderImpl.send(message);
//			return "notification sent successfully!";
//		} catch (Exception e) {
//			return "Failed to send notification:" + e.getMessage();
//		}
//
//	}
	   @Value("${twilio.phone-number}")
	    private String twilioPhoneNumber;

	   public String sendNotification(NotificationRequest notificationRequest) {
		   
		   //implementing Logger 
		   logger.info(" + Sending {} notification to :{}",notificationRequest.getNotificationType(),notificationRequest.getTo());
		    try {
		        if (notificationRequest.getNotificationType() == null || 
		        		notificationRequest.getNotificationType().isEmpty()) {
		            notificationRequest.setNotificationType("EMAIL");
		        }
		        switch (notificationRequest.getNotificationType().toUpperCase()) {
		            case "EMAIL":
		                return sendEmail(notificationRequest);
		            case "SMS":
		                return sendSMS(notificationRequest);
		            default:
		                return "Unsupported notification type: " + notificationRequest.getNotificationType();
		        }
		    } catch (Exception e) {
		        return "Failed to send notification: " + e.getMessage();
		    }
		}

	    private String sendEmail(NotificationRequest notificationRequest) {
	        try {
	            SimpleMailMessage message = new SimpleMailMessage();
	            message.setTo(notificationRequest.getTo());
	            message.setSubject(notificationRequest.getSubject());
	            message.setText(notificationRequest.getBody());
	            javaMailSenderImpl.send(message);
	            logger.info("Email sent to {}", notificationRequest.getTo());
	            return "Email notification sent successfully!";
	        } catch (Exception e) {
	        	logger.error("Failed to send email to {}: {} ",notificationRequest.getTo(),e.getMessage(),e);
	            return "Failed to send email notification: " + e.getMessage();
	        }
	    }

	    private String sendSMS(NotificationRequest notificationRequest) {
	        try {
	            Message.creator(
	                new PhoneNumber(notificationRequest.getTo()), // Recipient's phone number
	                new PhoneNumber(twilioPhoneNumber), // Your Twilio phone number
	                notificationRequest.getBody() // Message body
	            ).create();
	            logger.info("SMS sent to {}", notificationRequest.getTo());
	            return "SMS notification sent!";
	        } catch (Exception e) {
	        	logger.error("Failed to send email to {}: {} ",notificationRequest.getTo(),e.getMessage(),e);
	            return "Failed to send SMS notification: " + e.getMessage();
	        }
	    }
	
	
}
