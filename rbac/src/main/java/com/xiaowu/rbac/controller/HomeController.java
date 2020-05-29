package com.xiaowu.rbac.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/")
    public String index(){
        return "home";
    }

//    @GetMapping("/login")
//    public String login(){
//        return "login";
//    }

//     @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping({"/index", "/home"})
    public String root(){
        return "index";
    }

    // @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping({"/resource"})
    public String resource(){
        System.out.println("post resource");
        return "resource";
    }

    @RequestMapping("/task")
    public String task(){
        return "task";
    }
}
