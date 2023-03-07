package ru.chia2k.auth.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class PasswordUtils {
    public static String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return  encoder.encode(password);
    }

    public static void main(String[] args) {
        System.out.println(encodePassword("123456"));
        System.out.println(encodePassword("654321"));
    }
}
