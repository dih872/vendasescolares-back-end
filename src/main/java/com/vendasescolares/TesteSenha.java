package com.vendasescolares;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TesteSenha {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String novoHash = encoder.encode("Laura123");
        System.out.println("Novo hash: " + novoHash);
    }
}
