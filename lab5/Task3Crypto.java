import java.io.*;

class CipherOutputStream extends FilterOutputStream {
    private final char keyChar;

    public CipherOutputStream(OutputStream out, char keyChar) {
        super(out);
        this.keyChar = keyChar;
    }

    @Override
    public void write(int b) throws IOException {
        // Додаємо код ключового символу для шифрування
        int encryptedByte = b + keyChar;
        super.write(encryptedByte);
    }
}

class CipherInputStream extends FilterInputStream {
    private final char keyChar;

    public CipherInputStream(InputStream in, char keyChar) {
        super(in);
        this.keyChar = keyChar;
    }

    @Override
    public int read() throws IOException {
        int b = super.read();
        if (b == -1) {
            return -1; // Кінець потоку
        }
        return b - keyChar;
    }
}

public class Task3Crypto {

    // a. метод шифрування
    public static void encryptFile(String inputFile, String outputFile, char key) throws IOException {
        try (InputStream in = new FileInputStream(inputFile);
             CipherOutputStream out = new CipherOutputStream(new FileOutputStream(outputFile), key)) {

            int data;
            while ((data = in.read()) != -1) {
                out.write(data);
            }
        }
    }

    // b. метод дешифрування
    public static void decryptFile(String encryptedFile, String decryptedFile, char key) throws IOException {
        try (CipherInputStream in = new CipherInputStream(new FileInputStream(encryptedFile), key);
             OutputStream out = new FileOutputStream(decryptedFile)) {

            int data;
            while ((data = in.read()) != -1) {
                out.write(data);
            }
        }
    }

    public static void main(String[] args) {
        String originalFile = "source.txt";
        String encryptedFile = "encrypted.dat";
        String decryptedFile = "decrypted.txt";
        char key = 'K';

        try {
            try (FileWriter writer = new FileWriter(originalFile)) {
                writer.write("Hello World! Привіт Світ! 123");
            }

            encryptFile(originalFile, encryptedFile, key);
            System.out.println("Файл успішно зашифровано в " + encryptedFile);

            decryptFile(encryptedFile, decryptedFile, key);
            System.out.println("Файл успішно розшифровано в " + decryptedFile);

        } catch (IOException e) {
            System.err.println("Помилка під час обробки файлів: " + e.getMessage());
        }
    }
}
