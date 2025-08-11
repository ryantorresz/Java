import java.math.BigDecimal;

public class testeDecimal {
    public static void main(String[] args) {
    BigDecimal a = new BigDecimal("5");
    BigDecimal b = new BigDecimal("10");

    BigDecimal resultado = a.add(b);
        System.out.println(resultado);

    }
}