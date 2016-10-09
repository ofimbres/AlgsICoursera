import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments;
    
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        checkDuplicatedEntries(points);
        
        ArrayList<LineSegment> foundSegments = new ArrayList<LineSegment>();
        
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);
        
        for (int p = 0; p < pointsCopy.length - 3; p++) {
            for (int q = p + 1; q < pointsCopy.length - 2; q++) {
                for (int r = q + 1; r < pointsCopy.length - 1; r++) {
                    for (int s = r + 1; s < pointsCopy.length; s++) {
                        if (pointsCopy[p].slopeTo(pointsCopy[q]) == pointsCopy[p].slopeTo(pointsCopy[r]) &&
                            pointsCopy[p].slopeTo(pointsCopy[q]) == pointsCopy[p].slopeTo(pointsCopy[s])) {
                            foundSegments.add(new LineSegment(pointsCopy[p], pointsCopy[s]));
                        }
                    }
                }
            }
        }
        
        this.segments = foundSegments.toArray(new LineSegment[foundSegments.size()]);
    }
    
    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }
    
    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }
    
    private void checkDuplicatedEntries(Point[] points) {
        if (points == null) throw new NullPointerException("points is null");
        
        for (int i = 0; i < points.length -1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i] == null || points[j] == null) throw new NullPointerException("One of the points is null");
                if (points[i].compareTo(points[j]) == 0)    throw new IllegalArgumentException(points[i].toString() + "point is repeated");
            }
        }
    }
}