package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private final List<Course> courses = new ArrayList<>();

    public Course addCourse(String courseName, String description, int durationInWeeks) {
        courseName = InputValidator.requireText(courseName, "Course name");
        description = InputValidator.requireText(description, "Description");
        InputValidator.requirePositiveInt(durationInWeeks, "Duration");
        Course course = new Course(IdGenerator.getNextCourseId(), courseName, description, durationInWeeks);
        courses.add(course);
        return course;
    }

    public List<Course> listCourses() {
        return new ArrayList<>(courses);
    }

    public Course findCourseById(int id) throws EntityNotFoundException {
        for (Course course : courses) {
            if (course.getId() == id) return course;
        }
        throw new EntityNotFoundException("Course with ID " + id + " was not found.");
    }

    public Course setCourseActive(int id, boolean active) throws EntityNotFoundException {
        Course course = findCourseById(id);
        course.setActive(active);
        return course;
    }
}
