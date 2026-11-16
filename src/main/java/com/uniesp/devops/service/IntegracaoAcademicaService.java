package com.uniesp.devops.service;

import org.springframework.stereotype.Service;

@Service
public class IntegracaoAcademicaService {
    private static final String API_TOKEN = "TOKEN-FICTICIO-AULA-123";

    public String consultarCredencialConfigurada() {
        return API_TOKEN;
    }
}
