package com.airtribe.learntrack.entity;

public class Student extends Person {
    private String batch;
    private boolean active;

    public Student() {
        super();
        this.active = true;
    }

    // Constructor overloading: email can be supplied or omitted.
    public Student(int id, String firstName, String lastName, String email, String batch) {
        super(id, firstName, lastName, email);
        this.batch = batch;
        this.active = true;
    }

    public Student(int id, String firstName, String lastName, String batch) {
        this(id, firstName, lastName, null, batch);
    }

    public String getBatch() { return batch; }
    public void setBatch(String batch) { this.batch = batch; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public String getDisplayName() {
        return "Student: " + super.getDisplayName();
    }

    @Override
    public String toString() {
        return "ID: " + getId()
                + " | Name: " + getFirstName() + " " + getLastName()
                + " | Email: " + (getEmail() == null || getEmail().isBlank() ? "Not provided" : getEmail())
                + " | Batch: " + batch
                + " | Active: " + active;
    }
}
