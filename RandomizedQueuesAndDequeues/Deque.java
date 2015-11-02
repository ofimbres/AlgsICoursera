import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int N;
    
    private class Node {
        Item item;
        Node previous;
        Node next;
    }
    
    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        N = 0;
    }
    
    // is the deque empty?
    public boolean isEmpty() {
        return N == 0; 
    }
    
    // return the number of items on the dequeue
    public int size() {
        return N;
    }
    
    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException("item cannot be added as null");
        
        Node newNode = createNode(item);
        
        if (isEmpty()) {
            first = last = newNode;
        }
        else {
            Node oldFirst = first;
            oldFirst.previous = newNode;
            newNode.next = oldFirst;
            first = newNode;
        }
        N++;
    }
    
    // add the item to the end
    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException("item cannot be added as null");
        
        Node newNode = createNode(item);
        
        if (isEmpty()) {
            first = last = newNode;
        } else {
            Node oldLast = last;
            oldLast.next = newNode;
            newNode.previous = oldLast;
            last = newNode;
        }
        N++;
    }
    
    private Node createNode(Item item) {
        Node node = new Node();
        node.item = item;
        return node;
    }
    
    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("cannot remove item because deque is empty deque");
        
        Item item = first.item;
        if (size() == 1) {
            first = last = null;
        } else {
            first = first.next;
            first.previous = null;
        }
        N--;
        return item;
    }
    
    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("cannot remove item because deque is empty deque");
        
        Item item = last.item;
        if (size() == 1) {
            first = last = null;
        } else {
            last = last.previous;
            last.next = null;
        }
        N--;
        return item;
    }
    
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();
    }
    
    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;
        
        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            
            Item item = current.item;
            current = current.next;
            return item;
        }

    }
    
    // unit testing
    public static void main(String[] args) { 
    }
}
