import java.util.*;

public class FastCollinearPoints {
    private List<LineSegment> segments = new ArrayList<LineSegment>();
    
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        checkDuplicatedEntries(points);
        
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Map<Double, List<Point>> foundSegments = new HashMap<Double, List<Point>>();
        
        for (Point startPoint : pointsCopy) {
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
    
    
    
    private void addSegmentIfNew(Map<Double, List<Point>> foundSegments, double slope, ArrayList<Point> slopePoints) {           
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
                if (currentEndPoint.compareTo(endPoint) == 0) return;
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
        if (points == null) throw new NullPointerException("points is null");
        
        for (int i = 0; i < points.length -1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i] == null || points[j] == null) throw new NullPointerException("One of the points is null");
                if (points[i].compareTo(points[j]) == 0)    throw new IllegalArgumentException(points[i].toString() + "point is repeated");
            }
        }
    }
}