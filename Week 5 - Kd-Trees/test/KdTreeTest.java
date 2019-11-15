import junit.framework.TestCase;
import java.util.Iterator;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class KdTreeTest extends TestCase {
    public void testIsEmpty() {
        KdTree kdTree = new KdTree();
        assertTrue(kdTree.isEmpty());
        
        kdTree.insert(new Point2D(0.5, 0.5));
        assertFalse(kdTree.isEmpty());
    }
    
    public void testSize() {
        KdTree kdTree = new KdTree();
        assertEquals(0, kdTree.size());
        
        kdTree.insert(new Point2D(0.1, 0.1));
        kdTree.insert(new Point2D(0.5, 0.5));
        kdTree.insert(new Point2D(0.7, 0.7));
        assertEquals(3, kdTree.size());
    }
    
    public void testSize_RepeatedPoint() {
        KdTree kdTree = new KdTree();
        assertEquals(0, kdTree.size());
        
        kdTree.insert(new Point2D(0.1, 0.1));
        kdTree.insert(new Point2D(0.5, 0.5));
        kdTree.insert(new Point2D(0.7, 0.7));
        kdTree.insert(new Point2D(0.5, 0.5));
        assertEquals(3, kdTree.size());
    }
    
    public void testInsert() {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.1, 0.1));
        kdTree.insert(new Point2D(0.5, 0.5));
        kdTree.insert(new Point2D(0.7, 0.7));
    }
    
    public void testInsert_ThrowsExceptionWhenParamIsNull() {
        KdTree kdTree = new KdTree();
        try {
            kdTree.insert(null);
            fail("Exception should have been thrown");
        } catch (final IllegalArgumentException e) {
            // expected
        }
    }
    
    public void testContains() {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.1, 0.1));
        kdTree.insert(new Point2D(0.5, 0.5));
        
        assertTrue(kdTree.contains(new Point2D(0.1, 0.1)));
        assertTrue(kdTree.contains(new Point2D(0.5, 0.5)));
        assertFalse(kdTree.contains(new Point2D(0.7, 0.7)));
    }
    
    public void testRange() {
        RectHV rect = new RectHV(0.4, 0.3, 0.8, 0.6);
        
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.0, 0.0));
        kdTree.insert(new Point2D(0.1, 0.4));
        kdTree.insert(new Point2D(0.6, 0.5));
        
        Iterable<Point2D> rangeIterable = kdTree.range(rect);
        Iterator i = rangeIterable.iterator();
        
        Point2D[] expected = new Point2D[] { new Point2D(0.6, 0.5) };
        for (Point2D expectedPoint : expected) {
            assertEquals(expectedPoint, i.next());
        }
    }
    
    public void testNearest() {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.0, 0.0));
        kdTree.insert(new Point2D(0.1, 0.4));
        kdTree.insert(new Point2D(0.6, 0.5));
        
        Point2D nearestPoint = kdTree.nearest(new Point2D(0.8, 0.6));
        Point2D expectedPoint = new Point2D(0.6, 0.5);
        assertEquals(expectedPoint, nearestPoint);
    }
}