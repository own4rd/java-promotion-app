package br.com.lowlevel.promotion_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PromotionAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(PromotionAppApplication.class, args);

//        Map<String, PasswordEncoder> encoders = new HashMap<>();
//
//        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder("", 8, 185000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256));
//
//        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
//
//        passwordEncoder.setDefaultPasswordEncoderForMatches(new Pbkdf2PasswordEncoder("", 8, 185000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256));
//
//
//        String result = passwordEncoder.encode("admin123");
//
//        System.out.println("My hash " + result);
    }
}
