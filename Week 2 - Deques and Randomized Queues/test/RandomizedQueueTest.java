import junit.framework.TestCase;
import java.util.Iterator;
import java.util.Arrays;

public class RandomizedQueueTest extends TestCase {

    public void testRandomizedQueueInitialization() {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        
        assertTrue(queue.isEmpty());   
        assertEquals(queue.size(), 0);
    }
    
    public void testRandomizedQueueMakesPushPopPushPop() {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        
        queue.enqueue(5);
        assertEquals(queue.size(), 1);
        assertFalse(queue.isEmpty()); 
        
        assertEquals((int)queue.dequeue(), 5);
        assertEquals(queue.size(), 0);
        assertTrue(queue.isEmpty()); 
        
        queue.enqueue(9);
        assertEquals(queue.size(), 1);
        assertFalse(queue.isEmpty()); 
        
        assertEquals((int)queue.dequeue(), 9);
        assertEquals(queue.size(), 0);
        assertTrue(queue.isEmpty()); 
    }
    
    public void testRandomizedQueuePopItems() {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        
        Integer[] array = new Integer[] { 1, 2, 3};
        for (int i = 0; i < array.length; i++)
            queue.enqueue(array[i]);
        
        assertEquals(queue.size(), array.length);
        assertFalse(queue.isEmpty());
        
        assertTrue(Arrays.asList(array).contains(queue.sample()));
        assertEquals(queue.size(), 3);
        
        assertTrue(Arrays.asList(array).contains(queue.dequeue()));
        assertEquals(queue.size(), 2);
        
        assertTrue(Arrays.asList(array).contains(queue.dequeue()));
        assertTrue(Arrays.asList(array).contains(queue.dequeue()));
        assertEquals(queue.size(), 0);
        assertTrue(queue.isEmpty());
    }
    
    public void testRandomizedQueueIterator() {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        
        int[] array = new int[] { 9, 8, 7, 6, 5 };
        // 1, 2, 3, 4, 5
        for (int i = 0; i < 5; i++) {
            queue.enqueue(array[i]);
        }
        
        Iterator iterator = queue.iterator();
        
        assertTrue(iterator.hasNext());
        
        for (int i = 0; i < 5; i++) {
            iterator.next();
        }

        assertFalse(iterator.hasNext());
    }
    
    public void testRandomizedQueueMultipleIterators() {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        
        for (int i = 1; i <= 5; i++)
            queue.enqueue(i);
        
        int x1 = 0, y1 = 0;;
        for (int x : queue) {
            for (int y : queue) {
                y1++;
            }
            x1++;
        }
        
        assertEquals(x1, 5);
        assertEquals(y1, 25);
    }
}