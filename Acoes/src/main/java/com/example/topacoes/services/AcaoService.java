package com.example.topacoes.services;

import com.example.topacoes.models.Acao;
import com.example.topacoes.repositories.AcaoRepository;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

@Service
public class AcaoService {

    private final AcaoRepository acaoRepository;
    private final HttpClient httpClient;

    @Autowired
    public AcaoService(AcaoRepository acaoRepository) {
        this.acaoRepository = acaoRepository;
        this.httpClient = HttpClient.newHttpClient();
    }

    public List<Acao> buscarTop10AcoesBrasileiras() {
        List<Acao> acoes = new ArrayList<>();
        String apiKey = "T3PW677JDS7D4H8X"; // Substitua pela sua chave real
        String apiUrl = "https://www.alphavantage.co/query?function=TOP_GAINERS_LOSERS&apikey=" + apiKey;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(apiUrl);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject = new JSONObject(jsonResponse);

                if (jsonObject.has("top_gainers")) {
                    JSONArray topGainers = jsonObject.getJSONArray("top_gainers");

                    // Filtra e processa apenas tickers brasileiros (.SA)
                    for (int i = 0; i < topGainers.length() && acoes.size() < 10; i++) {
                        JSONObject stock = topGainers.getJSONObject(i);
                        String ticker = stock.optString("ticker", "");

                        // Verifica se é uma ação brasileira (.SA ou padrão B3)
                        if (ticker.endsWith(".SA") || isTickerBrasileiro(ticker)) {
                            Acao acao = new Acao();
                            acao.setCodigo(ticker.replace(".SA", "")); // Remove .SA para exibição
                            acao.setNome(stock.optString("company_name", "Desconhecido"));

                            try {
                                acao.setPrecoAtual(new BigDecimal(stock.optString("price", "0")));
                                String changePercentage = stock.optString("change_percentage", "0").replace("%", "");
                                acao.setVariacao(new BigDecimal(changePercentage));
                            } catch (NumberFormatException e) {
                                acao.setPrecoAtual(BigDecimal.ZERO);
                                acao.setVariacao(BigDecimal.ZERO);
                            }

                            acao.setVolumeNegociado(stock.optInt("volume", 0));
                            acoes.add(acao);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return acoes;
    }

    // Método auxiliar para identificar tickers BR (sem .SA)
    private boolean isTickerBrasileiro(String ticker) {
        // Padrão de tickers da B3: 4 letras + 1-2 números (ex: PETR4, VALE3, BBDC4)
        return ticker.matches("^[A-Z]{4}[0-9]{1,2}$");
    }
    // Método para salvar as ações no banco de dados
    public void salvarAcoes(List<Acao> acoes) {
        acaoRepository.saveAll(acoes);
    }

    // Agendado para atualizar a cada hora (opcional)
    @Scheduled(fixedRate = 3600000)
    public void atualizarAcoesPeriodicamente() {
        List<Acao> top10Acoes = buscarTop10AcoesBrasileiras();
        salvarAcoes(top10Acoes);
    }
}