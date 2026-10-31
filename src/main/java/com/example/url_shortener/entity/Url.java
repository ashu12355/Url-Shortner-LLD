package com.example.url_shortener.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "urls")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalUrl;
    @Column(unique = true)
    private String shortCode;
    private String generatedUrl;
    private LocalDateTime createdAt;
    private LocalDateTime expireAt;

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
}
