package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.controller;

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
    public String signupPage(@RequestParam("kakaoId") Long kakaoId, @RequestParam("profileUrl") String profileUrl, Model model) {
        model.addAttribute("kakaoId", kakaoId);
        model.addAttribute("profileUrl", profileUrl);
        return "signup";
    }
}
