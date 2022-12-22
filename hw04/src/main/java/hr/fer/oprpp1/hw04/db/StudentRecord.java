package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

/**
 * Record class that containes getters for
 */
public class StudentRecord {

    private final String jmbag;
    private final String lastName;
    private final String firstName;
    private final String finalGrade;

    public StudentRecord(String jmbag, String lastName, String firstName, String finalGrade) {
        this.jmbag = jmbag;
        this.lastName = lastName;
        this.firstName = firstName;
        this.finalGrade = finalGrade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentRecord that = (StudentRecord) o;
        return jmbag.equals(that.jmbag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jmbag);
    }

    public String getJmbag() {
        return jmbag;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFinalGrade() {
        return finalGrade;
    }
}
