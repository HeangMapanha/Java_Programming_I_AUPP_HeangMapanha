import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    static ArrayList<String> studentList = new ArrayList<>();
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean running = true;

        while (running) {
            clearConsole();
            boolean found = false;
            menuDisplayStudent();
            char operation = operation(input);


            if (operation == 'q' || operation == 'Q') {
                System.out.println("Thanks for using the calculator. Goodbye!");
                running = false;
            }
            continueProgram(input);
        }
    }

    public static void menuDisplayStudent() {
        System.out.println("Welcome to ID searcher.");
        System.out.println("1 --> add Student");
        System.out.println("2 --> search by Student/ID");
        System.out.println("3 --> show all Student/ID");
        System.out.println("4 --> delete Student/ID");
        System.out.println("q to quit");
    }

    public static char operation(Scanner input) {
        System.out.print("Enter your choice (1, 2, 3, q): ");
        String choice = input.next();
        input.nextLine();

        switch (choice) {
            case "1":
                System.out.println("Enter Student Name: ");
                String user_input_name = input.nextLine().toLowerCase();
                String new_student = idGen_combine(user_input_name);
                clearConsole();

                System.out.println("Student Added Successfully! ");
                System.out.println("Student Name: " + user_input_name);
                System.out.println("Student ID: " + extractID(new_student));
                return 0;
            case "2":
                System.out.println("Enter Student/ID: ");
                String query = input.nextLine().toLowerCase();
                clearConsole();
                searchStudent(query,studentList);
                return 0;
            case "3":
                clearConsole();
                searchAllStudent(studentList);
                return 0;
            case "4":
                clearConsole();
                System.out.println("Choose delete method:");
                System.out.println("[1] --> delete a student/ID");
                System.out.println("[2] --> delete all student/ID");
                System.out.println("--> ");
                int user_delete_choice = input.nextInt();
                input.nextLine();
                if  (user_delete_choice == 1) {
                    String user_student_delete_input = input.nextLine().toLowerCase();
                    clearConsole();
                    deleteStudent(user_student_delete_input,studentList);
                    return 0;
                }
                else if (user_delete_choice == 2) {
                    studentList.clear();
                    clearConsole();
                    System.out.println("List Cleared");
                }
                else {
                    System.out.println("Invalid choice");
                }
                return 0;
            case "q":
            case "Q":
                return 'q';
            default:
                System.out.println("Invalid choice. Try again.");
                continueProgram(input);
                return 0;
        }
    }

    // ------------- Methods ---------------

    public static void clearConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public static String idGen_combine(String input) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        java.util.Random rand = new java.util.Random();
        String newID = "";
        do {
            newID = "";
            for (int i = 0; i < 2; i++) {
                int index = rand.nextInt(characters.length());
                newID += characters.charAt(index%26) + "";
            }
            for (int i = 0; i < 6; i++) {
                int index = rand.nextInt(characters.length());
                newID += characters.charAt(index); // simple String concatenation
            }


        } while (studentList.contains(newID));
        studentList.add(input + newID);
        return newID;
    }

    public static void searchStudent(String query, ArrayList<String> List) {
        boolean found = false;

        for (String student : List) {
            // Assume ID is last 8 characters, name is the rest
            String namePart = student.substring(0, student.length() - 8);
            String idPart = student.substring(student.length() - 8);

            if (namePart.toLowerCase().contains(query) || idPart.toLowerCase().contains(query)) {
                System.out.println("Student Found");
                System.out.println("Student name: " + namePart);
                System.out.println("Student ID: " + idPart);
                System.out.println(" ");

                found = true;
            }
        }

        if (!found) {
            System.out.println("Student not found.");
        }
    }
    public static void searchAllStudent(ArrayList<String> List) {
        int index = 0;
        boolean found = false;
        for (String student : List) {
            String namePart = student.substring(0, student.length() - 8);
            String idPart = student.substring(student.length() - 8);
            ++index;
            System.out.println(index +".Student Name: " + namePart + ", student ID: " + idPart);
            found = true;
        }
        if  (!found) {
            System.out.println("List is empty.");
        }
    }

    public static void deleteStudent(String query, ArrayList<String> List){
        boolean found = false;

        for (int i = 0; i < List.size(); i++) {
            // Assume ID is last 8 characters, name is the rest
            String student = List.get(i);
            String namePart = student.substring(0, student.length() - 8);
            String idPart = student.substring(student.length() - 8);

            if (query.equalsIgnoreCase(namePart) || query.equalsIgnoreCase(idPart)) {
                System.out.println("Student Deleted");
                System.out.println("Student name: " + namePart);
                System.out.println("Student ID: " + idPart);
                studentList.remove(i);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Student not found.");
        }
    }

    public static String extractID(String combined) {
        return combined.substring(combined.length() - 8);
    }
    public static void continueProgram(Scanner input) {
        System.out.println("Press Enter to continue...");
        input.nextLine(); // waits until user presses Enter
        clearConsole();   // clears screen after pressing Enter
    }
}

