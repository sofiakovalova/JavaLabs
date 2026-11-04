import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Task4TagCounter {
    public static void main(String[] args) {
        String urlString = "https://example.com"; // URL на ваш вибір
        Map<String, Integer> tagMap = new HashMap<>();

        try {
            URL url = new URL(urlString);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            // регулярний вираз для пошуку відкриваючих та одинарних HTML-тегів
            Pattern pattern = Pattern.compile("<\\s*([a-zA-Z0-9]+)[^>]*>");

            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    String tagName = matcher.group(1).toLowerCase();
                    tagMap.put(tagName, tagMap.getOrDefault(tagName, 0) + 1);
                }
            }
            reader.close();

            // виведення в порядку зростання для тегів
            System.out.println("=== 1. Теги у лексикографічному порядку (за алфавітом) ===");
            Map<String, Integer> sortedByTag = new TreeMap<>(tagMap);
            for (Map.Entry<String, Integer> entry : sortedByTag.entrySet()) {
                System.out.printf("Тег: %-15s | Частота: %d%n", entry.getKey(), entry.getValue());
            }

            // виведення в порядку зростання по частоті появи тегів
            System.out.println("\n=== 2. Теги у порядку зростання частоти ===");

            List < Map.Entry < String, Integer > > sortedByFrequency = new ArrayList < > (tagMap.entrySet());
            sortedByFrequency.sort(Map.Entry.comparingByValue());

            for (Map.Entry<String, Integer> entry : sortedByFrequency) {
                System.out.printf("Тег: %-15s | Частота: %d%n", entry.getKey(), entry.getValue());
            }

        } catch (Exception e) {
            System.err.println("Помилка під час зчитування сторінки: " + e.getMessage());
        }
    }
}
