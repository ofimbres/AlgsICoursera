import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class PointTest extends TestCase {
    // Tests for 'slopeTo' method.
    public void testHorizontalLine() {
        Point x, y;
        x = new Point(0,0);
        y = new Point(2,0);
        assertEquals("slopeTo: (0,0) and (2,0)", 0.0, x.slopeTo(y)); 
    }
    
    public void testVerticalLine() {
        Point x, y;
        x = new Point(0,0);
        y = new Point(0,2);
        assertEquals("slopeTo: (0,0) and (0,2)", Double.POSITIVE_INFINITY, x.slopeTo(y)); 
    }
       
    public void testEqualPoints() {
        Point x, y;
        x = new Point(2,2);
        y = new Point(2,2);
        assertEquals("slopeTo: (2,2) and (2,2)", Double.NEGATIVE_INFINITY, x.slopeTo(y)); 
    }
           
    public void testSlopeWith1() {
        Point x, y;
        x = new Point(0,0);
        y = new Point(2,2);
        assertEquals("slopeTo: (0,0) and (2,2)", 1.0, x.slopeTo(y)); 
    }
    
    public void testSlopeWith6() {
        Point x, y;
        x = new Point(0,0);
        y = new Point(1,6);
        assertEquals("slopeTo: (0,0) and (1,6)", 6.0, x.slopeTo(y)); 
    }
    
    public void testNegativeSlope() {
        Point x, y;
        x = new Point(0,0);
        y = new Point(-2,5);
        assertEquals("(0,0) and (-2,5)", -2.5, x.slopeTo(y)); 
    }
    
    // Tests for 'compareTo' method.
    public void testCompareToMethod() {
        Point x, y;
        x = new Point(0,0);
        y = new Point(0,0);
        assertEquals("compareTo: (0,0) and (0,0)", 0, x.compareTo(y));
        
        x = new Point(0,0);
        y = new Point(0,2);
        assertEquals("compareTo: (0,0) and (0,2)", -1, x.compareTo(y));
        
        x = new Point(1,2);
        y = new Point(0,2);
        assertEquals("compareTo: (1,2) and (0,2)", 1, x.compareTo(y));
    }
}
