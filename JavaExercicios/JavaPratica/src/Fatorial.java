public class Fatorial {
    public static void main(String[] args) {
        System.out.println(fatorial(5));
    }

    public static int fatorial(int n) {
        return fatorialAux(n, 1);
    }

    public static int fatorialAux(int n, int acumulador) {
        if (n == 1) return acumulador;
        return fatorialAux(n - 1, n * acumulador);
    }
}