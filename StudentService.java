package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private final List<Student> students = new ArrayList<>();

    public Student addStudent(String firstName, String lastName, String email, String batch) {
        firstName = InputValidator.requireText(firstName, "First name");
        lastName = InputValidator.requireText(lastName, "Last name");
        batch = InputValidator.requireText(batch, "Batch");
        if (email != null && !email.trim().isEmpty() && !email.contains("@")) {
            throw new IllegalArgumentException("Email must contain '@', or be left blank.");
        }

        Student student = new Student(IdGenerator.getNextStudentId(), firstName, lastName,
                email == null || email.trim().isEmpty() ? null : email.trim(), batch);
        students.add(student);
        return student;
    }

    public List<Student> listStudents() {
        return new ArrayList<>(students);
    }

    public Student findStudentById(int id) throws EntityNotFoundException {
        for (Student student : students) {
            if (student.getId() == id) return student;
        }
        throw new EntityNotFoundException("Student with ID " + id + " was not found.");
    }

    public Student updateStudent(int id, String firstName, String lastName, String email, String batch)
            throws EntityNotFoundException {
        Student student = findStudentById(id);
        student.setFirstName(InputValidator.requireText(firstName, "First name"));
        student.setLastName(InputValidator.requireText(lastName, "Last name"));
        if (email != null && !email.trim().isEmpty() && !email.contains("@")) {
            throw new IllegalArgumentException("Email must contain '@', or be left blank.");
        }
        student.setEmail(email == null || email.trim().isEmpty() ? null : email.trim());
        student.setBatch(InputValidator.requireText(batch, "Batch"));
        return student;
    }

    public Student deactivateStudent(int id) throws EntityNotFoundException {
        Student student = findStudentById(id);
        student.setActive(false);
        return student;
    }

    public Student activateStudent(int id) throws EntityNotFoundException {
        Student student = findStudentById(id);
        student.setActive(true);
        return student;
    }
}
