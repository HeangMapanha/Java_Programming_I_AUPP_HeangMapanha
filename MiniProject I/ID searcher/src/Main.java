import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean running = true;

        while (running) {
            menuDisplayStudent();
            char operation = operation(input);

            if (operation == 'q' || operation == 'Q') {
                System.out.println("Thanks for using the calculator. Goodbye!");
                running = false;
            } else {
                System.out.println("Thank you for using the calculator. Goodbye!");
            }
        }
        input.close();
    }

    public static void menuDisplayStudent() {
        System.out.println("Welcome to ID search.");
        System.out.println("Who would you like to find?:");
        System.out.println("1 to add ID");
        System.out.println("2 to add search");
        System.out.println("3 to see all the student ID/ name");
        System.out.println("q to quit");
    }

    public static char operation(Scanner input) {
        System.out.print("Enter your choice (1, 2, 3, q): ");
        String choice = input.next();

        switch (choice) {
            case "1":
                System.out.println("who would you like to add: ");
            case "2":
                System.out.println("who would you like to search: ");
            case "3":
                System.out.println("Display ID/Names");
            case "q":
            case "Q":
                return 'q';
            default:
                System.out.println("Invalid choice. Try again.");
                return operation(input);
        }
    }
}

