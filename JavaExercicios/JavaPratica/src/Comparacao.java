import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Teste para a classe Calculadora")
public class CalculadoraTest {
    @Test
    DisplayName("Deve retornar a soma correta para números positivos")
        void DeveSomarNumerosPositivos() {
        int a = 5;
        int b = 3;
        int somaEsperada = 8;

        int resultado = calculadora.somar(a,b);

        }

}