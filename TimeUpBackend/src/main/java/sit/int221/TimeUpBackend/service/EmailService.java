package sit.int221.TimeUpBackend.service;


import sit.int221.TimeUpBackend.dtos.EventDto;
import sit.int221.TimeUpBackend.dtos.EventPostDto;
import sit.int221.TimeUpBackend.dtos.UserPostDto;
import sit.int221.TimeUpBackend.entities.EmailDetails;
import sit.int221.TimeUpBackend.entities.Event;

public interface EmailService {
    // Method
    // To send a simple email
    String sendSimpleMail(UserPostDto user);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EventPostDto details);
}
