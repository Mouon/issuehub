package com.everysource.everysource.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {



    @GetMapping("/")
    public String showHomePage() {
        return "/home.html"; // resources/static/home.html을 렌더링
    }
}
