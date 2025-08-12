package com.example.topacoes;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {

    @Bean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        manager.setMaxTotal(100);  // Número máximo de conexões
        manager.setDefaultMaxPerRoute(20);  // Máximo por rota
        return manager;
    }

    @Bean(destroyMethod = "close")  // Adicionado para fechar o client corretamente
    public CloseableHttpClient httpClient() {
        return HttpClients.custom()
                .setConnectionManager(poolingHttpClientConnectionManager())
                .build();
    }
}