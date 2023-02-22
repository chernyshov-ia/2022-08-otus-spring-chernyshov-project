package ru.chia2k.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "REFRESH_TOKENS")
@Getter
@Setter
public class RefreshTokenEntity {
    @Id
    @Column(name = "TOKEN_ID")
    private String id;

    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

    @Column(name = "APP_ID")
    private String app;

    @Column(name = "SUBJECT")
    private String subject;

    @Column(name = "EXPIRATION")
    private Date expiration;
}