import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Atividade {
    String nome;
    int valor;
    int tempo;

    public Atividade(String nome, int valor, int tempo) {
        this.nome = nome;
        this.valor = valor;
        this.tempo = tempo;
    }
}

public class ProblemaViagem {
    public static void main(String[] args) {
        List<Atividade> atividades = new ArrayList<>();
        atividades.add(new Atividade("Torre Eiffel", 10, 4));
        atividades.add(new Atividade("Museu do Louvre", 9, 6));
        atividades.add(new Atividade("Arco do Triunfo", 7, 2));
        atividades.add(new Atividade("Notre Dame", 8, 3));
        atividades.add(new Atividade("Montmartre", 6, 5));

        int tempoDisponivel = 7; // dias

        // Ordena por valor/tempo (estratégia gulosa)
        Collections.sort(atividades, new Comparator<Atividade>() {
            @Override
            public int compare(Atividade a1, Atividade a2) {
                double ratio1 = (double) a1.valor / a1.tempo;
                double ratio2 = (double) a2.valor / a2.tempo;
                return Double.compare(ratio2, ratio1);
            }
        });

        int tempoUsado = 0;
        int valorTotal = 0;
        System.out.println("Atividades selecionadas:");

        for (Atividade atividade : atividades) {
            if (tempoUsado + atividade.tempo <= tempoDisponivel) {
                tempoUsado += atividade.tempo;
                valorTotal += atividade.valor;
                System.out.println("- " + atividade.nome + " (" + atividade.tempo + " dias, valor: " + atividade.valor + ")");
            }
        }

        System.out.println("Tempo total usado: " + tempoUsado + "/" + tempoDisponivel + " dias");
        System.out.println("Valor total obtido: " + valorTotal);
    }
}