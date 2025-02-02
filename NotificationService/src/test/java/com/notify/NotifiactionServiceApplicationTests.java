package com.notify;


import com.notify.entity.NotificationRequest;
import com.notify.service.NotificationService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class NotifiactionServiceApplicationTests {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private JavaMailSenderImpl javaMailSenderImpl;

    @Mock
    private Message mockTwilioMessage;

    private NotificationRequest notificationRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup a basic NotificationRequest object
        notificationRequest = new NotificationRequest();
        notificationRequest.setTo("test@example.com");
        notificationRequest.setSubject("Test Subject");
        notificationRequest.setBody("This is a test message.");
    }

    @Test
    void testSendEmailNotificationSuccess() {
        // Mock the behavior of JavaMailSender
        doNothing().when(javaMailSenderImpl).send(any(SimpleMailMessage.class));

        // Update type to EMAIL
        notificationRequest.setNotificationType("EMAIL");

        String result = notificationService.sendNotification(notificationRequest);

        // Verify interactions and assert result
        verify(javaMailSenderImpl, times(1)).send(any(SimpleMailMessage.class));
        assertEquals("Email notification sent successfully!", result);
    }

    @Test
    void testSendEmailNotificationFailure() {
        // Simulate an exception when sending email
        doThrow(new RuntimeException("Email service is down")).when(javaMailSenderImpl).send(any(SimpleMailMessage.class));

        // Update type to EMAIL
        notificationRequest.setNotificationType("EMAIL");

        String result = notificationService.sendNotification(notificationRequest);

        // Verify interactions and assert result
        verify(javaMailSenderImpl, times(1)).send(any(SimpleMailMessage.class));
        assertEquals("Failed to send email notification: Email service is down", result);
    }

 
  
    @Test
    void testUnsupportedNotificationType() {
        // Set an unsupported notification type
        notificationRequest.setNotificationType("PUSH");

        String result = notificationService.sendNotification(notificationRequest);

        // Assert result
        assertEquals("Unsupported notification type: PUSH", result);
    }

    @Test
    void testDefaultNotificationType() {
        // Don't set a notification type
        notificationRequest.setNotificationType(null);

        // Mock email sending
        doNothing().when(javaMailSenderImpl).send(any(SimpleMailMessage.class));

        String result = notificationService.sendNotification(notificationRequest);

        // Verify email was sent and assert result
        verify(javaMailSenderImpl, times(1)).send(any(SimpleMailMessage.class));
        assertEquals("Email notification sent successfully!", result);
    }
}