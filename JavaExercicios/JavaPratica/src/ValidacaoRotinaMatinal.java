import java.util.Arrays;
import java.util.List;

public class ValidacaoRotinaMatinal {

    public static void main(String[] args) {
        // Definir as dependências: "tomar café da manhã" deve vir depois de "escovar os dentes"
        String[][] dependencias = {
                {"tomar café da manhã", "escovar os dentes"}
        };

        // Listas a serem validadas
        List<String> lista1 = Arrays.asList(
                "acordar",
                "tomar banho",
                "escovar os dentes",
                "tomar café da manhã"
        );

        List<String> lista2 = Arrays.asList(
                "acordar",
                "escovar os dentes",
                "tomar banho",
                "tomar café da manhã"
        );

        List<String> lista3 = Arrays.asList(
                "acordar",
                "tomar café da manhã",
                "escovar os dentes",
                "tomar banho"
        );

        // Validar as listas
        System.out.println("Lista 1 é válida? " + validarLista(lista1, dependencias));
        System.out.println("Lista 2 é válida? " + validarLista(lista2, dependencias));
        System.out.println("Lista 3 é válida? " + validarLista(lista3, dependencias));
    }

    public static boolean validarLista(List<String> lista, String[][] dependencias) {
        for (String[] dep : dependencias) {
            String atividadeDependente = dep[0];
            String prerequisito = dep[1];

            int indexAtividade = lista.indexOf(atividadeDependente);
            int indexPrerequisito = lista.indexOf(prerequisito);

            if (indexAtividade == -1 || indexPrerequisito == -1) {
                // Se alguma atividade não existe na lista, considerar inválido
                return false;
            }

            if (indexAtividade < indexPrerequisito) {
                // A atividade dependente vem antes do pré-requisito (inválido)
                return false;
            }
        }
        return true;
    }
}