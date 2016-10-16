import junit.framework.TestCase;
import java.util.Iterator;

public class DequeTest extends TestCase {
    
    public void testDequeInitialization() {
        Deque<Integer> deque = new Deque<Integer>();
        
        assertTrue(deque.isEmpty());   
        assertEquals(deque.size(), 0);
    }
    
    public void testDequeMakesAddRemoveAddRemove() {
        Deque<Integer> deque = new Deque<Integer>();
        
        deque.addFirst(5);
        assertEquals(deque.size(), 1);
        assertFalse(deque.isEmpty()); 
        
        assertEquals((int)deque.removeLast(), 5);
        assertEquals(deque.size(), 0);
        assertTrue(deque.isEmpty()); 
        
        deque.addFirst(9);
        assertEquals(deque.size(), 1);
        assertFalse(deque.isEmpty()); 
        
        assertEquals((int)deque.removeLast(), 9);
        assertEquals(deque.size(), 0);
        assertTrue(deque.isEmpty()); 
    }
    
    public void testDequePopItems() {
        Deque<Integer> deque = new Deque<Integer>();
        
        deque.addFirst(1);
        deque.addLast(2);
        deque.addLast(3);
        
        assertEquals(deque.size(), 3);
        assertFalse(deque.isEmpty()); 
        
        assertEquals((int)deque.removeLast(), 3);
        assertEquals((int)deque.removeFirst(), 1);
        assertEquals(deque.size(), 1);
        
        deque.addLast(9);
        deque.addFirst(5);
        
        assertEquals((int)deque.removeLast(), 9);
        assertEquals((int)deque.removeLast(), 2);
        assertEquals((int)deque.removeFirst(), 5);
        
        assertTrue(deque.isEmpty()); 
        assertEquals(deque.size(), 0);
    }
    
    public void testDequeIterator() {
        Deque<Integer> deque = new Deque<Integer>();
        
        // 4 2 1 3 5
        for (int i = 1; i <= 5; i++) {
            if (i % 2 == 0)
                deque.addFirst(i);
            else
                deque.addLast(i);
        }
        
        Iterator iterator = deque.iterator();
        
        assertTrue(iterator.hasNext());
        assertEquals(iterator.next(), 4);
        assertEquals(iterator.next(), 2);
        assertEquals(iterator.next(), 1);
        assertEquals(iterator.next(), 3);
        assertEquals(iterator.next(), 5);
        assertFalse(iterator.hasNext());
    }
    
    public void testDequeMultipleIterators() {
        Deque<Integer> deque = new Deque<Integer>();
        
        for (int i = 1; i <= 5; i++)
            deque.addLast(i);
        
        int x2 = 1, y2;
        for (int x : deque) {
            y2 = 1;
            
            for (int y : deque) {
                assertEquals(y, y2++);
            }
            assertEquals(x, x2++);
        }
            
    }
}
