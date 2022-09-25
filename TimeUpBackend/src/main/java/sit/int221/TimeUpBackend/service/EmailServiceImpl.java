package sit.int221.TimeUpBackend.service;

import java.io.File;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.TimeUpBackend.dtos.EventPostDto;
import sit.int221.TimeUpBackend.entities.EmailDetails;
import sit.int221.TimeUpBackend.entities.EventCategory;
import sit.int221.TimeUpBackend.repositories.EventCategoryRepository;

@Service
// Class
// Implementing EmailService interface
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EventCategoryRepository eventCategoryRepository;
    @Value("${spring.mail.username}")
    private String sender;
    private static final String PATTERN_FORMAT = "EEE, d MMM yyyy HH:mm Z";
    String messageSubject = "Accepted: meeting Event ";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT).withZone(ZoneId.systemDefault());
    // Method 1
    // To send a simple email
    @Override
    public String sendSimpleMail(EventPostDto event) {
        UserDetails getCurrentAuthentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EventCategory eventCategory = eventCategoryRepository.findById(event.getEventCategory().getEventCategoryId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
         Instant getEventEndTime = event.getEventStartTime().plusMillis(eventCategory.getEventDuration() * 60000);
        // Try block to check for exceptions
            String startTime = formatter.format(event.getEventStartTime());
            String endTime = formatter.format(getEventEndTime);
        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(getCurrentAuthentication.getUsername());
            mailMessage.setText("Name : " + "\t" + event.getBookingName() + "\n"
                            + "Category : "  + eventCategory.getEventCategoryName() + "\n"
                            + "Email : " + "\t" + getCurrentAuthentication.getUsername()  + "\n"
                            + "Start time : " + "\t" + startTime + "\n"
                            + "End time : " + "\t" + endTime
            );
            mailMessage.setSubject(messageSubject + "\t" + startTime + " - " + endTime );

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    // Method 2
    // To send an email with attachment
    @Override
    public String sendMailWithAttachment(EventPostDto event) {
        UserDetails getCurrentAuthentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EventCategory eventCategory = eventCategoryRepository.findById(event.getEventCategory().getEventCategoryId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Instant getEventEndTime = event.getEventStartTime().plusMillis(eventCategory.getEventDuration() * 60000);
        // Try block to check for exceptions
        String startTime = formatter.format(event.getEventStartTime());
        String endTime = formatter.format(getEventEndTime);
        // Creating a mime message
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        String pathPicCat = "../timeupFrontend/public/images/card.png";
        try {
            Date date = new Date();
            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(getCurrentAuthentication.getUsername());
            mimeMessageHelper.setText("Booking name : "  + event.getBookingName() + "<br>"
                    + "Category : "  + eventCategory.getEventCategoryName()  + "<br>"
                    + "Email : " + getCurrentAuthentication.getUsername()   + "<br>"
                    + "Start time : "  + startTime  + "<br>"
                    + "End time : "  + endTime  + "<br>" + "<br>"
                    + "<img src=\"https://scontent-hkt1-2.xx.fbcdn.net/v/t39.30808-6/309118887_5158619637581103_1956758466557752512_n.jpg?_nc_cat=106&ccb=1-7&_nc_sid=730e14&_nc_ohc=H1-vi4JJmEQAX-C-Msp&_nc_oc=AQnR6OuJhg0Pwd-0CjWo0vUl-X5YSE1JSQvwbZV1XwZoGULi54O-O46LSiaHhEbngOM&_nc_ht=scontent-hkt1-2.xx&oh=00_AT9AIwMetUyuVKKAtWq5Uyrk8nGA2BXFy_tIx6am3KPJ-w&oe=6334C7A9\" height=\"150px\"></img>" , true
            );
            mimeMessageHelper.setSubject(messageSubject + "\t" + startTime + " - " + endTime );
            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }

        // Catch block to handle MessagingException
        catch (MessagingException e) {

            // Display message when exception occurred
            return "Error while sending mail!!!";
        }
    }
}