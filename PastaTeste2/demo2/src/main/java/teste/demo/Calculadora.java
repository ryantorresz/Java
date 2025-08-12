package teste.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Testes para a classe Calculadora")
    public class Calculadora {
    public int somar(int a, int b) {
        return a + b;
    }

    @Test
    @DisplayName("Deve retornar a soma correta para números positivos")
    void deveSomarNumerosPositivos() {
        // Arrange: Preparar o ambiente e dados de entrada
        Calculadora calculadora = new Calculadora();
        int a = 5;
        int b = 3;
        int somaEsperada = 8;

        // Act: Executar a ação sob teste
        int resultado = calculadora.somar(a, b);

        // Assert: Verificar o resultado
        assertEquals(somaEsperada, resultado, "A soma de 5 e 3 deve ser 8");
    }

    @Test
    @DisplayName("Deve retornar a soma correta para números negativos")
    void deveSomarNumerosNegativos() {
        // Arrange
        Calculadora calculadora = new Calculadora();
        int a = -5;
        int b = -3;
        int somaEsperada = -8;

        // Act
        int resultado = calculadora.somar(a, b);

        // Assert
        assertEquals(somaEsperada, resultado, "A soma de -5 e -3 deve ser -8");
    }

    @Test
    @DisplayName("Deve retornar zero ao somar zero com um número")
    void deveSomarComZero() {
        // Arrange
        Calculadora calculadora = new Calculadora();
        int a = 0;
        int b = 10;
        int somaEsperada = 10;

        // Act
        int resultado = calculadora.somar(a, b);

        // Assert
        assertEquals(somaEsperada, resultado, "A soma de 0 e 10 deve ser 10");
    }
}
