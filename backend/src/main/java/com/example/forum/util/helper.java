package com.example.forum.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class helper {

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
