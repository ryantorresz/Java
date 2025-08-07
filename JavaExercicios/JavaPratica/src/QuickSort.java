import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuickSort {

    public static List<Integer> quicksort(List<Integer> array) {
        // Caso base: arrays com 0 ou 1 elemento já estão "ordenados"
        if (array.size() < 2) {
            return array;
        } else {
            // Escolhe o primeiro elemento como pivô
            int pivo = array.get(0);

            // Sub-array dos elementos menores ou iguais ao pivô
            List<Integer> menores = new ArrayList<>();
            // Sub-array dos elementos maiores que o pivô
            List<Integer> maiores = new ArrayList<>();

            // Particiona os elementos
            for (int i = 1; i < array.size(); i++) {
                if (array.get(i) <= pivo) {
                    menores.add(array.get(i));
                } else {
                    maiores.add(array.get(i));
                }
            }

            // Combina os resultados recursivamente
            List<Integer> resultado = new ArrayList<>();
            resultado.addAll(quicksort(menores));
            resultado.add(pivo);
            resultado.addAll(quicksort(maiores));

            return resultado;
        }
    }

    public static void main(String[] args) {
        List<Integer> numeros = Arrays.asList(10, 5, 2, 3);
        List<Integer> ordenados = quicksort(numeros);
        System.out.println(ordenados); // [2, 3, 5, 10]
    }
}