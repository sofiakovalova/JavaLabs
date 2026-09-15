import java.util.Arrays;

public class DiffSymbols {
    public static void main(String[] args) {
        String[] inputWords = {"hello", "world", "java", "unique", "kpi", "student"};
        String[] result = findWordsWithUniqueChars(inputWords);
        System.out.println("Result: " + Arrays.toString(result));
    }

    public static String[] findWordsWithUniqueChars(String[] words) {
        String[] temp = new String[words.length];
        int count = 0;

        for (String word : words) {
            if (hasUniqueCharacters(word)) {
                temp[count++] = word;
            }
        }

        String[] result = new String[count];
        System.arraycopy(temp, 0, result, 0, count);
        return result;
    }

    private static boolean hasUniqueCharacters(String word) {
        for (int i = 0; i < word.length(); i++) {
            for (int j = i + 1; j < word.length(); j++) {
                if (word.charAt(i) == word.charAt(j)) {
                    return false;
                }
            }
        }
        return true;
    }
}
