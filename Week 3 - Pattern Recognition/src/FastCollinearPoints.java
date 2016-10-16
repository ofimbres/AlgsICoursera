import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class FastCollinearPoints {
    private List<LineSegment> segments = new ArrayList<LineSegment>();
    
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException("points is null");
        
        int n = points.length;
        Point[] pointsCopy = Arrays.copyOf(points, n);
        Arrays.sort(pointsCopy);
        
        // send sorted points to check validations
        checkDuplicatedEntries(pointsCopy);
        
        Point[] sortedCopy = Arrays.copyOf(pointsCopy, n);
        
        Map<Double, List<Point>> foundSegments = new HashMap<Double, List<Point>>();

        for (Point startPoint : sortedCopy) {
            System.arraycopy(sortedCopy, 0, pointsCopy, 0, n);
            
            // sort the points according to the angles they makes with p
            Arrays.sort(pointsCopy, startPoint.slopeOrder());

            double slope = 0;
            double previousSlope = Double.NEGATIVE_INFINITY;
            ArrayList<Point> slopePoints = new ArrayList<Point>();

            for (int q = 1; q < pointsCopy.length; q++) {
                slope = startPoint.slopeTo(pointsCopy[q]);
                
                if (slope == previousSlope) {
                    slopePoints.add(pointsCopy[q]);
                } else {
                    if (slopePoints.size() >= 3) {
                       slopePoints.add(startPoint); 
                       addSegmentIfNew(foundSegments, previousSlope, slopePoints);
                    }
                    
                    slopePoints.clear();
                    slopePoints.add(pointsCopy[q]);
                }
                
                previousSlope = slope;
            }
            
            if (slopePoints.size() >= 3) {
                slopePoints.add(startPoint);
                addSegmentIfNew(foundSegments, slope, slopePoints);
            }
        }
    }
    
    private void addSegmentIfNew(Map<Double, List<Point>> foundSegments, double slope, List<Point> slopePoints) {           
        List<Point> endPoints = foundSegments.get(slope);
        Collections.sort(slopePoints);
        
        Point startPoint = slopePoints.get(0);
        Point endPoint = slopePoints.get(slopePoints.size() - 1);
            
        if (endPoints == null) {
            endPoints = new ArrayList<Point>();
            endPoints.add(endPoint);
                
            foundSegments.put(slope, endPoints);
            segments.add(new LineSegment(startPoint, endPoint));
        } else {
            for (Point currentEndPoint : endPoints) {
                if (currentEndPoint.equals(endPoint)) return;
            }
                
            endPoints.add(endPoint);
            segments.add(new LineSegment(startPoint, endPoint));
        }
    }
    
    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }
    
    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }
    
    private void checkDuplicatedEntries(Point[] points) {        
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null || points[i + 1] == null) throw new NullPointerException("One of the points is null");
            if (points[i].equals(points[i + 1]))    throw new IllegalArgumentException(points[i] + "point is repeated");
        }
    }
}