package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Model of a database with rows as StudentRecords
 */
public class StudentDatabase {

    private final List<StudentRecord> studentRecords;
    private final HashMap<String, StudentRecord> indexedRecords = new HashMap<>();

    public StudentDatabase(List<StudentRecord> studentRecords) {
        this.studentRecords = studentRecords;
        studentRecords.forEach((record) -> indexedRecords.put(record.getJmbag(), record));
    }

    public StudentRecord forJMBAG(String jmbag) {
        return indexedRecords.get(jmbag);
    }

    /**
     * @param filter takes a custom filter to check over the student records
     * @return returns the accepted student records
     */
    public List<StudentRecord> filter(IFilter filter) {
        List<StudentRecord> returnList = new ArrayList<>();

        studentRecords.forEach((record) -> {
            if (filter.accepts(record)) returnList.add(record);
        });

        return returnList;
    }

}
