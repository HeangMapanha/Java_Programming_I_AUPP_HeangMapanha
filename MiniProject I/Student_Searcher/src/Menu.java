import java.util.Scanner;

public class Menu {
    private StudentManager sm;
    private CourseManager cm;
    private Scanner input = new Scanner(System.in);

    public Menu(StudentManager sm, CourseManager cm) { this.sm = sm; this.cm = cm; }

    public void start() {
        while (true) {
            System.out.println("\n=== STUDENT MANAGEMENT SYSTEM ===");
            System.out.println("1 --> Add Student");
            System.out.println("2 --> Search");
            System.out.println("3 --> Show All Students");
            System.out.println("4 --> Deactivate Student");
            System.out.println("5 --> Manage Courses");
            System.out.println("6 --> View Inactive Students");
            System.out.println("q --> Quit");
            System.out.print("Choice: ");
            String choice = input.nextLine().trim();
            switch (choice) {
                case "1" -> sm.addStudent(input);
                case "2" -> searchMenu();
                case "3" -> sm.showAllStudents();
                case "4" -> sm.deleteStudent(input);
                case "5" -> manageCourses();
                case "6" -> sm.viewInactiveStudents();
                case "q","Q" -> { sm.saveToCSV(); System.out.println("Goodbye!"); return; }
                default -> System.out.println("Invalid choice.");
            }
            System.out.println("\nPress Enter to continue...");
            input.nextLine();
        }
    }

    private void searchMenu() {
        while (true) {
            System.out.println("\n=== SEARCH MENU ===");
            System.out.println("1. Search by Student Name/ID");
            System.out.println("2. Search by Class");
            System.out.println("3. Back");
            System.out.print("Choice: ");
            String choice = input.nextLine().trim();
            switch (choice) {
                case "1" -> sm.searchStudent(input);
                case "2" -> sm.searchByClass(input);
                case "3" -> { return; }
                default -> System.out.println("Invalid choice.");
            }
            System.out.println("\nPress Enter to continue...");
            input.nextLine();
        }
    }

    private void manageCourses() {
        while (true) {
            System.out.println("\n=== COURSE MANAGEMENT ===");
            System.out.println("1. Add Course");
            System.out.println("2. List Courses");
            System.out.println("3. Back");
            System.out.print("Choice: ");
            String choice = input.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    System.out.print("Course name: ");
                    cm.addCourse(input.nextLine().trim());
                }
                case "2" -> cm.listCourses();
                case "3" -> { return; }
                default -> System.out.println("Invalid choice.");
            }

            System.out.println("\nPress Enter to continue...");
            input.nextLine();
        }
    }

}
