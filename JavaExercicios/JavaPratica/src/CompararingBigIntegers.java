import java.math.BigDecimal;
import java.math.BigInteger;

public class CompararingBigIntegers {
    public static void main(String[] args) {
        BigInteger one = BigInteger.valueOf(1);
        BigInteger two = BigInteger.valueOf(10);

        if(one.equals(two)) {
            System.out.println("Equal");
        } else {
            System.out.println("NOT EQUAL");
        }

    }
}