import java.util.ArrayList;
import java.util.List;

public class MemoryOverflowExample {
    public static void main(String[] args) {
        List<byte[]> list = new ArrayList<>();
        try {
            while (true) {
                list.add(new byte[1000000]); // Aloca 1MB por vez
            }
        } catch (OutOfMemoryError e) {
            System.out.println("Erro: " + e);
        }
    }
}