import java.util.*;
import java.io.*;

public class Main {

    static ArrayList<Student> studentList = new ArrayList<>();
    static final String FILE_NAME = "students.csv";
    static int nextId = 1;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        loadFromCSV();

        while (true) {
            System.out.println("=== STUDENT MANAGEMENT SYSTEM ===");
            System.out.println("1 --> Add Student");
            System.out.println("2 --> Search by Student/ID");
            System.out.println("3 --> Show All Students");
            System.out.println("4 --> Delete Student");
            System.out.println("5 --> Search by Class");
            System.out.println("q --> Quit");
            System.out.print("Enter your choice: ");

            String choice = input.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    addStudent(input);
                    continueProgram(input);
                }
                case "2" -> {
                    searchStudent(input);
                    continueProgram(input);
                }
                case "3" -> {
                    showAllStudents();
                    continueProgram(input);
                }
                case "4" -> {
                    deleteMenu(input);
                    continueProgram(input);
                }
                case "5" -> {
                    searchByClass(input);
                    continueProgram(input);
                }
                case "q", "Q" -> {
                    saveToCSV();
                    System.out.println("Goodbye!");
                    return;
                }
                default -> {
                    System.out.println("Invalid choice.");
                    continueProgram(input);
                }
            }
        }
    }

    // ---------------- ADD STUDENT ----------------

    public static void addStudent(Scanner input) {
        System.out.print("Enter student name: ");
        String name = input.nextLine().trim();

        System.out.println("Select classes (0 to finish):");
        System.out.println("1. Math");
        System.out.println("2. Science");
        System.out.println("3. History");
        System.out.println("4. English");
        System.out.println("5. Computer");
        System.out.println("6. Art");

        ArrayList<String> selected = new ArrayList<>();

        while (true) {
            System.out.print("Choose class: ");
            String c = input.nextLine().trim();

            if (c.equals("0")) break;

            switch (c) {
                case "1" -> addClass(selected, "Math");
                case "2" -> addClass(selected, "Science");
                case "3" -> addClass(selected, "History");
                case "4" -> addClass(selected, "English");
                case "5" -> addClass(selected, "Computer");
                case "6" -> addClass(selected, "Art");
                default -> System.out.println("Invalid class.");
            }
        }

        String id = String.format("ST%04d", nextId++);
        studentList.add(new Student(id, name, selected));
        saveToCSV();

        System.out.println("✅ Student added: " + id);
    }

    private static void addClass(ArrayList<String> list, String cls) {
        if (!list.contains(cls)) {
            list.add(cls);
            System.out.println("✔ " + cls + " added.");
        } else {
            System.out.println("⚠️ " + cls + " already added!");
        }
    }

    // ---------------- SEARCH STUDENT ----------------

    public static void searchStudent(Scanner input) {
        System.out.print("Search by name or ID: ");
        String query = input.nextLine().trim().toLowerCase();

        boolean found = false;
        for (Student s : studentList) {
            if (s.name.toLowerCase().contains(query) || s.id.toLowerCase().contains(query)) {
                System.out.println(s);
                found = true;
            }
        }

        if (!found) System.out.println("⚠️ No student found.");
    }

    // ---------------- SHOW ALL ----------------

    public static void showAllStudents() {
        if (studentList.isEmpty()) {
            System.out.println("⚠️ No students available.");
            return;
        }

        for (Student s : studentList) System.out.println(s);
    }

    // ---------------- DELETE MENU ----------------

    public static void deleteMenu(Scanner input) {
        if (studentList.isEmpty()) {
            System.out.println("⚠️ No students to delete.");
            return;
        }

        System.out.println("1 --> Delete a student by name/ID");
        System.out.println("2 --> Delete all students");
        System.out.print("Enter choice: ");

        String choice = input.nextLine().trim();

        switch (choice) {
            case "1" -> {
                System.out.print("Enter name or ID: ");
                String q = input.nextLine().trim().toLowerCase();
                deleteStudent(q);
            }
            case "2" -> {
                studentList.clear();
                saveToCSV();
                System.out.println("✅ All students deleted.");
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    public static void deleteStudent(String query) {
        boolean removed = studentList.removeIf(s -> s.name.toLowerCase().contains(query) || s.id.toLowerCase().contains(query));

        if (removed) {
            saveToCSV();
            System.out.println("✅ Student deleted.");
        } else {
            System.out.println("⚠️ No matching student.");
        }
    }

    // ---------------- SEARCH BY CLASS ----------------

    public static void searchByClass(Scanner input) {
        System.out.print("Enter class name (Math, Computer, etc.): ");
        String cls = input.nextLine().trim().toLowerCase();

        boolean found = false;
        for (Student s : studentList) {
            for (String c : s.classes) {
                if (c.toLowerCase().equals(cls)) {
                    System.out.println(s);
                    found = true;
                }
            }
        }

        if (!found) System.out.println("⚠️ No students found for class: " + cls);
    }

    // ---------------- FILE I/O ----------------

    public static void saveToCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Student s : studentList) {
                pw.println(s.toCSV());
            }
        } catch (Exception e) {
            System.out.println("❌ Error saving file.");
        }
    }

    public static void loadFromCSV() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Student s = Student.fromCSV(line);
                studentList.add(s);

                int num = Integer.parseInt(s.id.substring(2));
                if (num >= nextId) nextId = num + 1;
            }
        } catch (Exception e) {
            System.out.println("❌ Error loading file.");
        }
    }

    // ---------------- CONTINUE PROGRAM ----------------

    public static void continueProgram(Scanner input) {
        System.out.println("\nPress Enter to continue...");
        input.nextLine();
    }
}

// ================== STUDENT CLASS ==================

class Student {
    String id;
    String name;
    ArrayList<String> classes;

    public Student(String id, String name, ArrayList<String> classes) {
        this.id = id;
        this.name = name;
        this.classes = classes;
    }

    @Override
    public String toString() {
        return id + " | " + name + " [" + String.join(", ", classes)+ "]";
    }

    public String toCSV() {
        return id + "," + name + "," + String.join(";", classes);
    }

    public static Student fromCSV(String line) {
        String[] p = line.split(",");
        String id = p[0];
        String name = p[1];
        ArrayList<String> cls = new ArrayList<>();
        if (p.length > 2 && !p[2].isEmpty()) cls.addAll(Arrays.asList(p[2].split(";")));
        return new Student(id, name, cls);
    }
}
