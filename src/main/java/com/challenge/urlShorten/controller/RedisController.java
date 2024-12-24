package com.challenge.urlShorten.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.urlShorten.service.RedisService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/redis")
public class RedisController {
    
    @Autowired
    private RedisService redisService;

    @GetMapping("/save")
    public String save(@RequestParam String key, @RequestParam String value) {
        redisService.save(key, value);
        return "Saved key: " + key + " with value: " + value;
    }
    
    @GetMapping("/get")
    public String getMethodName(@RequestParam String param) {
        String value = redisService.get(param);
        return "Value for key: " + param + " is " + value;
    }

}