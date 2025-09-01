package br.com.alura;

import br.com.alura.model.Anuncio;
import br.com.alura.model.Produto;

import java.math.BigDecimal;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        var produto = new Produto(1,"TV", "4K", new BigDecimal(2000), "Eletrodoméstico");
        var anuncio = new Anuncio(produto,new BigDecimal(4000), 20);

        System.out.println(anuncio.toString());
    }
}