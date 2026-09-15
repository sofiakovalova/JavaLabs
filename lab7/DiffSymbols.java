import java.util.Arrays;

public class DiffSymbols {
    public static void main(String[] args) {
        String[] inputWords = {"hello", "world", "java", "unique", "kpi", "student"};
        String[] result = findWordsWithUniqueCharsLambda(inputWords);
        System.out.println("Result: " + Arrays.toString(result));
    }

    public static String[] findWordsWithUniqueCharsLambda(String[] words) {
        return Arrays.stream(words)
                .filter(word -> word.chars().distinct().count() == word.length())
                .toArray(String[]::new);
    }
}
