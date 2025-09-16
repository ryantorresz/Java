import java.util.regex.Pattern;

public class ValidaEmail{
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean emailValido(String email) {
        return pattern.matcher(email).matches();
    }

    public static void main(String[] args) {
        String email = "example@email.com";
        if (emailValido(email)) {
            System.out.println("E-mail válido.");
        } else {
            System.out.println("E-mail inválido.");
        }
    }
}