import java.util.List;

public class Main {
    public static int contarItens(List<?> lista) {
        // Caso base: lista vazia
        if (lista.isEmpty()) {
            return 0;
        }
        // Caso recursivo: 1 + contagem do restante da lista
        return 1 + contarItens(lista.subList(1, lista.size()));
    }

    public static void main(String[] args) {
        List<Integer> numeros = List.of(1, 2, 3, 4);
        System.out.println(contarItens(numeros));  // Saída: 4

        List<String> vazia = List.of();
        System.out.println(contarItens(vazia));  // Saída: 0
    }
}