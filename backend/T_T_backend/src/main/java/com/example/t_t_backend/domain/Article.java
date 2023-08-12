package com.example.t_t_backend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Table(indexes = {
        @Index(columnList = "id"),
        @Index(columnList = "title")
})
public class Article {
    @Id @GeneratedValue
    Long id;

    @Setter
    String title;
    @Setter
    double startLat;
    @Setter
    double startLon;
    @Setter
    double endLat;
    @Setter
    double endLon;

    @Setter
    String content;

    @Setter
    LocalDateTime startTime;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}