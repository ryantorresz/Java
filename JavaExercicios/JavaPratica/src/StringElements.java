import java.util.StringTokenizer;

public class StringElements {
    public static void main(String[] args) {
        String[] elements = {"foo", "bar","teste"};
        String singleString = String.join("+",elements);
        System.out.println(singleString);

    }
}