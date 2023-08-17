package com.example.t_t_backend.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(columnList = "id"),
        @Index(columnList = "name")
})
public class Group {
    @Id @GeneratedValue
    Long id;

    @Setter
    String name;

    @Setter
    @OneToOne
    @JoinColumn(name = "article_id")
    Article article;
}