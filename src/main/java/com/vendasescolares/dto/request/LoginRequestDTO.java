package com.vendasescolares.dto.request;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String senha;
}