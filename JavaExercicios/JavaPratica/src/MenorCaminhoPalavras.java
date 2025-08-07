import java.util.*;

public class MenorCaminhoPalavras {

    public static List<String> encontrarMenorCaminho(String inicio, String fim, Set<String> dicionario) {
        // Verifica se as palavras têm o mesmo tamanho
        if (inicio.length() != fim.length()) {
            return Collections.emptyList();
        }

        // Cria uma fila para a BFS
        Queue<List<String>> fila = new LinkedList<>();
        List<String> caminhoInicial = new ArrayList<>();
        caminhoInicial.add(inicio);
        fila.add(caminhoInicial);

        // Cria um conjunto para palavras visitadas
        Set<String> visitadas = new HashSet<>();
        visitadas.add(inicio);

        while (!fila.isEmpty()) {
            List<String> caminhoAtual = fila.poll();
            String palavraAtual = caminhoAtual.get(caminhoAtual.size() - 1);

            // Se chegamos à palavra final, retornamos o caminho
            if (palavraAtual.equals(fim)) {
                return caminhoAtual;
            }

            // Gera todas as palavras possíveis com uma letra diferente
            for (int i = 0; i < palavraAtual.length(); i++) {
                char[] chars = palavraAtual.toCharArray();
                for (char c = 'a'; c <= 'z'; c++) {
                    chars[i] = c;
                    String novaPalavra = new String(chars);

                    // Verifica se a nova palavra está no dicionário e não foi visitada
                    if (dicionario.contains(novaPalavra) && !visitadas.contains(novaPalavra)) {
                        visitadas.add(novaPalavra);
                        List<String> novoCaminho = new ArrayList<>(caminhoAtual);
                        novoCaminho.add(novaPalavra);
                        fila.add(novoCaminho);
                    }
                }
            }
        }

        return Collections.emptyList(); // Caminho não encontrado
    }

    public static void main(String[] args) {
        // Dicionário de palavras válidas (pode ser expandido)
        Set<String> dicionario = new HashSet<>(Arrays.asList(
                "jato", "gato", "rato", "pato", "jaro", "jata", "goto", "gado"
        ));

        String inicio = "jato";
        String fim = "gato";

        List<String> caminho = encontrarMenorCaminho(inicio, fim, dicionario);

        if (caminho.isEmpty()) {
            System.out.println("Não foi encontrado um caminho de " + inicio + " para " + fim);
        } else {
            System.out.println("Menor caminho de " + inicio + " para " + fim + ":");
            System.out.println(String.join(" → ", caminho));
        }
    }
}