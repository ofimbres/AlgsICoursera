import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int n;
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[2];
        n = 0;
    }
    
    // is the queue empty?
    public boolean isEmpty() {
        return n == 0;
    }
    
    // return the number of items on the queue
    public int size() {
        return n;
    }
    
    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException("item cannot be added as null");
        
        if (n == s.length) resize(2 * s.length);
        s[n++] = item;
    }
    
    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) { throw new NoSuchElementException("cannot remove item because deque is empty deque"); }
        
        Item item = s[--n];
        
        int r = StdRandom.uniform(n + 1);
        Item item2 = s[r];
        
        s[r] = item;
        s[n] = null;
        
        if (n > 0 && n == s.length / 4) resize(s.length / 2);
        return item2;
    }
    
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++)
            copy[i] = s[i];
        s = copy;
    }
    
    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("cannot get item because deque is empty deque");
        
        int r = StdRandom.uniform(n);
        return s[r];
    }
    
    // return an independent iterator over items in random order 
    public Iterator<Item> iterator() {
        return new RandomListIterator();
    }
    
    private class RandomListIterator implements Iterator<Item> {
        private int current;
        private Item[] ss; // s shuffled
        
        public RandomListIterator() {
            current = n;
            
            ss = (Item[]) new Object[n];
            for (int i = 0; i < n; i++) {
                ss[i] = s[i];
            }
            StdRandom.shuffle(ss);
        }
        
        public boolean hasNext() {
            return current > 0;
        }
        
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            
            Item item = ss[--current];
            return item;
        }
    }
    
    // unit testing
    public static void main(String[] args) { 
    }
    
}
