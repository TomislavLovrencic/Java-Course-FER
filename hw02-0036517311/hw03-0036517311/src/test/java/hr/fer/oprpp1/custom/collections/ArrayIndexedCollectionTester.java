package hr.fer.oprpp1.custom.collections;


import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;




public class ArrayIndexedCollectionTester {
	
	@Test
    public void testConstructorWrongCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection<>(- 5));
    }




    @Test
    public void testConstructorGiveArray() {
        Collection<Integer> startCollection = new ArrayIndexedCollection<>();
        startCollection.add(3);
        startCollection.add(10);

        ArrayIndexedCollection<Integer> testCollection = new ArrayIndexedCollection<>(startCollection);

        var expectedArray = new Object[]{3, 10};
        var gottenArray = testCollection.toArray();


        assertArrayEquals(expectedArray, gottenArray);
    }

    @Test
    public void testSendNullCollection() {
        assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
    }

    @Test
    public void testAddMethod() {
        var testCollection = new ArrayIndexedCollection<>(2);
        testCollection.add(5);
        testCollection.add("Test");

        var expectedArray = new Object[]{5, "Test"};

        assertArrayEquals(expectedArray, testCollection.toArray());

    }

    @Test
    public void testGetMethod() {
        String test = "Test";
        var testCollection = new ArrayIndexedCollection<>();
        testCollection.add(test);

        assertEquals(test, testCollection.get(0));
    }

    @Test
    public void testClearMethod() {
        String test = "Test";
        var testCollection = new ArrayIndexedCollection<>();
        testCollection.add(test);

        testCollection.clear();
        assertFalse(testCollection.contains(test));
    }

    @Test
    public void testInsertMethod() {
        var first = 1;
        var second = "Second";
        var third = 3.0;
        var fourth = "4";

        var testCollection = new ArrayIndexedCollection<>(5);
        testCollection.add(first);
        testCollection.add(second);
        testCollection.add(third);
        testCollection.add(fourth);

        var test = "Test";

        testCollection.insert(test, 1);
        var array = testCollection.toArray();

        var expectedArray = new Object[]{first, test, second, third, fourth};

        assertArrayEquals(expectedArray, array);

    }

    @Test
    public void testIndexOfMethod() {
        var test = "Test";
        var testCollection = new ArrayIndexedCollection<>();
        testCollection.add(1);
        testCollection.add(2);
        testCollection.add(test);
        testCollection.add(3);

        assertEquals(2, testCollection.indexOf(test));
    }

    @Test
    public void testRemoveMethod() {
        var first = 1;
        var second = "Second";
        var third = 3.0;
        var fourth = "4";

        var testCollection = new ArrayIndexedCollection<>(5);
        testCollection.add(first);
        testCollection.add(second);
        testCollection.add(third);
        testCollection.add(fourth);

        testCollection.remove(1);
        var array = testCollection.toArray();

        var expectedArray = new Object[]{first, third, fourth};

        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testElementsGetter() {
        Collection<String> col1 = new ArrayIndexedCollection<>();
        Collection<String> col2 = new ArrayIndexedCollection<>();
        col1.add("Ivo");
        col1.add("Ana");
        col1.add("Jasna");
        col2.add("Jasmina");
        col2.add("Štefanija");
        col2.add("Karmela");
        ElementsGetter<String> getter1 = col1.createElementsGetter();
        ElementsGetter<String> getter2 = col1.createElementsGetter();
        ElementsGetter<String> getter3 = col2.createElementsGetter();

        String result = "";
        result += getter1.getNextElement() + " ";
        result += getter1.getNextElement() + " ";
        result += getter2.getNextElement() + " ";
        result += getter3.getNextElement() + " ";
        result += getter3.getNextElement();

        assertEquals("Ivo Ana Ivo Jasmina Štefanija", result);
    }

    @Test
    public void testElementsGetterException() {
        assertThrows(NoSuchElementException.class, () -> {
            Collection<String> col = new ArrayIndexedCollection<>(); // npr. new ArrayIndexedCollection();
            col.add("Ivo");
            col.add("Ana");
            col.add("Jasna");
            ElementsGetter<String> getter1 = col.createElementsGetter();
            String result = "";
            result += getter1.getNextElement() + " ";
            result += getter1.getNextElement() + " ";
            result += getter1.getNextElement() + " ";
            result += getter1.getNextElement();
        });
    }

    @Test
    public void hasNextElementsGetterTest() {
        Collection<String> col = new ArrayIndexedCollection<>(); // npr. new ArrayIndexedCollection();
        col.add("Ivo");
        col.add("Ana");
        col.add("Jasna");
        ElementsGetter<String> getter = col.createElementsGetter();
        getter.getNextElement();
        getter.getNextElement();
        var result = new boolean[2];
        result[0] = getter.hasNextElement();
        getter.getNextElement();
        result[1] = getter.hasNextElement();
        assertArrayEquals(new boolean[]{true, false}, result);
    }

    @Test
    public void testElementsGetterThrowsConcurrentModificationException() {
        assertThrows(ConcurrentModificationException.class, () -> {
            Collection<String> col = new ArrayIndexedCollection<>();
            col.add("Ivo");
            col.add("Ana");
            col.add("Jasna");
            ElementsGetter<String> getter = col.createElementsGetter();
            getter.getNextElement();
            getter.getNextElement();
            col.clear();
            getter.getNextElement();
        });
    }
    
    @Test
    public void testAddAllSatisfying() {
        Collection<Integer> col1 = new LinkedListIndexedCollection<>();
        Collection<Integer> col2 = new ArrayIndexedCollection<>();
        col1.add(2);
        col1.add(3);
        col1.add(4);
        col1.add(5);
        col1.add(6);
        col2.add(12);
        col2.addAllSatisfying(col1, (v) -> (Integer) v % 2 == 0);

        StringBuilder sb = new StringBuilder();
        col2.forEach(sb::append);

        assertEquals("12246", sb.toString());
    }
    
    @Test
    public void testProcessRemaining() {
        Collection<String> col = new ArrayIndexedCollection<>();
        col.add("Ivo");
        col.add("Ana");
        col.add("Jasna");
        ElementsGetter<String> getter = col.createElementsGetter();
        getter.getNextElement();
        StringBuilder stringBuilder = new StringBuilder();
        getter.processRemaining(stringBuilder::append);
        assertEquals("AnaJasna",stringBuilder.toString());
    }
	
	
	
}
