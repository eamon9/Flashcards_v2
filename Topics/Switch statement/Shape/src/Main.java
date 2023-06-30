import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int shapeNr = input.nextInt();
        final int square = 1;
        final int circle = 2;
        final int triangle = 3;
        final int rhombus = 4;
        String[] shapes = {"square", "circle", "triangle", "rhombus"};
        String youHaveChosen = "You have chosen a ";

        switch (shapeNr) {
            case square:
                System.out.println(youHaveChosen + shapes[0]);
                break;
            case circle:
                System.out.println(youHaveChosen + shapes[1]);
                break;
            case triangle:
                System.out.println(youHaveChosen + shapes[2]);
                break;
            case rhombus:
                System.out.println(youHaveChosen + shapes[3]);
                break;
            default:
                System.out.println("There is no such shape!");
                break;
        }
    }
}