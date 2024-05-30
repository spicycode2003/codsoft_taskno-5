import java.util.*;

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private List<String> enrolledStudents;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = new ArrayList<>();
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public int getVacancy() {
        return capacity - enrolledStudents.size();
    }

    public List<String> getEnrolledStudents() {
        return enrolledStudents;
    }

    public boolean registerStudent(String studentId) {
        if (enrolledStudents.size() < capacity) {
            enrolledStudents.add(studentId);
            return true;
        }
        return false;
    }

    public boolean removeStudent(String studentId) {
        return enrolledStudents.remove(studentId);
    }

    @Override
    public String toString() {
        return courseCode + " - " + title + " (" + enrolledStudents.size() + "/" + capacity + ")";
    }
}

class Student {
    private String studentId;
    private String name;
    private List<String> registeredCourses;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public List<String> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(String courseCode) {
        registeredCourses.add(courseCode);
    }

    public void dropCourse(String courseCode) {
        registeredCourses.remove(courseCode);
    }
}

public class CourseRegistrationSystem {
    public static void main(String[] args) {
        // Create courses
        Course[] courses = {
                new Course("CS101", "Introduction to Computer Science", "An introductory course to computer science concepts.", 20, "MWF 10:00 AM - 11:00 AM"),
                new Course("ENG201", "English Literature", "A study of classic works of English literature.", 20, "TR 1:00 PM - 2:30 PM"),
                new Course("MATH301", "Calculus", "An advanced course in calculus.", 20, "MWF 2:00 PM - 3:30 PM"),
                new Course("PHYS101", "Physics", "Basic principles of physics.", 20, "TR 10:00 AM - 11:30 AM"),
                new Course("CHEM201", "Chemistry", "Introduction to chemical concepts.", 20, "MWF 9:00 AM - 10:30 AM"),
                new Course("HIST101", "World History", "A survey of world history from ancient times to the present.", 20, "TR 9:00 AM - 10:30 AM")
        };

        // Create scanner object for user input
        Scanner scanner = new Scanner(System.in);

        // Welcome message and student details
        System.out.println("Welcome to Course Registration System");
        System.out.print("Enter your name: ");
        String studentName = scanner.nextLine();
        System.out.print("Enter your student ID: ");
        String studentId = scanner.nextLine();
        Student student = new Student(studentId, studentName);

        // Main loop
        boolean exit = false;
        while (!exit) {
            // Display options
            System.out.println("\nOptions:");
            System.out.println("1. Register for available courses");
            System.out.println("2. View registration info");
            System.out.println("3. Drop a registered course");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            // Perform action based on user choice
            switch (choice) {
                case 1:
                    System.out.println("\nAvailable Courses:");
                    for (Course course : courses) {
                        System.out.println(course + " - Vacancy: " + course.getVacancy());
                        System.out.println("Description: " + course.getDescription());
                        System.out.println("Schedule: " + course.getSchedule());
                    }
                    System.out.println("Select a course to register (enter course code):");
                    String courseCode = scanner.nextLine();
                    Course selectedCourse = findCourseByCode(courses, courseCode);
                    if (selectedCourse != null) {
                        if (selectedCourse.registerStudent(student.getStudentId())) {
                            student.registerCourse(selectedCourse.getCourseCode());
                            System.out.println("Successfully registered for " + selectedCourse.getTitle());
                        } else {
                            System.out.println("Course is full. Please select another course.");
                        }
                    } else {
                        System.out.println("Invalid course code. Please try again.");
                    }
                    break;
                case 2:
                    System.out.println("\nRegistration Information:");
                    System.out.println("Name: " + student.getName());
                    System.out.println("Student ID: " + student.getStudentId());
                    System.out.println("Registered Courses:");
                    for (String code : student.getRegisteredCourses()) {
                        Course c = findCourseByCode(courses, code);
                        if (c != null) {
                            System.out.println("- " + c.getTitle());
                        }
                    }
                    break;
                case 3:
                    System.out.println("\nDrop a Registered Course:");
                    System.out.println("Enter course code to drop:");
                    String dropCourseCode = scanner.nextLine();
                    Course dropCourse = findCourseByCode(courses, dropCourseCode);
                    if (dropCourse != null && student.getRegisteredCourses().contains(dropCourse.getCourseCode())) {
                        dropCourse.removeStudent(student.getStudentId());
                        student.dropCourse(dropCourse.getCourseCode());
                        System.out.println("Successfully dropped " + dropCourse.getTitle());
                    } else {
                        System.out.println("Invalid course code or you are not registered for this course.");
                    }
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.println("Thank you for using Course Registration System");
    }

    // Helper method to find a course by its course code
    private static Course findCourseByCode(Course[] courses, String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                return course;
            }
        }
        return null;
    }
}
