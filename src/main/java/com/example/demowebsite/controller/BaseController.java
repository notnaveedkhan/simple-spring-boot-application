package com.example.demowebsite.controller;

import com.example.demowebsite.model.UserRecord;
import com.example.demowebsite.repository.UserRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BaseController {

    private static final String INDEX = "index";
    private static final String ERROR = "error";
    private static final String INFO = "info";

    private final UserRecordRepository userRecordRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("userRecord", new UserRecord());
        model.addAttribute("users", userRecordRepository.findAll());
        return INDEX;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id, Model model) {
        userRecordRepository.deleteById(Long.valueOf(id));
        model.addAttribute("userRecord", new UserRecord());
        model.addAttribute("users", userRecordRepository.findAll());
        return INDEX;
    }

    @GetMapping("/create-user")
    public String createUser(Model model) {
        model.addAttribute("userRecord", new UserRecord());
        model.addAttribute("users", userRecordRepository.findAll());
        return INDEX;
    }

    @PostMapping("/create-user")
    public String createUser(UserRecord userRecord, Model model) {
        model.addAttribute("userRecord", new UserRecord());
        if (userRecord.getName() == null || userRecord.getName().isBlank()) {
            log.error("Name is required");
            model.addAttribute(ERROR, "Name is required");
            model.addAttribute("users", userRecordRepository.findAll());
        } else if (userRecord.getEmail() == null || userRecord.getEmail().isBlank()) {
            log.error("Email is required");
            model.addAttribute(ERROR, "Email is required");
            model.addAttribute("users", userRecordRepository.findAll());
        } else if (userRecordRepository.existsByEmail(userRecord.getEmail())) {
            log.error("Email is already in use");
            model.addAttribute(ERROR, "Email is already in use");
            model.addAttribute("users", userRecordRepository.findAll());
        } else {
            userRecordRepository.save(userRecord);
            log.info("Record is saved");
            model.addAttribute(INFO, "User record saved!");
            model.addAttribute("users", userRecordRepository.findAll());
        }
        return INDEX;
    }

}
