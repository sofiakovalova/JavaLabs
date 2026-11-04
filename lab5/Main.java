import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

// model

interface Drawable {
    void draw();
}

abstract class Shape implements Drawable, Serializable {
    private static final long serialVersionUID = 1L;
    protected String shapeColor;

    public Shape(String shapeColor) {
        this.shapeColor = shapeColor;
    }

    public String getShapeColor() {
        return shapeColor;
    }

    public abstract double calcArea();

    @Override
    public String toString() {
        return "Shape, color: " + shapeColor;
    }
}

class Rectangle extends Shape {
    private static final long serialVersionUID = 1L;
    private double a, b;

    public Rectangle(String shapeColor, double a, double b) {
        super(shapeColor);
        this.a = a;
        this.b = b;
    }

    @Override
    public double calcArea() {
        return a * b;
    }

    @Override
    public void draw() {
        System.out.println("Drawing Rectangle");
    }

    @Override
    public String toString() {
        return String.format("Rectangle [Color: %-6s | Sides: %.1f, %.1f | Area: %.2f]", shapeColor, a, b, calcArea());
    }
}

class Triangle extends Shape {
    private static final long serialVersionUID = 1L;
    private double base, height;

    public Triangle(String shapeColor, double base, double height) {
        super(shapeColor);
        this.base = base;
        this.height = height;
    }

    @Override
    public double calcArea() {
        return 0.5 * base * height;
    }

    @Override
    public void draw() {
        System.out.println("Drawing Triangle");
    }

    @Override
    public String toString() {
        return String.format("Triangle  [Color: %-6s | Base: %.1f, H: %.1f | Area: %.2f]", shapeColor, base, height, calcArea());
    }
}

class Circle extends Shape {
    private static final long serialVersionUID = 1L;
    private double radius;

    public Circle(String shapeColor, double radius) {
        super(shapeColor);
        this.radius = radius;
    }

    @Override
    public double calcArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public void draw() {
        System.out.println("Drawing Circle");
    }

    @Override
    public String toString() {
        return String.format("Circle    [Color: %-6s | Radius: %.1f      | Area: %.2f]", shapeColor, radius, calcArea());
    }
}


class AreaComparator implements Comparator<Shape> {
    @Override
    public int compare(Shape s1, Shape s2) {
        return Double.compare(s1.calcArea(), s2.calcArea());
    }
}

class ColorComparator implements Comparator<Shape> {
    @Override
    public int compare(Shape s1, Shape s2) {
        return s1.getShapeColor().compareTo(s2.getShapeColor());
    }
}

// клас для роботи з файлами
class ShapeFileManager {

    public static void saveShapesToFile(Shape[] shapes, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(shapes);
        }
    }

    public static Shape[] readShapesFromFile(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (Shape[]) ois.readObject();
        }
    }
}

// view

class ShapeView {
    private Scanner scanner = new Scanner(System.in);

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printShapes(Shape[] shapes) {
        if (shapes == null || shapes.length == 0) {
            System.out.println("Масив фігур порожній.");
            return;
        }
        for (Shape shape : shapes) {
            System.out.println(shape.toString());
        }
        System.out.println("--------------------------------------------------");
    }

    public void printMenu() {
        System.out.println("\n========== МЕНЮ ==========");
        System.out.println("1. Відобразити поточний набір фігур");
        System.out.println("2. Обчислити загальну площу");
        System.out.println("3. Обчислити площу за типом (Circle)");
        System.out.println("4. Сортувати за зростанням площі");
        System.out.println("5. Сортувати за кольором");
        System.out.println("6. Зберегти набір фігур у файл");
        System.out.println("7. Завантажити набір фігур з файлу");
        System.out.println("0. Вихід");
        System.out.print("Оберіть опцію: ");
    }

    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Некоректне введення! Введіть ціле число: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }
}

// controller

class ShapeController {
    private Shape[] shapes;
    private ShapeView view;

    public ShapeController(ShapeView view) {
        this.view = view;
        this.shapes = generateShapes(10);
    }

    private Shape[] generateShapes(int size) {
        Shape[] generated = new Shape[size];
        String[] colors = {"Red", "Blue", "Green", "Yellow", "Black", "White"};
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            String color = colors[random.nextInt(colors.length)];
            int type = random.nextInt(3);
            switch (type) {
                case 0:
                    generated[i] = new Rectangle(color, 2 + random.nextDouble() * 8, 2 + random.nextDouble() * 8);
                    break;
                case 1:
                    generated[i] = new Triangle(color, 2 + random.nextDouble() * 8, 2 + random.nextDouble() * 8);
                    break;
                case 2:
                    generated[i] = new Circle(color, 2 + random.nextDouble() * 8);
                    break;
            }
        }
        return generated;
    }

    public void run() {
        boolean running = true;
        while (running) {
            view.printMenu();
            int choice = view.readInt();

            switch (choice) {
                case 1:
                    view.printMessage("\n=== Поточний набір фігур ===");
                    view.printShapes(shapes);
                    break;
                case 2:
                    view.printMessage("Сумарна площа всіх фігур: " + String.format("%.2f", calculateTotalArea()));
                    break;
                case 3:
                    view.printMessage("Сумарна площа всіх Кіл (Circle): " + String.format("%.2f", calculateAreaByType(Circle.class)));
                    break;
                case 4:
                    view.printMessage("\n=== Впорядкування за збільшенням площі ===");
                    Arrays.sort(shapes, new AreaComparator());
                    view.printShapes(shapes);
                    break;
                case 5:
                    view.printMessage("\n=== Впорядкування за кольором ===");
                    Arrays.sort(shapes, new ColorComparator());
                    view.printShapes(shapes);
                    break;
                case 6:
                    saveToFile();
                    break;
                case 7:
                    loadFromFile();
                    break;
                case 0:
                    running = false;
                    view.printMessage("Завершення роботи.");
                    break;
                default:
                    view.printMessage("Невідома команда. Спробуйте ще раз.");
            }
        }
    }

    private void saveToFile() {
        String filename = view.readString("Введіть шлях/ім'я файлу для збереження (наприклад, shapes.dat): ");
        try {
            ShapeFileManager.saveShapesToFile(shapes, filename);
            view.printMessage("Дані успішно збережено у файл: " + filename);
        } catch (IOException e) {
            view.printMessage("Помилка під час збереження у файл: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        String filename = view.readString("Введіть шлях/ім'я файлу для зчитування: ");
        try {
            this.shapes = ShapeFileManager.readShapesFromFile(filename);
            view.printMessage("Дані успішно завантажено з файлу: " + filename);
        } catch (FileNotFoundException e) {
            view.printMessage("Помилка: Файл не знайдено!");
        } catch (ClassNotFoundException e) {
            view.printMessage("Помилка: Не вдалося розпізнати структуру даних (клас не знайдено).");
        } catch (IOException e) {
            view.printMessage("Помилка під час читання файлу: " + e.getMessage());
        }
    }

    private double calculateTotalArea() {
        double sum = 0;
        for (Shape shape : shapes) {
            sum += shape.calcArea();
        }
        return sum;
    }

    private double calculateAreaByType(Class<?> type) {
        double sum = 0;
        for (Shape shape : shapes) {
            if (type.isInstance(shape)) {
                sum += shape.calcArea();
            }
        }
        return sum;
    }
}

public class Main {
    public static void main(String[] args) {
        ShapeView view = new ShapeView();
        ShapeController controller = new ShapeController(view);
        controller.run();
    }
}
