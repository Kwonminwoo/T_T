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
        @Index(columnList = "account_id"),
        @Index(columnList = "name")
})
public class User {

    @Id @GeneratedValue
    Long id;

    @Setter
    @Column(name = "account_id")
    String accountId;
    @Setter
    String password;
    @Setter
    String name;
    @Setter
    String image;
}