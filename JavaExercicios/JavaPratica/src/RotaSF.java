import java.util.*;

public class RotaSF {

    // Classe para representar o grafo
    static class Grafo {
        private Map<String, List<String>> adjacencias = new HashMap<>();

        public void adicionarAresta(String origem, String destino) {
            adjacencias.putIfAbsent(origem, new ArrayList<>());
            adjacencias.putIfAbsent(destino, new ArrayList<>());
            adjacencias.get(origem).add(destino);
            adjacencias.get(destino).add(origem); // Grafo não-direcionado
        }

        public List<String> getVizinhos(String no) {
            return adjacencias.getOrDefault(no, Collections.emptyList());
        }
    }

    // 2. Implementação da Busca em Largura (BFS)
    public static List<String> encontrarCaminhoBFS(Grafo grafo, String inicio, String fim) {
        Queue<String> fila = new LinkedList<>();
        Map<String, String> predecessores = new HashMap<>();
        Set<String> visitados = new HashSet<>();

        fila.add(inicio);
        visitados.add(inicio);
        predecessores.put(inicio, null);

        while (!fila.isEmpty()) {
            String atual = fila.poll();

            if (atual.equals(fim)) {
                return reconstruirCaminho(predecessores, fim);
            }

            for (String vizinho : grafo.getVizinhos(atual)) {
                if (!visitados.contains(vizinho)) {
                    visitados.add(vizinho);
                    predecessores.put(vizinho, atual);
                    fila.add(vizinho);
                }
            }
        }

        return Collections.emptyList(); // Caminho não encontrado
    }

    private static List<String> reconstruirCaminho(Map<String, String> predecessores, String fim) {
        List<String> caminho = new ArrayList<>();
        String atual = fim;

        while (atual != null) {
            caminho.add(atual);
            atual = predecessores.get(atual);
        }

        Collections.reverse(caminho);
        return caminho;
    }

    public static void main(String[] args) {
        // 1. Criando o grafo com locais de São Francisco
        Grafo grafoSF = new Grafo();

        // Adicionando conexões entre pontos turísticos
        grafoSF.adicionarAresta("Twin Peaks", "Sutro Tower");
        grafoSF.adicionarAresta("Twin Peaks", "Dolores Park");
        grafoSF.adicionarAresta("Sutro Tower", "Golden Gate Park");
        grafoSF.adicionarAresta("Dolores Park", "Union Square");
        grafoSF.adicionarAresta("Golden Gate Park", "Presidio");
        grafoSF.adicionarAresta("Union Square", "Fisherman's Wharf");
        grafoSF.adicionarAresta("Presidio", "Golden Gate Bridge");
        grafoSF.adicionarAresta("Fisherman's Wharf", "Golden Gate Bridge");

        // 2. Encontrando o caminho
        List<String> caminho = encontrarCaminhoBFS(grafoSF, "Twin Peaks", "Golden Gate Bridge");

        System.out.println("Caminho de Twin Peaks até Golden Gate Bridge:");
        System.out.println(String.join(" → ", caminho));
    }
}