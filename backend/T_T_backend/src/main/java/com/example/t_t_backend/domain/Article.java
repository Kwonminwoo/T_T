package com.example.t_t_backend.domain;

import java.time.LocalDateTime;

public class Article {
    Long id;
    String title;
    double startLat;
    double startLon;
    double endLat;
    double endLon;

    String content;
    LocalDateTime startTime;
    User user;
}