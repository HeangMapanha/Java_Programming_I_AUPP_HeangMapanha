public class Course {
    private String courseId;
    private String courseName;

    public Course(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    @Override
    public String toString() {
        return courseId + " | " + courseName;
    }

    public String toCSV() {
        return courseId + "," + courseName;
    }

    public static Course fromCSV(String line) {
        String[] parts = line.split(",");
        return new Course(parts[0], parts[1]);
    }
}
