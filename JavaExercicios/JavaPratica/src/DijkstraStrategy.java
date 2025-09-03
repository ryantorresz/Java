import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// Interface para estratégias de roteamento
interface RoutingStrategy {
    List<Integer> findShortestPath(Graph graph, int source, int destination);
    String getStrategyName();
}

// Algoritmo de Dijkstra (clássico)
class DijkstraStrategy implements RoutingStrategy {
    @Override
    public List<Integer> findShortestPath(Graph graph, int source, int destination) {
        int n = graph.getNumVertices();
        int[] dist = new int[n];
        int[] prev = new int[n];
        boolean[] visited = new boolean[n];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        dist[source] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{source, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int u = current[0];

            if (u == destination) break;
            if (visited[u]) continue;
            visited[u] = true;

            for (Graph.Edge edge : graph.getAdjacencyList(u)) {
                int v = edge.destination;
                int weight = edge.weight;

                if (!visited[v] && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    prev[v] = u;
                    pq.offer(new int[]{v, dist[v]});
                }
            }
        }

        return reconstructPath(prev, destination);
    }

    private List<Integer> reconstructPath(int[] prev, int destination) {
        LinkedList<Integer> path = new LinkedList<>();
        for (int at = destination; at != -1; at = prev[at]) {
            path.addFirst(at);
        }
        return path.getFirst() == destination ? path : Collections.emptyList();
    }

    @Override
    public String getStrategyName() {
        return "Dijkstra";
    }
}

// Algoritmo A* (heurístico)
class AStarStrategy implements RoutingStrategy {
    @Override
    public List<Integer> findShortestPath(Graph graph, int source, int destination) {
        int n = graph.getNumVertices();
        int[] gScore = new int[n];
        int[] fScore = new int[n];
        int[] cameFrom = new int[n];
        boolean[] visited = new boolean[n];

        Arrays.fill(gScore, Integer.MAX_VALUE);
        Arrays.fill(fScore, Integer.MAX_VALUE);
        Arrays.fill(cameFrom, -1);

        gScore[source] = 0;
        fScore[source] = heuristic(source, destination, graph);

        PriorityQueue<int[]> openSet = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        openSet.offer(new int[]{source, fScore[source]});

        while (!openSet.isEmpty()) {
            int[] current = openSet.poll();
            int u = current[0];

            if (u == destination) {
                return reconstructPath(cameFrom, destination);
            }

            visited[u] = true;

            for (Graph.Edge edge : graph.getAdjacencyList(u)) {
                int v = edge.destination;
                if (visited[v]) continue;

                int tentativeGScore = gScore[u] + edge.weight;

                if (tentativeGScore < gScore[v]) {
                    cameFrom[v] = u;
                    gScore[v] = tentativeGScore;
                    fScore[v] = gScore[v] + heuristic(v, destination, graph);

                    if (!openSet.stream().anyMatch(node -> node[0] == v)) {
                        openSet.offer(new int[]{v, fScore[v]});
                    }
                }
            }
        }

        return Collections.emptyList();
    }

    private int heuristic(int node, int target, Graph graph) {
        // Heurística simples baseada em coordenadas (se disponíveis)
        // Em um caso real, seria calculada a distância euclidiana ou manhattan
        return Math.abs(node - target);
    }

    private List<Integer> reconstructPath(int[] cameFrom, int destination) {
        LinkedList<Integer> path = new LinkedList<>();
        for (int at = destination; at != -1; at = cameFrom[at]) {
            path.addFirst(at);
        }
        return path;
    }

    @Override
    public String getStrategyName() {
        return "A*";
    }
}

// Algoritmo Bellman-Ford (para pesos negativos)
class BellmanFordStrategy implements RoutingStrategy {
    @Override
    public List<Integer> findShortestPath(Graph graph, int source, int destination) {
        int n = graph.getNumVertices();
        int[] dist = new int[n];
        int[] prev = new int[n];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        dist[source] = 0;

        // Relaxamento das arestas
        for (int i = 0; i < n - 1; i++) {
            for (int u = 0; u < n; u++) {
                if (dist[u] == Integer.MAX_VALUE) continue;

                for (Graph.Edge edge : graph.getAdjacencyList(u)) {
                    int v = edge.destination;
                    int weight = edge.weight;

                    if (dist[u] + weight < dist[v]) {
                        dist[v] = dist[u] + weight;
                        prev[v] = u;
                    }
                }
            }
        }

        // Verificação de ciclos negativos
        for (int u = 0; u < n; u++) {
            if (dist[u] == Integer.MAX_VALUE) continue;

            for (Graph.Edge edge : graph.getAdjacencyList(u)) {
                int v = edge.destination;
                int weight = edge.weight;

                if (dist[u] + weight < dist[v]) {
                    throw new RuntimeException("Grafo contém ciclo negativo");
                }
            }
        }

        return reconstructPath(prev, destination);
    }

    private List<Integer> reconstructPath(int[] prev, int destination) {
        LinkedList<Integer> path = new LinkedList<>();
        for (int at = destination; at != -1; at = prev[at]) {
            path.addFirst(at);
        }
        return path.getFirst() == destination ? path : Collections.emptyList();
    }

    @Override
    public String getStrategyName() {
        return "Bellman-Ford";
    }
}

// Classe que representa o grafo
class Graph {
    static class Edge {
        int destination;
        int weight;

        public Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    private final List<List<Edge>> adjacencyList;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public Graph(int numVertices) {
        adjacencyList = new ArrayList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    public void addEdge(int source, int destination, int weight) {
        lock.writeLock().lock();
        try {
            adjacencyList.get(source).add(new Edge(destination, weight));
        } finally {
            lock.writeLock().unlock();
        }
    }

    public List<Edge> getAdjacencyList(int vertex) {
        lock.readLock().lock();
        try {
            return new ArrayList<>(adjacencyList.get(vertex));
        } finally {
            lock.readLock().unlock();
        }
    }

    public int getNumVertices() {
        return adjacencyList.size();
    }
}

// Sistema de cache para resultados de roteamento
class RouteCache {
    private final ConcurrentMap<String, List<Integer>> cache = new ConcurrentHashMap<>();
    private final AtomicInteger hits = new AtomicInteger(0);
    private final AtomicInteger misses = new AtomicInteger(0);

    public String generateKey(int source, int destination, String strategy) {
        return source + ":" + destination + ":" + strategy;
    }

    public void put(String key, List<Integer> path) {
        cache.put(key, path);
    }

    public List<Integer> get(String key) {
        List<Integer> result = cache.get(key);
        if (result != null) {
            hits.incrementAndGet();
        } else {
            misses.incrementAndGet();
        }
        return result;
    }

    public void clear() {
        cache.clear();
        hits.set(0);
        misses.set(0);
    }

    public int getHitCount() {
        return hits.get();
    }

    public int getMissCount() {
        return misses.get();
    }
}

// Gerenciador de roteamento com múltiplas estratégias
class RoutingManager {
    private final Map<String, RoutingStrategy> strategies = new ConcurrentHashMap<>();
    private final RouteCache cache = new RouteCache();
    private final ExecutorService executor = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public RoutingManager() {
        registerStrategy("dijkstra", new DijkstraStrategy());
        registerStrategy("astar", new AStarStrategy());
        registerStrategy("bellmanford", new BellmanFordStrategy());
    }

    public void registerStrategy(String name, RoutingStrategy strategy) {
        strategies.put(name.toLowerCase(), strategy);
    }

    public CompletableFuture<List<Integer>> findShortestPathAsync(Graph graph, int source,
                                                                  int destination, String strategyName) {
        return CompletableFuture.supplyAsync(() ->
                findShortestPath(graph, source, destination, strategyName), executor);
    }

    public List<Integer> findShortestPath(Graph graph, int source, int destination, String strategyName) {
        String key = cache.generateKey(source, destination, strategyName);
        List<Integer> cachedResult = cache.get(key);

        if (cachedResult != null) {
            return cachedResult;
        }

        RoutingStrategy strategy = strategies.get(strategyName.toLowerCase());
        if (strategy == null) {
            throw new IllegalArgumentException("Estratégia não encontrada: " + strategyName);
        }

        List<Integer> path = strategy.findShortestPath(graph, source, destination);
        cache.put(key, path);

        return path;
    }

    public Map<String, List<Integer>> compareStrategies(Graph graph, int source, int destination) {
        Map<String, List<Integer>> results = new HashMap<>();
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (String strategyName : strategies.keySet()) {
            CompletableFuture<Void> future = findShortestPathAsync(graph, source, destination, strategyName)
                    .thenAccept(path -> results.put(strategyName, path));
            futures.add(future);
        }

        // Aguarda todas as estratégias completarem
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        return results;
    }

    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public RouteCache getCache() {
        return cache;
    }
}

// Exemplo de uso
class AdvancedRoutingSystem {
    public static void main(String[] args) {
        // Criar um grafo de exemplo
        Graph graph = new Graph(10);

        // Adicionar arestas (origem, destino, peso)
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 2);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 5);
        graph.addEdge(2, 3, 8);
        graph.addEdge(2, 4, 10);
        graph.addEdge(3, 4, 2);
        graph.addEdge(3, 5, 6);
        graph.addEdge(4, 5, 3);
        graph.addEdge(5, 6, 7);
        graph.addEdge(6, 7, 2);
        graph.addEdge(7, 8, 3);
        graph.addEdge(8, 9, 4);
        graph.addEdge(4, 9, 5);

        RoutingManager routingManager = new RoutingManager();

        try {
            // Testar uma rota específica
            System.out.println("Calculando rota do nó 0 ao nó 9...");

            List<Integer> path = routingManager.findShortestPath(graph, 0, 9, "dijkstra");
            System.out.println("Rota (Dijkstra): " + path);

            // Comparar todas as estratégias
            System.out.println("\nComparando todas as estratégias:");
            Map<String, List<Integer>> results = routingManager.compareStrategies(graph, 0, 9);

            for (Map.Entry<String, List<Integer>> entry : results.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            // Testar com cache
            System.out.println("\nTestando cache...");
            System.out.println("Primeira consulta (miss):");
            routingManager.findShortestPath(graph, 0, 9, "dijkstra");
            System.out.println("Cache hits: " + routingManager.getCache().getHitCount());
            System.out.println("Cache misses: " + routingManager.getCache().getMissCount());

            System.out.println("Segunda consulta (hit):");
            routingManager.findShortestPath(graph, 0, 9, "dijkstra");
            System.out.println("Cache hits: " + routingManager.getCache().getHitCount());
            System.out.println("Cache misses: " + routingManager.getCache().getMissCount());

            // Testar execução assíncrona
            System.out.println("\nTestando execução assíncrona...");
            CompletableFuture<List<Integer>> future = routingManager.findShortestPathAsync(graph, 0, 9, "astar");
            future.thenAccept(result -> System.out.println("Rota calculada assincronamente: " + result));

            // Aguardar a conclusão da tarefa assíncrona
            Thread.sleep(1000);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            routingManager.shutdown();
        }
    }
}