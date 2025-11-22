import java.io.*;
import java.util.*;

public class CourseManager {
    private ArrayList<Course> courseList = new ArrayList<>();
    private static final String FILE_NAME = "courses.csv";
    private int nextCourseNumber = 1;

    public CourseManager() {
        loadCourses();
    }

    // ---------- Add Course ----------
    public void addCourse(String courseName) {
        String id = String.format("C%03d", nextCourseNumber++);
        Course newCourse = new Course(id, courseName);
        courseList.add(newCourse);
        saveCourses();
        System.out.println("✅ Course added: " + newCourse);
    }

    // ---------- List Courses ----------
    public void listCourses() {
        System.out.println("\n=== Courses ===");
        if (courseList.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }
        for (Course c : courseList) System.out.println(c);
    }

    // ---------- Get Course by ID ----------
    public Course getCourseById(String id) {
        for (Course c : courseList) {
            if (c.getCourseId().equalsIgnoreCase(id)) return c;
        }
        return null;
    }

    // ---------- Save Courses to CSV ----------
    private void saveCourses() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Course c : courseList) {
                pw.println(c.toCSV());
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving courses: " + e.getMessage());
        }
    }

    // ---------- Load Courses from CSV ----------
    private void loadCourses() {
        File f = new File(FILE_NAME);
        if (!f.exists()) return;

        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                Course c = Course.fromCSV(sc.nextLine());
                courseList.add(c);
                int num = Integer.parseInt(c.getCourseId().substring(1));
                if (num >= nextCourseNumber) nextCourseNumber = num + 1;
            }
        } catch (IOException e) {
            System.out.println("❌ Error loading courses: " + e.getMessage());
        }
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
    }
}
