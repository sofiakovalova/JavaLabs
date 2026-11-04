import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Task1MaxWordsLine {
    public static void main(String[] args) {
        String filePath = "test_text.txt";

        String maxLine = "";
        int maxWordCount = 0;
        int maxLineNumber = 0;
        int currentLineNumber = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                currentLineNumber++;

                String[] words = line.trim().split("\\s+");

                int wordCount = line.trim().isEmpty() ? 0 : words.length;

                if (wordCount > maxWordCount) {
                    maxWordCount = wordCount;
                    maxLine = line;
                    maxLineNumber = currentLineNumber;
                }
            }

            if (maxWordCount > 0) {
                System.out.println("Рядок №" + maxLineNumber + " має найбільшу кількість слів (" + maxWordCount + "):");
                System.out.println("\"" + maxLine + "\"");
            } else {
                System.out.println("Файл порожній або не містить слів.");
            }

        } catch (IOException e) {
            System.err.println("Помилка читання файлу: " + e.getMessage());
        }
    }
}
