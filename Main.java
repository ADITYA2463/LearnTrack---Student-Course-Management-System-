package com.airtribe.learntrack.ui;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.StudentService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();
    private static final EnrollmentService enrollmentService =
            new EnrollmentService(studentService, courseService);

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println(" Welcome to LearnTrack");
        System.out.println(" Student & Course Management System");
        System.out.println("========================================");

        boolean running = true;
        while (running) {
            showMenu();
            int choice = readInt("Choose an option: ");
            try {
                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> listStudents();
                    case 3 -> searchStudent();
                    case 4 -> updateStudent();
                    case 5 -> changeStudentStatus(false);
                    case 6 -> changeStudentStatus(true);
                    case 7 -> addCourse();
                    case 8 -> listCourses();
                    case 9 -> searchCourse();
                    case 10 -> changeCourseStatus();
                    case 11 -> enrollStudent();
                    case 12 -> listStudentEnrollments();
                    case 13 -> listAllEnrollments();
                    case 14 -> updateEnrollmentStatus();
                    case 0 -> {
                        running = false;
                        System.out.println("Thank you for using LearnTrack. Goodbye!");
                    }
                    default -> System.out.println("Invalid menu option. Please choose an option shown above.");
                }
            } catch (EntityNotFoundException | InvalidInputException | IllegalArgumentException ex) {
                System.out.println("Error: " + ex.getMessage());
            } catch (Exception ex) {
                // Final safety net: avoid displaying a stack trace for normal console mistakes.
                System.out.println("Something went wrong. Please check your input and try again.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void showMenu() {
        System.out.println("--------------- MAIN MENU ---------------");
        System.out.println("STUDENTS");
        System.out.println("1. Add student");
        System.out.println("2. View all students");
        System.out.println("3. Search student by ID");
        System.out.println("4. Update student");
        System.out.println("5. Deactivate student");
        System.out.println("6. Activate student");
        System.out.println("COURSES");
        System.out.println("7. Add course");
        System.out.println("8. View all courses");
        System.out.println("9. Search course by ID");
        System.out.println("10. Activate/deactivate course");
        System.out.println("ENROLLMENTS");
        System.out.println("11. Enroll student in a course");
        System.out.println("12. View enrollments for a student");
        System.out.println("13. View all enrollments");
        System.out.println("14. Update enrollment status");
        System.out.println("0. Exit");
    }

    private static void addStudent() {
        String firstName = readText("First name: ");
        String lastName = readText("Last name: ");
        String email = readText("Email (optional; press Enter to skip): ");
        String batch = readText("Batch: ");
        Student student = studentService.addStudent(firstName, lastName, email, batch);
        System.out.println("Student added successfully. " + student);
    }

    private static void listStudents() {
        List<Student> students = studentService.listStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        for (Student student : students) System.out.println(student);
    }

    private static void searchStudent() throws EntityNotFoundException {
        Student student = studentService.findStudentById(readInt("Enter student ID: "));
        System.out.println(student);
    }

    private static void updateStudent() throws EntityNotFoundException {
        int id = readInt("Student ID to update: ");
        String firstName = readText("New first name: ");
        String lastName = readText("New last name: ");
        String email = readText("New email (optional; Enter to clear): ");
        String batch = readText("New batch: ");
        System.out.println("Student updated: " +
                studentService.updateStudent(id, firstName, lastName, email, batch));
    }

    private static void changeStudentStatus(boolean active) throws EntityNotFoundException {
        int id = readInt("Student ID: ");
        Student student = active ? studentService.activateStudent(id) : studentService.deactivateStudent(id);
        System.out.println("Student status updated: " + student);
    }

    private static void addCourse() {
        String name = readText("Course name: ");
        String description = readText("Description: ");
        int duration = readInt("Duration in weeks: ");
        Course course = courseService.addCourse(name, description, duration);
        System.out.println("Course added successfully. " + course);
    }

    private static void listCourses() {
        List<Course> courses = courseService.listCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        for (Course course : courses) System.out.println(course);
    }

    private static void searchCourse() throws EntityNotFoundException {
        System.out.println(courseService.findCourseById(readInt("Enter course ID: ")));
    }

    private static void changeCourseStatus() throws EntityNotFoundException {
        int id = readInt("Course ID: ");
        Course course = courseService.findCourseById(id);
        System.out.println("Current active status: " + course.isActive());
        String answer = readText("Activate this course? (y/n): ");
        if (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n")) {
            throw new IllegalArgumentException("Please enter y or n.");
        }
        System.out.println("Course status updated: " +
                courseService.setCourseActive(id, answer.equalsIgnoreCase("y")));
    }

    private static void enrollStudent() throws EntityNotFoundException, InvalidInputException {
        int studentId = readInt("Student ID: ");
        int courseId = readInt("Course ID: ");
        Enrollment enrollment = enrollmentService.enrollStudent(studentId, courseId);
        System.out.println("Enrollment created successfully. " + enrollment);
    }

    private static void listStudentEnrollments() throws EntityNotFoundException {
        int studentId = readInt("Student ID: ");
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsForStudent(studentId);
        if (enrollments.isEmpty()) {
            System.out.println("This student has no enrollments.");
            return;
        }
        for (Enrollment enrollment : enrollments) {
            printEnrollmentWithNames(enrollment);
        }
    }

    private static void listAllEnrollments() {
        List<Enrollment> enrollments = enrollmentService.listEnrollments();
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found.");
            return;
        }
        for (Enrollment enrollment : enrollments) printEnrollmentWithNames(enrollment);
    }

    private static void printEnrollmentWithNames(Enrollment enrollment) {
        String studentName = "Unknown student";
        String courseName = "Unknown course";
        try {
            studentName = studentService.findStudentById(enrollment.getStudentId()).getFirstName()
                    + " " + studentService.findStudentById(enrollment.getStudentId()).getLastName();
        } catch (EntityNotFoundException ignored) {
            // Keep the enrollment visible even if its student record is unavailable.
        }
        try {
            courseName = courseService.findCourseById(enrollment.getCourseId()).getCourseName();
        } catch (EntityNotFoundException ignored) {
            // Keep the enrollment visible even if its course record is unavailable.
        }
        System.out.println(enrollment + " | Student: " + studentName + " | Course: " + courseName);
    }

    private static void updateEnrollmentStatus() throws EntityNotFoundException, InvalidInputException {
        int id = readInt("Enrollment ID: ");
        System.out.println("Allowed statuses: ACTIVE, COMPLETED, CANCELLED");
        String status = readText("New status: ").toUpperCase();
        System.out.println("Enrollment updated: " + enrollmentService.updateEnrollmentStatus(id, status));
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static String readText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
