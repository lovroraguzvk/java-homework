package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedListIndexedCollectionTest {

    @Test
    public void testConstructor1() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        collection.add(1);
        collection.add(2);
        collection.add(3);

        assertEquals(3, collection.size());
    }

    @Test
    public void testConstructor2() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        collection.add(1);
        collection.add(2);
        collection.add(3);

        LinkedListIndexedCollection collection1 = new LinkedListIndexedCollection(collection);

        assertEquals(3, collection1.size());
    }

    @Test
    public void testAdd() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        collection.add(1);

        collection.add(2);
        assertEquals(collection.get(1), 2);

        assertThrows(NullPointerException.class, () -> collection.add(null));
    }

    @Test
    public void testClear() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        collection.add(1);
        collection.add(2);
        collection.add(3);

        collection.clear();
        assertEquals(0, collection.size());
    }

    @Test
    public void testGet() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        collection.add(1);
        collection.add(2);
        collection.add(3);

        assertEquals(2, collection.get(1));
        assertEquals(3, collection.get(2));

        assertThrows(IndexOutOfBoundsException.class, () -> collection.get(3));
    }

    @Test
    public void testInsert() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        collection.add(1);
        collection.add(2);
        collection.add(3);

        collection.insert(4, 1);
        collection.insert(5, 4);

        assertEquals(4, collection.get(1));
        assertEquals(2, collection.get(2));
        assertEquals(1, collection.get(0));

        assertThrows(IndexOutOfBoundsException.class, () -> collection.insert(1, 6));
        assertThrows(IndexOutOfBoundsException.class, () -> collection.insert(1, -1));
        assertThrows(NullPointerException.class, () -> collection.insert(null, 1));
    }

    @Test
    public void testIndexOf() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        collection.add(1);
        collection.add(2);
        collection.add(3);

        assertEquals(2, collection.indexOf(3));
        assertEquals(1, collection.indexOf(2));
        assertEquals(-1, collection.indexOf(4));
    }

    @Test
    public void testRemove() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        collection.add(5);
        collection.add("hello");
        collection.add("world");

        collection.remove("hello");
        assertFalse(collection.contains("hello"));

        assertFalse(collection.remove("happy"));

        assertThrows(IndexOutOfBoundsException.class, () -> collection.remove(2));
    }
}