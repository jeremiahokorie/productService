package com.productservice.controller;


import com.productservice.persistence.entity.EmailEntity;
import com.productservice.service.EmailSenderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailSenderService emailSenderService;

    public EmailController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/send_email")
    public ResponseEntity<?> sendEmail(@RequestBody EmailEntity emailEntity){
        emailSenderService.sendEmail(emailEntity.getTo(), emailEntity.getSubject(), emailEntity.getMessage());
        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }
}
