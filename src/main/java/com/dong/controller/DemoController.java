package com.dong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Controller
public class DemoController {


    @RequestMapping("/123")
    public String demo01(Model model){
        model.addAttribute("name","xxxx");
        return "hello";
    }
}
