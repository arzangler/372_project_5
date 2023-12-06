import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
        Scanner scanner = new Scanner(System.in);

        // TODO CHANGES

        System.out.print("Enter the starting number of sticks for pile 1: ");
        String pileOneStart = scanner.nextLine();
        System.out.print("Enter the starting number of sticks for pile 2: ");
        int pileTwoStart = scanner.nextInt();
        System.out.print("Enter the starting number of sticks for pile 3: ");
        int pileThreeStart = scanner.nextInt();

        System.out.println("Pile 1: " + pileOneStart + " Pile 2: " + pileTwoStart + " Pile 3: " + pileThreeStart);

        System.out.print("How many games do you want to run via q-learning: ");
        int gamesToRun = scanner.nextInt();

        System.out.println("Q-learning games to run " + gamesToRun);


        State testState = new State("A12301");
        System.out.println(testState.toString());
    }
}
