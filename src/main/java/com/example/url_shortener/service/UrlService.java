package com.example.url_shortener.service;

import com.example.url_shortener.dto.CreateUrlRequest;
import com.example.url_shortener.entity.Url;
import com.example.url_shortener.exception.ResourceNotFoundException;
import com.example.url_shortener.repository.UrlRepository;
import com.example.url_shortener.utility.Base62Encoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.LocalDateTime;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final Base62Encoder base62Encoder;

    public UrlService(UrlRepository urlRepository, Base62Encoder base62Encoder){
        this.urlRepository = urlRepository;
        this.base62Encoder = base62Encoder;
    }

    @Transactional
    public Url createShortUrl(CreateUrlRequest createUrlRequest) {

        if (createUrlRequest == null) {
            throw new IllegalArgumentException("Request cannot be empty");
        }
        //1. url validation

        String originalUrl = createUrlRequest.originalUrl();

        if (originalUrl == null || originalUrl.isBlank()) {
            throw new IllegalArgumentException("URL Cannot be empty");
        }

        //2	Validate URL format, scheme and host

        URI uri;
        try {
            uri = URI.create(originalUrl);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid URL format", ex);
        }

        String scheme = uri.getScheme();
        String host = uri.getHost();



        if (scheme == null || host == null ||
                scheme.isBlank() || host.isBlank()) {
            throw new IllegalArgumentException("Invalid URL");
        }
        if (!host.contains(".")) {
            throw new IllegalArgumentException("Invalid domain name");
        }

        if (!scheme.equalsIgnoreCase("https") &&
                !scheme.equalsIgnoreCase("http")) {
            throw new IllegalArgumentException("Invalid Scheme");
        }

        // Expiration

        LocalDateTime expireAt = createUrlRequest.expireAt();

        if(expireAt != null && !expireAt.isAfter(LocalDateTime.now())){
            throw new IllegalArgumentException("Expiration Date must be in Future");
        }


        //3 Convert the ID into a Base62 short code.

           Url obj = new Url();
            obj.setOriginalUrl(originalUrl);
        obj.setExpireAt(expireAt);

            Url url = urlRepository.save(obj);

            Long savedUrlId = url.getId();


            String shortCode = base62Encoder.convertIntoBase62Encoder(savedUrlId);

            obj.setShortCode(shortCode);

            String generatedUrl = "http://localhost:8080/"+shortCode;

            obj.setGeneratedUrl(generatedUrl);

        return urlRepository.save(obj);
    }

    public String getOriginalUrl(String shortCode){



        Url url = urlRepository.findByShortCode(shortCode).orElseThrow(
                () -> new ResourceNotFoundException("URL Doesn't Exist")
        );

        LocalDateTime expireAt = url.getExpireAt();

        if(expireAt != null && !expireAt.isAfter(LocalDateTime.now())){
            throw new IllegalArgumentException("Link is Expired");
        }

        return url.getOriginalUrl();


    }


}
