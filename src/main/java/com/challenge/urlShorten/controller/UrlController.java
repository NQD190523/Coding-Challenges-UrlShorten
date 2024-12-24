package com.challenge.urlShorten.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.urlShorten.service.UrlService;


@RestController
@RequestMapping("/api/url")
public class UrlController {
    
    private static final String DOMAIN = "http://localhost:8080/api/url/";

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<Map<String,String>>  generateShortUrl( @RequestBody Map<String,String>  request) {
        String shortUrl = urlService.generateShortUrl(request.get("url"));
        ResponseEntity<Map<String,String>> response = ResponseEntity.ok(Map.of("shortUrl", DOMAIN+  shortUrl));
        return response;
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Map<String,String>> getOriginalUrl(@PathVariable String shortUrl) {
        String originalUrl = urlService.getOriginalUrl(shortUrl);
        System.out.println("originalUrl: " + originalUrl);
        if(originalUrl == null || originalUrl.isEmpty()) { 
            return new ResponseEntity<Map<String,String>>(Map.of("error", "URL not found"), HttpStatus.NOT_FOUND);
        }
        return  ResponseEntity.status(HttpStatus.FOUND).header("Location", originalUrl).build();
    }

    @GetMapping("/delete")
    public ResponseEntity<Map<String,String>> deleteUrl(@RequestBody String url) {
        urlService.deleteUrl(url);
        return new ResponseEntity<Map<String,String>>(Map.of("message", "URL deleted"), HttpStatus.OK);
    }
    
}