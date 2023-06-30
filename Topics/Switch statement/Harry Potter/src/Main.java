import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        // start coding here
        String house = input.nextLine();

        switch (house) {
            case "gryffindor" -> System.out.println("bravery");
            case "hufflepuff" -> System.out.println("loyalty");
            case "slytherin" -> System.out.println("cunning");
            case "ravenclaw" -> System.out.println("intellect");
            default -> System.out.println("not a valid house");
        }
    }
}