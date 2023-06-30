import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String pathToHelloWorldJava = "C:\\Users\\imants.brokans\\Downloads\\dataset_91022.txt";
        try {
            System.out.println(ReadingFileDemo.readFileAsString(pathToHelloWorldJava));
            String[] numbersArr = ReadingFileDemo.readFileAsString(pathToHelloWorldJava).split(" ");

            int greatestNumber = 9999;
            int count = 0;
            for (int i = 0; i < numbersArr.length; i++) {
                if (Integer.valueOf(numbersArr[i]) >= greatestNumber) {
                    count++;
                }
            }

            System.out.println("Full count is " + count);

        } catch (IOException e) {
            System.out.println("Cannot read file: " + e.getMessage());
        }
    }

    public static class ReadingFileDemo {
        public static String readFileAsString(String fileName) throws IOException {
            return new String(Files.readAllBytes(Paths.get(fileName)));
        }
    }
}