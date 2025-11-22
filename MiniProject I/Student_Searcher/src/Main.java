public class Main {
    public static void main(String[] args) {
        // Initialize CourseManager (loads courses from CSV)
        CourseManager courseManager = new CourseManager();

        // Initialize StudentManager with CourseManager
        StudentManager manager = new StudentManager(courseManager);

        // Launch the menu
        Menu menu = new Menu(manager, courseManager);
        menu.start();
    }
}
