package com.example.board;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Article {
    @Id
    public Long id;
    public String title;
    public String content;
    public LocalDateTime createdAt;
}