package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@Deprecated
public class KakaoLoginPageController {

    @Value("${kakao.auth.client}")
    private String client_id;

    @Value("${kakao.auth.redirect}")
    private String redirect_uri;

    @GetMapping("/page")
    public String loginPage(Model model) {
        // 클라이언트 id 는 백엔드꺼 써야되나
        // 이 url을 프론트한테 줘야 할듯. 이거는 테스트 용으로 남겨둔다.
        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + client_id + "&redirect_uri=" + redirect_uri;
        model.addAttribute("location", location);
        return "login";
    }
}
