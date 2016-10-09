import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class BruteCollinearPointsTest extends TestCase {
    
    public void testShouldThrowNullPointerExceptionIfOnePointIsNull() {
        Throwable e = null;
        
        try {
            Point[] points = new Point[] { new Point(0, 0), new Point(1, 1), null, new Point(3, 3) };
            new BruteCollinearPoints(points);
        } catch(Throwable ex) {
            e = ex;
        }
        
        assertTrue(e instanceof NullPointerException);
    }
    
    public void testShouldFindLineSegment1() {
        Throwable e = null;
        
        Point[] points = new Point[] { new Point(0, 0), new Point(1, 1), new Point(2, 2), new Point(3, 3) };
        BruteCollinearPoints cut = new BruteCollinearPoints(points);
        
        assertEquals(1, cut.numberOfSegments());
        assertEquals("(0, 0) -> (3, 3)", cut.segments()[0].toString());
    }
}