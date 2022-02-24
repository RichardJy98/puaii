package com.uto.puai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/log")
    public String log() {
        return "log";
    }
}
