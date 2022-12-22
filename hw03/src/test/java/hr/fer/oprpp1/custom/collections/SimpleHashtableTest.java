package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleHashtableTest {

    @Test
    public void testPut() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);

        hashtable.put("Ivan", 5);
        hashtable.put("Ante", 2);
        hashtable.put("Ivan", 2);
        hashtable.put("Jasna", 5);

        System.out.println(hashtable);

        assertThrows(NullPointerException.class, () -> hashtable.put(null, 2));

    }

    @Test
    public void testRemove() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);

        hashtable.put("Ivan", 5);
        hashtable.put("Matej", 7);

        hashtable.clear();
        assertEquals(0, hashtable.size());

        hashtable.put("Ivan", 5);
        hashtable.put("Matej", 7);
    }

    @Test
    public void testGet() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);

        hashtable.put("Ivan", 5);
        hashtable.put("Matej", 7);

        assertEquals(5, hashtable.get("Ivan"));
        assertNull(hashtable.get("Jasna"));
    }

    @Test
    public void testIterable() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);

        hashtable.put("Ivan", 5);
        hashtable.put("Matej", 7);

        for (SimpleHashtable.TableEntry<String, Integer> entry : hashtable) {
            System.out.println(entry.getValue());
        }
    }

}