package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.LinkedHashSet;
import java.util.Set;

@SpringBootApplication
public class TesteBasicoApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(TesteBasicoApplication.class, args);

        try {
            ApiCliente api = context.getBean(ApiCliente.class);

            String json = api.get("/pokemon?limit=30");
            ObjectMapper mapper = new ObjectMapper();
            JsonNode tudo = mapper.readTree(json);

            // Extrair nomes dos pokémons
            System.out.println("=== Lista de Pokémons ===");
            if (tudo.has("results")) {
                for (JsonNode pokemon : tudo.get("results")) {
                    if (pokemon.has("name")) {
                        System.out.println("Pokémon: " + pokemon.get("name").asText());
                    }
                }
            }

            // Se quiser extrair todos os "names" do JSON
            Set<String> todosNomes = new LinkedHashSet<>();
            collectAllNames(tudo, todosNomes);

            System.out.println("\n=== Todos os nomes encontrados no JSON ===");
            todosNomes.forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Mantém a aplicação rodando por um tempo
        Thread.sleep(5000);
        context.close();
    }

    private static void collectAllNames(JsonNode node, Set<String> names) {
        if (node == null || node.isNull()) return;

        if (node.isObject()) {
            // Verifica se este nó tem um campo "name"
            if (node.has("name") && node.get("name").isTextual()) {
                names.add(node.get("name").asText());
            }

            // Percorre todos os campos do objeto
            node.fields().forEachRemaining(entry -> {
                collectAllNames(entry.getValue(), names);
            });

        } else if (node.isArray()) {
            for (JsonNode item : node) {
                collectAllNames(item, names);
            }
        }
    }
}