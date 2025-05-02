package com.example.seoulpublicdata2025backend.domain.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/signup")
@Deprecated
public class SignupPageController {

    @GetMapping
    public String signupPage(@RequestParam("kakaoId") Long kakaoId,  Model model) {
        model.addAttribute("kakaoId", kakaoId);
        return "signup";
    }
}
