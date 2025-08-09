import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProgramacaoFuncional {
    public static void main(String[] args) {
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5);

        // Map
        List<Integer> quadrados = numeros.stream()
                .map(x -> x * x)
                .collect(Collectors.toList());
        System.out.println("Quadrados: " + quadrados);

        // Filter
        List<Integer> pares = numeros.stream()
                .filter(x -> x % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("Pares: " + pares);

        // Reduce
        int soma = numeros.stream()
                .reduce(0, (a, b) -> a + b);
        System.out.println("Soma: " + soma);
    }
}