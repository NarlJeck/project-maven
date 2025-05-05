package com.narel.spring.http.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")           //url
    public String loginPage() {
        return "user/login";            //view
    }
}
