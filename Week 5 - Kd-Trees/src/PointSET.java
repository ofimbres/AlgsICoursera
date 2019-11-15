import java.util.Queue;
import java.util.LinkedList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    
    private final SET<Point2D> pointSet;
    
    // construct an empty set of points
    public PointSET() {
        pointSet = new SET<Point2D>();
    }
    
    // is the set empty?
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }
    
    // number of points in the set
    public int size() {
        return pointSet.size();
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("p cannot be null");
        }
        pointSet.add(p);
    }
    
    // does the set contain point p?
    public boolean contains(Point2D p) {
        return pointSet.contains(p);
    }
    
    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenRadius(0.0125);
        StdDraw.setPenColor(StdDraw.BLACK);
        
        for (Point2D point : pointSet) {
            point.draw(); 
        }
    }
    
    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("rect cannot be null");
        }
        
        Queue<Point2D> pointsMatchesQueue = new LinkedList<Point2D>();
        
        for (Point2D point : pointSet) {
            if (rect.contains(point)) {
                pointsMatchesQueue.add(point);
            }
        }
        return pointsMatchesQueue;
    }
    
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("rect cannot be null");
        }
        
        double closestDistance = Double.POSITIVE_INFINITY;
        Point2D nearestPoint = null;
        
        for (Point2D point : pointSet) {
            double distance = p.distanceSquaredTo(point);
            if (distance < closestDistance) {
                nearestPoint = point;
                closestDistance = distance;
            }
        }
        return nearestPoint;
    }
    
    // unit testing of the methods
    public static void main(String[] args) {
        PointSET pointSet = new PointSET();
        pointSet.insert(new Point2D(0.1, 0.1));
        pointSet.insert(new Point2D(0.5, 0.5));
        pointSet.insert(new Point2D(0.7, 0.7));
        pointSet.insert(new Point2D(0.9, 0.2));
        
        pointSet.draw();
    }
}