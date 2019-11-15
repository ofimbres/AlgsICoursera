import junit.framework.TestCase;
import java.util.Iterator;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSETTest extends TestCase {
    public void testIsEmpty() {
        PointSET pointSet = new PointSET();
        assertTrue(pointSet.isEmpty());
        
        pointSet.insert(new Point2D(0.5, 0.5));
        assertFalse(pointSet.isEmpty());
    }
    
    public void testSize() {
        PointSET pointSet = new PointSET();
        assertEquals(0, pointSet.size());
        
        pointSet.insert(new Point2D(0.1, 0.1));
        pointSet.insert(new Point2D(0.5, 0.5));
        pointSet.insert(new Point2D(0.7, 0.7));
        assertEquals(3, pointSet.size());
    }
    
    public void testSize_RepeatedPoint() {
        PointSET pointSet = new PointSET();
        assertEquals(0, pointSet.size());
        
        pointSet.insert(new Point2D(0.1, 0.1));
        pointSet.insert(new Point2D(0.5, 0.5));
        pointSet.insert(new Point2D(0.7, 0.7));
        pointSet.insert(new Point2D(0.5, 0.5));
        assertEquals(3, pointSet.size());
    }
    
    public void testInsert() {
        PointSET pointSet = new PointSET();
        pointSet.insert(new Point2D(0.1, 0.1));
        pointSet.insert(new Point2D(0.5, 0.5));
        pointSet.insert(new Point2D(0.7, 0.7));
    }
    
    public void testInsert_ThrowsExceptionWhenParamIsNull() {
        PointSET pointSet = new PointSET();
        try {
            pointSet.insert(null);
            fail("Exception should have been thrown");
        } catch (final IllegalArgumentException e) {
            // expected
        }
    }
    
    public void testContains() {
        PointSET pointSet = new PointSET();
        pointSet.insert(new Point2D(0.1, 0.1));
        pointSet.insert(new Point2D(0.5, 0.5));
        
        assertTrue(pointSet.contains(new Point2D(0.1, 0.1)));
        assertTrue(pointSet.contains(new Point2D(0.5, 0.5)));
        assertFalse(pointSet.contains(new Point2D(0.7, 0.7)));
    }
    
    public void testRange() {
        RectHV rect = new RectHV(0.4, 0.3, 0.8, 0.6);
        
        PointSET pointSet = new PointSET();
        pointSet.insert(new Point2D(0.0, 0.0));
        pointSet.insert(new Point2D(0.1, 0.4));
        pointSet.insert(new Point2D(0.6, 0.5));
        
        Iterable<Point2D> rangeIterable = pointSet.range(rect);
        Iterator i = rangeIterable.iterator();
        
        Point2D[] expected = new Point2D[] { new Point2D(0.6, 0.5) };
        for (Point2D expectedPoint : expected) {
            assertEquals(expectedPoint, i.next());
        }
    }
    
    public void testNearest() {
        PointSET pointSet = new PointSET();
        pointSet.insert(new Point2D(0.0, 0.0));
        pointSet.insert(new Point2D(0.1, 0.4));
        pointSet.insert(new Point2D(0.6, 0.5));
        
        Point2D nearestPoint = pointSet.nearest(new Point2D(0.8, 0.6));
        Point2D expectedPoint = new Point2D(0.6, 0.5);
        assertEquals(expectedPoint, nearestPoint);
    }
}