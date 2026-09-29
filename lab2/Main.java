import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) {
        Person originalPerson = new Person("Шевченко", "Тарас", 47);

        Gson gson = new Gson();
        String json = gson.toJson(originalPerson);
        System.out.println("JSON representation: " + json);

        Person restoredPerson = gson.fromJson(json, Person.class);

        boolean isEqual = originalPerson.equals(restoredPerson);
        System.out.println("Are the objects equal? " + isEqual);
    }
}
