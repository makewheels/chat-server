package com.eg.chatserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    public String register(@RequestParam String loginName, @RequestParam String password) {
        return "";
    }

}
