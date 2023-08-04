package com.example.controller;

import com.example.service.EmailHistoryService;
import com.example.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/email_history")
public class EmailHistoryController {
    @Autowired
    private EmailHistoryService emailHistoryService;
    @Autowired
    private MailSenderService mailSenderService;
    @GetMapping(value = "/open/{email}")
    public ResponseEntity<?> getByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(emailHistoryService.getEmailHistory(email));
    }

    @GetMapping(value = "/open/{date}")
    public ResponseEntity<?> getByDate(@PathVariable("date") LocalDate date) {
        LocalDateTime localDateTime1 = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime localDateTime2 = LocalDateTime.of(date, LocalTime.MAX);
        return ResponseEntity.ok(emailHistoryService.getEmailByDates(localDateTime1, localDateTime2));
    }

    @GetMapping(value = "/admin/pagination")
    public ResponseEntity<?> pagination(@RequestParam("from") int from,
                                        @RequestParam("to") int to) {
      //  SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(emailHistoryService.emailHistoryPagination(from-1, to));
    }



}
