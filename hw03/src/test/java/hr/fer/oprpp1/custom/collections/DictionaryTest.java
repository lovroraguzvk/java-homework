package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DictionaryTest {

    @Test
    public void testPut() {
        Dictionary<String, Integer> dict = new Dictionary<>();

        assertNull(dict.put("Ivan", 4));

        assertEquals(4, dict.get("Ivan"));

        dict.put("Jasna", 4);

        assertEquals(4, dict.put("Ivan", 5));
        assertEquals(5, dict.get("Ivan"));
        assertEquals(4, dict.get("Jasna"));
    }

    @Test
    public void testNullKey() {
        Dictionary<String, Integer> dict = new Dictionary<>();

        dict.put("Matej", 5);

        assertThrows(NullPointerException.class, () -> dict.put(null, 2));
        assertThrows(NullPointerException.class, () -> dict.get(null));
    }

    @Test
    public void testIfEmpty() {
        Dictionary<String, Integer> dict = new Dictionary<>();

        assertTrue(dict.isEmpty());

        dict.put("Ivan", 2);

        assertFalse(dict.isEmpty());

        dict.remove("Ivan");

        assertTrue(dict.isEmpty());

        dict.put("Ivan", 3);
        dict.put("Jasna", 3);
        dict.clear();
        assertTrue(dict.isEmpty());
    }

    @Test
    public void testGetNoEntry() {
        Dictionary<String, Integer> dict = new Dictionary<>();

        dict.put("Ivan", 4);

        assertNull(dict.get("Jasna"));
    }

    @Test
    public void testRemove() {
        Dictionary<String, Integer> dict = new Dictionary<>();

        dict.put("Ivan", 4);
        assertEquals(4, dict.remove("Ivan"));

        assertNull(dict.remove("Jasna"));
    }

}