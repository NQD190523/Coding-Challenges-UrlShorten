package com.challenge.urlShorten.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlService {
    
    @Autowired
    private  RedisService redisService;
    public String generateShortUrl(String url) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(url.getBytes(StandardCharsets.UTF_8));
            String shortUrl = Base64.getEncoder().encode(hash).toString().substring(1,7);
            redisService.save(shortUrl, url);
            return shortUrl;
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate short URL", e);
        }
    }

    // get original url from redis
    public String getOriginalUrl(String shortUrl) {
        try {
            String  originalUrl = redisService.get(shortUrl);
            return originalUrl;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get original URL", e);   
        }
    }

    public void deleteUrl(String shortUrl) {
        redisService.delete(shortUrl);
    }
}