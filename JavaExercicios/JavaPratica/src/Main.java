import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static int countStringInString(String search, String text) {
        Pattern pattern = Pattern.compile(search);
        Matcher matcher = pattern.matcher(text);

        int stringOccurrences = 0;
        while (matcher.find()) {
            stringOccurrences++;
        }
        return stringOccurrences;
    }

    public static void main(String[] args) {
        String text = "One fish, two fish, red fish, blue fish";

        System.out.println(countStringInString("fish", text));
        System.out.println(countStringInString(",", text));
    }
}