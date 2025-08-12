package com.example.topacoes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // Habilita o agendamento de tarefas
public class TopAcoesApplication {
    public static void main(String[] args) {
        SpringApplication.run(TopAcoesApplication.class, args);
    }
}