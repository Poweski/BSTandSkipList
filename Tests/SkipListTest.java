import DataStructures.SkipList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class SkipListTest {

    private SkipList<Integer> skipList;

    @BeforeEach
    void setUp() {
        Comparator<Integer> comparator = Integer::compareTo;
        skipList = new SkipList<>(0.5, comparator);
    }

    @Test
    void search_existingElement_returnsElement() {
        skipList.insert(5);
        skipList.insert(10);
        skipList.insert(15);

        Integer result = skipList.search(10);

        assertEquals(10, result);
    }

    @Test
    void search_nonExistingElement_returnsNull() {
        skipList.insert(5);
        skipList.insert(10);
        skipList.insert(15);

        Integer result = skipList.search(20);

        assertNull(result);
    }

    @Test
    void remove_existingElement_returnsElement() {
        skipList.insert(5);
        skipList.insert(10);
        skipList.insert(15);

        Integer result = skipList.remove(10);

        assertEquals(10, result);
    }

    @Test
    void remove_nonExistingElement_returnsNull() {
        skipList.insert(5);
        skipList.insert(10);
        skipList.insert(15);

        Integer result = skipList.remove(20);

        assertNull(result);
    }

    @Test
    void insert_newElement_returnsElement() {
        Integer result = skipList.insert(5);

        assertEquals(5, result);
    }

    @Test
    void insert_duplicateElement_throwsException() {
        skipList.insert(5);

        assertThrows(IllegalArgumentException.class, () -> skipList.insert(5));
    }
}
