package com.bloghu.thymeleaf.helloword.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloworldController {

	@GetMapping("/")
    public String main(Model model) {
        model.addAttribute("message", "Salam perdamaian");
        return "helloworld";
    }
}
