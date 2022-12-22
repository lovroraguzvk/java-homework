package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayIndexedCollectionTest {

    @Test
    public void testConstructor1() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        assertEquals(collection.size(), 0);
    }

    @Test
    public void testConstructor2() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection(5);
        assertEquals(0, collection.size());
        assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(0));
    }

    @Test
    public void testConstructor3() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection(new Collection());
        collection.add(1);
        collection.add(2);

        ArrayIndexedCollection collection1 = new ArrayIndexedCollection(collection);
        assertEquals(2, collection1.size());
        assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
    }

    @Test
    public void testConstructor4() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        collection.add(1);
        collection.add(2);
        collection.add(3);
        collection.add(4);

        ArrayIndexedCollection collection1 = new ArrayIndexedCollection(collection, 6);

        assertEquals(4, collection1.size());

        assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(new Collection(), 0));
        assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null, 1));
    }

    @Test
    public void testSize() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        collection.add(5);
        collection.add("hello");
        collection.add("world");
        assertEquals(3, collection.size());
    }

    @Test
    public void testContains() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        collection.add(5);
        collection.add("hello");
        collection.add("world");
        assertTrue(collection.contains("hello"));
        assertFalse(collection.contains(1));
    }

    @Test
    public void testRemove() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        collection.add(5);
        collection.add("hello");
        collection.add("world");

        collection.remove("hello");
        assertFalse(collection.contains("hello"));

        assertFalse(collection.remove("happy"));

        assertThrows(IndexOutOfBoundsException.class, () -> collection.remove(2));
    }

    @Test
    public void testToArray() {
        Object[] expected = new Object[]{1,2,3};
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        collection.add(1);
        collection.add(2);
        collection.add(3);
        assertArrayEquals(expected, collection.toArray());

    }

    @Test
    public void testForEach() {
        class ExampleProcessor extends Processor {

            int x = 0;
            Object first;

            @Override
            public void process(Object value) {
                if(x == 0) first = value;
                x++;
            }
        }

        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        ExampleProcessor processor = new ExampleProcessor();

        collection.add(1);
        collection.add(2);
        collection.add(3);
        collection.forEach(processor);

        assertEquals(collection.size(), processor.x);
        assertEquals(collection.get(0), processor.first);
    }

    @Test
    public void testAdd() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        collection.add(1);

        collection.add(2);
        assertEquals(collection.get(1), 2);

        assertThrows(NullPointerException.class, () -> collection.add(null));
    }

    @Test
    public void testGet() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        collection.add(1);
        collection.add(2);
        collection.add(3);

        assertEquals(2, collection.get(1));
        assertEquals(3, collection.get(2));

        assertThrows(IndexOutOfBoundsException.class, () -> collection.get(3));
    }

    @Test
    public void testClear() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        collection.add(1);
        collection.add(2);
        collection.add(3);

        collection.clear();
        assertEquals(0, collection.size());
    }

    @Test
    public void testInsert() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        collection.add(1);
        collection.add(2);
        collection.add(3);

        collection.insert(4, 1);

        assertEquals(4, collection.get(1));
        assertEquals(2, collection.get(2));
        assertEquals(1, collection.get(0));

        assertThrows(IndexOutOfBoundsException.class, () -> collection.insert(1, 4));
        assertThrows(IndexOutOfBoundsException.class, () -> collection.insert(1, -1));
        assertThrows(NullPointerException.class, () -> collection.insert(null, 1));
    }

    @Test
    public void testIndexOf() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        collection.add(1);
        collection.add(2);
        collection.add(3);

        assertEquals(2, collection.indexOf(3));
        assertEquals(1, collection.indexOf(2));
        assertEquals(-1, collection.indexOf(4));
    }

    /*
    @Test
    void testTest() {
        ArrayIndexedCollection col = new ArrayIndexedCollection(2);
        col.add(Integer.valueOf(20));
        col.add("New York");
        col.add("San Francisco"); // here the internal array is reallocated to 4
        System.out.println(col.contains("New York")); // writes: true
        col.remove(1); // removes "New York"; shifts "San Francisco" to position 1
        System.out.println(col.get(1)); // writes: "San Francisco"
        System.out.println(col.size()); // writes: 2
        col.add("Los Angeles");
        LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col);
// This is local class representing a Processor which writes objects to System.out
        class P extends Processor {
            public void process(Object o) {
                System.out.println(o);
            }
        };
        System.out.println("col elements:");
        col.forEach(new P());
        System.out.println("col elements again:");
        System.out.println(Arrays.toString(col.toArray()));
        System.out.println("col2 elements:");
        col2.forEach(new P());
        System.out.println("col2 elements again:");
        System.out.println(Arrays.toString(col2.toArray()));
        System.out.println(col.contains(col2.get(1))); // true
        System.out.println(col2.contains(col.get(1))); // true
        col.remove(Integer.valueOf(20)); // removes 20 from collection (at position 0).

    }

     */
}