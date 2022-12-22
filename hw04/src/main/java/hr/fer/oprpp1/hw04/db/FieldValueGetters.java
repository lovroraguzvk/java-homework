package hr.fer.oprpp1.hw04.db;

/**
 * Class that contains static field getters of private variables in the class StudentRecord
 */
public class FieldValueGetters {

    public static final IFieldValueGetter FIRST_NAME = StudentRecord::getFirstName;

    public static final IFieldValueGetter LAST_NAME = StudentRecord::getLastName;

    public static final IFieldValueGetter JMBAG = StudentRecord::getJmbag;
}
