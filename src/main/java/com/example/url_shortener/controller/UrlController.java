package com.example.url_shortener.controller;

import com.example.url_shortener.dto.CreateUrlRequest;
import com.example.url_shortener.entity.Url;
import com.example.url_shortener.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService){
        this.urlService = urlService;
    }


    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectUrl(@PathVariable String shortCode){
        String originalUrl =  urlService.getOriginalUrl(shortCode);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }

    @PostMapping("/urls")
    public ResponseEntity<Url> createShortUrl(@RequestBody CreateUrlRequest createUrlRequest){
          Url url = urlService.createShortUrl(createUrlRequest);

          return ResponseEntity.status(HttpStatus.CREATED).body(url);
    }



}
