import java.util.*;

public class DijkstraAlgorithm {

    // Classe para representar uma aresta no grafo
    static class Edge {
        int destino;
        int peso;

        public Edge(int destino, int peso) {
            this.destino = destino;
            this.peso = peso;
        }
    }

    // Classe para o nó no heap mínimo
    static class Node implements Comparable<Node> {
        int vertice;
        int distancia;

        public Node(int vertice, int distancia) {
            this.vertice = vertice;
            this.distancia = distancia;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.distancia, other.distancia);
        }
    }

    private List<List<Edge>> grafo;
    private int numVertices;

    public DijkstraAlgorithm(int numVertices) {
        this.numVertices = numVertices;
        this.grafo = new ArrayList<>();

        for (int i = 0; i < numVertices; i++) {
            grafo.add(new ArrayList<>());
        }
    }

    // Adiciona uma aresta direcionada
    public void addAresta(int origem, int destino, int peso) {
        grafo.get(origem).add(new Edge(destino, peso));
    }

    // Algoritmo de Dijkstra usando PriorityQueue (Heap)
    public int[] executarDijkstra(int origem) {
        int[] distancias = new int[numVertices];
        Arrays.fill(distancias, Integer.MAX_VALUE);
        distancias[origem] = 0;

        boolean[] visitado = new boolean[numVertices];
        PriorityQueue<Node> heap = new PriorityQueue<>();
        heap.offer(new Node(origem, 0));

        while (!heap.isEmpty()) {
            Node nodeAtual = heap.poll();
            int u = nodeAtual.vertice;

            if (visitado[u]) continue;
            visitado[u] = true;

            for (Edge aresta : grafo.get(u)) {
                int v = aresta.destino;
                int peso = aresta.peso;

                if (!visitado[v] && distancias[u] != Integer.MAX_VALUE &&
                        distancias[u] + peso < distancias[v]) {

                    distancias[v] = distancias[u] + peso;
                    heap.offer(new Node(v, distancias[v]));
                }
            }
        }

        return distancias;
    }

    // Método para reconstruir o caminho mais curto
    public List<Integer> reconstruirCaminho(int[] distancias, int[] predecessores, int destino) {
        List<Integer> caminho = new ArrayList<>();

        if (distancias[destino] == Integer.MAX_VALUE) {
            return caminho; // Caminho não existe
        }

        for (int v = destino; v != -1; v = predecessores[v]) {
            caminho.add(v);
        }

        Collections.reverse(caminho);
        return caminho;
    }

    // Versão avançada com predecessores
    public int[] executarDijkstraComPredecessores(int origem) {
        int[] distancias = new int[numVertices];
        int[] predecessores = new int[numVertices];
        Arrays.fill(distancias, Integer.MAX_VALUE);
        Arrays.fill(predecessores, -1);
        distancias[origem] = 0;

        PriorityQueue<Node> heap = new PriorityQueue<>();
        heap.offer(new Node(origem, 0));

        while (!heap.isEmpty()) {
            Node nodeAtual = heap.poll();
            int u = nodeAtual.vertice;

            if (nodeAtual.distancia > distancias[u]) continue;

            for (Edge aresta : grafo.get(u)) {
                int v = aresta.destino;
                int peso = aresta.peso;

                if (distancias[u] + peso < distancias[v]) {
                    distancias[v] = distancias[u] + peso;
                    predecessores[v] = u;
                    heap.offer(new Node(v, distancias[v]));
                }
            }
        }

        return predecessores;
    }

    // Exemplo de uso
    public static void main(String[] args) {
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(6);

        // Construindo o grafo
        dijkstra.addAresta(0, 1, 4);
        dijkstra.addAresta(0, 2, 2);
        dijkstra.addAresta(1, 2, 1);
        dijkstra.addAresta(1, 3, 5);
        dijkstra.addAresta(2, 3, 8);
        dijkstra.addAresta(2, 4, 10);
        dijkstra.addAresta(3, 4, 2);
        dijkstra.addAresta(3, 5, 6);
        dijkstra.addAresta(4, 5, 3);

        int origem = 0;
        int[] distancias = dijkstra.executarDijkstra(origem);
        int[] predecessores = dijkstra.executarDijkstraComPredecessores(origem);

        System.out.println("Distâncias mínimas a partir do vértice " + origem + ":");
        for (int i = 0; i < distancias.length; i++) {
            System.out.println("Para vértice " + i + ": " +
                    (distancias[i] == Integer.MAX_VALUE ? "Inalcançável" : distancias[i]));
        }

        System.out.println("\nCaminhos mais curtos:");
        for (int i = 0; i < distancias.length; i++) {
            List<Integer> caminho = dijkstra.reconstruirCaminho(distancias, predecessores, i);
            System.out.println("Para " + i + ": " + caminho +
                    " (distância: " + distancias[i] + ")");
        }
    }
}