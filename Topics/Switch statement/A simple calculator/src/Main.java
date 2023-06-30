import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        long firstNr = input.nextLong();
        String operator = input.next();
        long secondNr = input.nextLong();

        switch (operator) {
            case "+":
                System.out.println(firstNr + secondNr);
                break;
            case "-":
                System.out.println(firstNr - secondNr);
                break;
            case "*":
                System.out.println(firstNr * secondNr);
                break;
            case "/":
                if (secondNr == 0) {
                    System.out.println("Division by 0!");
                } else {
                    System.out.println(firstNr / secondNr);
                }
                break;
            default:
                System.out.println("Unknown operator");
                break;
        }
    }
}