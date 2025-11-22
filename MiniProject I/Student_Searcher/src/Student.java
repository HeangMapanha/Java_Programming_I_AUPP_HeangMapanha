import java.util.*;

public class Student {
    private String id;
    private String name;
    private ArrayList<String> courses;
    private String status; // "active" or "inactive"

    public Student(String id, String name, ArrayList<String> courses) {
        this.id = id;
        this.name = name;
        this.courses = courses != null ? courses : new ArrayList<>();
        this.status = "active";
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public ArrayList<String> getCourses() { return courses; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return id + " | " + name + " | " + status + " | " + String.join(", ", courses);
    }

    public String toCSV() {
        return id + "," + name + "," + status + "," + String.join(";", courses);
    }

    public static Student fromCSV(String line) {
        String[] parts = line.split(",", 4); // avoid ArrayIndexOutOfBounds
        String id = parts[0];
        String name = parts.length > 1 ? parts[1] : "";
        String status = parts.length > 2 ? parts[2] : "active";
        ArrayList<String> courses = new ArrayList<>();
        if (parts.length > 3 && !parts[3].isEmpty()) {
            courses.addAll(Arrays.asList(parts[3].split(";")));
        }
        Student s = new Student(id, name, courses);
        s.setStatus(status);
        return s;
    }
}
