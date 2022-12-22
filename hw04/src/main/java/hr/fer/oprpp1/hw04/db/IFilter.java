package hr.fer.oprpp1.hw04.db;

/**
 * Interface for filtering StudentRecord-s
 */
public interface IFilter {

    boolean accepts(StudentRecord record);
}
