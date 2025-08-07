import java.util.List;

public class ContadorRecursivo {
    public static <T> int contarRecursivo(List<T> lista) {
        return contarRecursivo(lista, 0);
    }

    private static <T> int contarRecursivo(List<T> lista, int indice) {
        if (indice >= lista.size()) {
            return 0;
        }
        return 1 + contarRecursivo(lista, indice + 1);
    }

    public static void main(String[] args) {
        List<String> linguagens = List.of("Java", "Python", "C++", "JavaScript");
        System.out.println("Total (recursivo): " + contarRecursivo(linguagens)); // 4
    }
}