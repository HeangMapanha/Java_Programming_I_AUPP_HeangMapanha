import java.io.*;
import java.util.*;

public class StudentManager {

    private ArrayList<Student> studentList = new ArrayList<>();
    private static final String FILE_NAME = "students.csv";
    private int nextId = 1;
    private CourseManager cm;

    public StudentManager(CourseManager cm) {
        this.cm = cm;
        loadFromCSV(); // Load students on startup
    }

    // ---------------- ADD STUDENT ----------------
    public void addStudent(Scanner input) {
        System.out.print("Enter student name: ");
        String name = input.nextLine().trim();

        ArrayList<String> selectedCourses = new ArrayList<>();
        System.out.println("\n=== Available Courses ===");
        cm.listCourses(); // use the class-level cm reference
        System.out.println("Select course IDs:");
        System.out.println("Type 0 to cancel adding student.");
        System.out.println("Type 1 to finish selecting courses.");

        while (true) {
            System.out.print("Course ID: ");
            String cid = input.nextLine().trim();

            if (cid.equals("0")) { // cancel adding
                System.out.println("❌ Adding student canceled.");
                return;
            } else if (cid.equals("1")) { // finish selection
                if (selectedCourses.isEmpty()) {
                    System.out.println("⚠️ No courses selected yet. Select at least one course or cancel.");
                } else {
                    break;
                }
            } else {
                Course c = cm.getCourseById(cid);
                if (c == null) {
                    System.out.println("❌ Invalid ID or inactive.");
                } else if (selectedCourses.contains(c.getCourseName())) {
                    System.out.println("⚠️ Already selected.");
                } else {
                    selectedCourses.add(c.getCourseName());
                    System.out.println("✔ Added: " + c.getCourseName());
                }
            }
        }

        String id = String.format("ST%04d", nextId++);
        Student s = new Student(id, name, selectedCourses);
        studentList.add(s);
        saveToCSV();
        System.out.println("✅ Student added: " + s.getId());
    }



    // ---------------- DEACTIVATE STUDENT ----------------
    public void deleteStudent(Scanner input) {
        System.out.print("Enter name or ID to deactivate: ");
        String query = input.nextLine().trim().toLowerCase();
        boolean found = false;

        for (Student s : studentList) {
            if (s.getName().toLowerCase().contains(query) || s.getId().toLowerCase().contains(query)) {
                if (!s.getStatus().equalsIgnoreCase("inactive")) {
                    s.setStatus("inactive");
                    saveInactiveStudent(s);
                    saveToCSV();
                    System.out.println("✅ Student marked inactive: " + s.getId());
                } else {
                    System.out.println("⚠️ Already inactive: " + s.getId());
                }
                found = true;
            }
        }

        if (!found) {
            System.out.println("⚠️ No matching student.");
        }
    }

    // ---------------- SEARCH ----------------
    public void searchStudent(Scanner input) {
        System.out.print("Search by name or ID: ");
        String query = input.nextLine().trim().toLowerCase();
        boolean found = false;

        for (Student s : studentList) {
            if (s.getName().toLowerCase().contains(query) || s.getId().toLowerCase().contains(query)) {
                System.out.println(s);
                found = true;
            }
        }

        if (!found) System.out.println("⚠️ No student found.");
    }

    public void searchByClass(Scanner input) {
        System.out.print("Enter class name: ");
        String cls = input.nextLine().trim().toLowerCase();
        boolean found = false;

        for (Student s : studentList) {
            for (String c : s.getCourses()) {
                if (c.toLowerCase().equals(cls)) {
                    System.out.println(s);
                    found = true;
                }
            }
        }

        if (!found) System.out.println("⚠️ No students found for: " + cls);
    }

    // ---------------- SHOW ----------------
    public void showAllStudents() {
        if (studentList.isEmpty()) {
            System.out.println("⚠️ No students.");
        } else {
            studentList.forEach(System.out::println);
        }
    }

    // ---------------- VIEW INACTIVE ----------------
    public void viewInactiveStudents() {
        File f = new File("inactive_students.csv");
        if (!f.exists()) {
            System.out.println("No inactive students.");
            return;
        }
        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading inactive students.");
        }
    }

    // ---------------- SAVE INACTIVE ----------------
    private void saveInactiveStudent(Student s) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("inactive_students.csv", true))) {
            pw.println(s.toCSV());
        } catch (IOException e) {
            System.out.println("❌ Error saving inactive student.");
        }
    }

    // ---------------- CSV SAVE/LOAD ----------------
    public void saveToCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Student s : studentList) pw.println(s.toCSV());
        } catch (IOException e) {
            System.out.println("❌ Error saving students.");
        }
    }

    private void loadFromCSV() {
        File f = new File(FILE_NAME);
        if (!f.exists()) return;

        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                Student s = Student.fromCSV(sc.nextLine());
                studentList.add(s);

                int num = Integer.parseInt(s.getId().substring(2));
                if (num >= nextId) nextId = num + 1;
            }
        } catch (Exception e) {
            System.out.println("❌ Error loading students.");
        }
    }
}
