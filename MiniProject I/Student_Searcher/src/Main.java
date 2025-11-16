import java.util.*;
import java.io.*;

public class Main {
    static ArrayList<Student> studentList = new ArrayList<>();
    static int nextId = 1; // incremental ID counter
    static final String FILE_NAME = "students.csv";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Load data when program starts
        loadFromCSV();

        boolean running = true;
        while (running) {
            clearConsole();
            menuDisplayStudent();
            char operation = operation(input);

            if (operation == 'q' || operation == 'Q') {
                System.out.println("💾 Saving data before exit...");
                saveToCSV();
                System.out.println("✅ Data saved. Goodbye!");
                running = false;
            }
            continueProgram(input);
        }
    }

    // -------------------- MENU --------------------
    public static void menuDisplayStudent() {
        System.out.println("=== STUDENT MANAGEMENT SYSTEM ===");
        System.out.println("1 --> Add Student");
        System.out.println("2 --> Search by Student/ID");
        System.out.println("3 --> Show All Students");
        System.out.println("4 --> Delete Student");
        System.out.println("5 --> Search by Class");
        System.out.println("q --> Quit");
        System.out.print("Enter your choice: ");
    }

    public static char operation(Scanner input) {
        String choice = input.next();
        input.nextLine();

        switch (choice) {
            case "1" -> addStudent(input);
            case "2" -> searchStudentMenu(input);
            case "3" -> showAllStudents();
            case "4" -> deleteMenu(input);
            case "5" -> searchByClass(input);
            case "q", "Q" -> { return 'q'; }
            default -> System.out.println("Invalid choice. Try again.");
        }
        return 0;
    }

    // -------------------- CORE FUNCTIONS --------------------
    public static void addStudent(Scanner input) {
        System.out.print("Enter Student Name: ");
        String name = input.nextLine().trim();

        String newId = generateIncrementalId();
        ArrayList<String> selectedClasses = new ArrayList<>();

        boolean addingClasses = true;
        while (addingClasses) {
            System.out.println("\nSelect Class to Add:");
            System.out.println("1. Math");
            System.out.println("2. Science");
            System.out.println("3. History");
            System.out.println("4. English");
            System.out.println("5. Computer");
            System.out.println("6. Art");
            System.out.println("0. Finish adding classes");
            System.out.print("Enter choice (0-6): ");
            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1 -> selectedClasses.add("Math");
                case 2 -> selectedClasses.add("Science");
                case 3 -> selectedClasses.add("History");
                case 4 -> selectedClasses.add("English");
                case 5 -> selectedClasses.add("Computer");
                case 6 -> selectedClasses.add("Art");
                case 0 -> addingClasses = false;
                default -> System.out.println("Invalid choice.");
            }
        }

        Student newStudent = new Student(name, newId, selectedClasses);
        studentList.add(newStudent);
        saveToCSV();

        clearConsole();
        System.out.println("✅ Student Added Successfully!");
        System.out.println("Student Name: " + name);
        System.out.println("Student ID: " + newId);
        System.out.println("Classes: " + selectedClasses);
    }

    public static void searchStudentMenu(Scanner input) {
        System.out.print("Enter Student Name or ID: ");
        String query = input.nextLine().trim().toLowerCase();
        clearConsole();
        boolean found = false;

        for (Student s : studentList) {
            if (s.name.toLowerCase().contains(query) || s.id.toLowerCase().contains(query)) {
                System.out.println("🎯 Student Found:");
                System.out.println("Name: " + s.name);
                System.out.println("ID: " + s.id);
                System.out.println("Classes: " + s.classes);
                System.out.println();
                found = true;
            }
        }

        if (!found) {
            System.out.println("⚠️ Student not found.");
        }
    }

    public static void showAllStudents() {
        if (studentList.isEmpty()) {
            System.out.println("⚠️ No students in the list.");
            return;
        }

        System.out.println("=== ALL STUDENTS ===");
        int index = 1;
        for (Student s : studentList) {
            System.out.println(index++ + ". Name: " + s.name +
                    " | ID: " + s.id +
                    " | Classes: " + s.classes);
        }
    }

    public static void deleteMenu(Scanner input) {
        if (studentList.isEmpty()) {
            System.out.println("⚠️ No students to delete.");
            return;
        }

        System.out.println("1 --> Delete a student by name/ID");
        System.out.println("2 --> Delete all students");
        System.out.print("Enter choice: ");
        int choice = input.nextInt();
        input.nextLine();

        if (choice == 1) {
            System.out.print("Enter Student Name or ID to delete: ");
            String query = input.nextLine().trim().toLowerCase();
            deleteStudent(query);
        } else if (choice == 2) {
            studentList.clear();
            System.out.println("✅ All students deleted.");
            saveToCSV();
        } else {
            System.out.println("Invalid choice.");
        }
    }

    public static void deleteStudent(String query) {
        boolean removed = false;
        for (int i = 0; i < studentList.size(); i++) {
            Student s = studentList.get(i);
            if (s.name.equalsIgnoreCase(query) || s.id.equalsIgnoreCase(query)) {
                System.out.println("🗑️ Deleted Student: " + s.name + " (" + s.id + ")");
                studentList.remove(i);
                removed = true;
                break;
            }
        }
        if (removed) saveToCSV();
        if (!removed) System.out.println("⚠️ Student not found.");
    }

    // -------------------- NEW FUNCTION: SEARCH BY CLASS --------------------
    public static void searchByClass(Scanner input) {
        System.out.print("Enter class name to search (e.g., Math): ");
        String className = input.nextLine().trim().toLowerCase();

        boolean found = false;
        System.out.println("\n=== Students in " + className.toUpperCase() + " ===");
        int count = 0;

        for (Student s : studentList) {
            for (String c : s.classes) {
                if (c.toLowerCase().equals(className)) {
                    count++;
                    System.out.println(count + ". " + s.name + " (" + s.id + ")");
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            System.out.println("⚠️ No students found in " + className + ".");
        }
    }

    // -------------------- CSV FUNCTIONS --------------------
    public static void saveToCSV() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Student s : studentList) {
                String classString = String.join(";", s.classes);
                writer.println(s.id + "," + s.name + "," + classString);
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving file: " + e.getMessage());
        }
    }

    public static void loadFromCSV() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int maxIdNum = 0;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length >= 3) {
                    String id = parts[0];
                    String name = parts[1];
                    String[] classArray = parts[2].split(";");
                    ArrayList<String> classList = new ArrayList<>(Arrays.asList(classArray));
                    studentList.add(new Student(name, id, classList));

                    try {
                        int num = Integer.parseInt(id.substring(2));
                        if (num > maxIdNum) maxIdNum = num;
                    } catch (Exception ignore) {}
                }
            }
            nextId = maxIdNum + 1;
        } catch (IOException e) {
            System.out.println("❌ Error loading file: " + e.getMessage());
        }
    }

    // -------------------- HELPERS --------------------
    public static String generateIncrementalId() {
        String formatted = String.format("ST%04d", nextId);
        nextId++;
        return formatted;
    }

    public static void clearConsole() {
        for (int i = 0; i < 25; i++) System.out.println();
    }

    public static void continueProgram(Scanner input) {
        System.out.println("\nPress Enter to continue...");
        input.nextLine();
        clearConsole();
    }
}

// -------------------- STUDENT CLASS --------------------
class Student {
    String name;
    String id;
    ArrayList<String> classes;

    Student(String name, String id, ArrayList<String> classes) {
        this.name = name;
        this.id = id;
        this.classes = classes;
    }
}