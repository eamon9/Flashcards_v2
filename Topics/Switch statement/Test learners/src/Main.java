import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int userInput = input.nextInt();
        final int a1 = 1;
        final int a2 = 2;
        final int a3 = 3;
        final int a4 = 4;
        switch (userInput) {
            case a1:
                System.out.println("Yes!");
                break;
            case a2:
            case a3:
            case a4:
                System.out.println("No!");
                break;
            default:
                System.out.println("Unknown number");
        }
    }
}