package com.demo.spring.common.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
public class IndexController {

    @Autowired
    private CustomSendMessage customSendMessage;

    @GetMapping("oneMessage")
    public String oneMessage(@RequestParam String name, @RequestParam int age){
        customSendMessage.sendOneMessage(new CustomOne(name, age));
        return "success";
    }
}
