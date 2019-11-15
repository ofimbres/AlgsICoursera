import java.util.Queue;
import java.util.LinkedList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    
    private Node root;
    private int count;
    
    // construct an empty set of points
    public KdTree() {
    }
    
    // is the set empty?
    public boolean isEmpty() {
        return (root == null);
    }
    
    // number of points in the set
    public int size() {
        return count;
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("p cannot be null");
        }
        
        // if p is already in the set, then ignore
        if (contains(p)) {
            return;
        }
        
        // root node always starts dividing the grid in x-axis
        RectHV rect = (root == null) ? new RectHV(0, 0, 1, 1) : root.rect;
        root = insert(root, p, rect, true);
        count++;
    }
    
    private Node insert(Node node, Point2D p, RectHV rect, boolean isVOrientation) {
        if (node == null) {
            return new Node(p, rect);
        }
        
        // if the point to be inserted has a smaller x-coordinate than the point at the node, go left;
        // otherwise go right
        if (isVOrientation) {
            if (p.x() < node.point.x()) {
                rect = (node.lb == null) ? new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax()) : node.lb.rect;
                node.lb = insert(node.lb, p, rect, !isVOrientation);
            } else {
                rect = (node.rt == null) ? new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax()) : node.rt.rect;
                node.rt = insert(node.rt, p, rect, !isVOrientation);
            }
        } else {
            if (p.y() < node.point.y()) {
                rect = (node.lb == null) ? new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y()) : node.lb.rect;
                node.lb = insert(node.lb, p, rect, !isVOrientation);
            } else {
                rect = (node.rt == null) ? new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax()) : node.rt.rect;
                node.rt = insert(node.rt, p, rect, !isVOrientation);
            }
        }
        
        return node;
    }
    
    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("p cannot be null");
        }
        
        return contains(p, root, true);
    }
    
    private boolean contains(Point2D p, Node node, boolean isVOrientation) {
        if (node == null) {
            return false;
        }
        
        if (p.equals(node.point)) {
            return true;
        }
        
        if (isVOrientation) {
            if (p.x() < node.point.x()) {
                return contains(p, node.lb, !isVOrientation);
            } else {
                return contains(p, node.rt, !isVOrientation);
            }
        } else {
            if (p.y() < node.point.y()) {
                return contains(p, node.lb, !isVOrientation);
            } else {
                return contains(p, node.rt, !isVOrientation);
            }
        }
    }
    
    // draw all points to standard draw 
    public void draw() {
        draw(root, true);
    }
    
    // pre-order traversal
    private void draw(Node node, boolean isVOrientation) {
        if (node == null) {
            return;
        }
        
        // visit node
        StdDraw.setPenRadius(0);
        Point2D p0, p1;
        if (isVOrientation) {
            p0 = new Point2D(node.point.x(), node.rect.ymin());
            p1 = new Point2D(node.point.x(), node.rect.ymax());
            StdDraw.setPenColor(StdDraw.RED);
        } else {
            p0 = new Point2D(node.rect.xmin(), node.point.y());
            p1 = new Point2D(node.rect.xmax(), node.point.y());
            StdDraw.setPenColor(StdDraw.BLUE);
        }
        p0.drawTo(p1);
        
        StdDraw.setPenRadius(0.0125);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.point(node.point.x(), node.point.y());
        
        // traverse node branches
        draw(node.lb, !isVOrientation);        
        draw(node.rt, !isVOrientation);
    }
    
    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("rect cannot be null");
        }
        
        Queue<Point2D> pointsMatchesQueue = new LinkedList<Point2D>();
        range(rect, root, pointsMatchesQueue);
        return pointsMatchesQueue;
    }
    
    // find all points in a query axis-aligned rectangle.
    // * check if point in node lies in given rectangle.
    // * recursively search left/bottom (if any could fall in rectangle).
    // * recursively search right/top (if any could fall in rectangle)
    private void range(RectHV rect, Node node, Queue<Point2D> pointsMatchesQueue) {
        if (node == null || !node.rect.intersects(rect)) {
            return;
        }
        
        if (rect.contains(node.point)) {
            pointsMatchesQueue.add(node.point);
        }
        
        range(rect, node.lb, pointsMatchesQueue);
        range(rect, node.rt, pointsMatchesQueue);
    }
    
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {  
        if (p == null) {
            throw new IllegalArgumentException("p cannot be null");
        }
        
        if (root == null) {
            return null;
        }
        
        return nearest(p, root, root.point);
    }
    
    // * check distance from point in node to query point.
    // * recursively search left/bottom (if it could contain a closer point).
    // * recursively search right/top (if it could contain a closer point).
    // * organize method so that it begins by searching for query point.
    private Point2D nearest(Point2D queryPoint, Node node, Point2D currentClosestPoint) {
        if (node == null) {
            return currentClosestPoint;
        }
        
        double distanceChampion = currentClosestPoint.distanceSquaredTo(queryPoint);
        double distanceNodeRect = node.rect.distanceSquaredTo(queryPoint);
        
        // prune node?
        if (distanceChampion < distanceNodeRect) {
            return currentClosestPoint;
        }
        
        double distanceNode = node.point.distanceSquaredTo(queryPoint);
        if (distanceNode < distanceChampion) {
            currentClosestPoint = node.point;
        }
        
        if (less(node, queryPoint)) {
            currentClosestPoint = nearest(queryPoint, node.lb, currentClosestPoint);
            currentClosestPoint = nearest(queryPoint, node.rt, currentClosestPoint);
        } else {
            currentClosestPoint = nearest(queryPoint, node.rt, currentClosestPoint);
            currentClosestPoint = nearest(queryPoint, node.lb, currentClosestPoint);
        }
  
        return currentClosestPoint;
    }
    
    // determine the node branch with less distance to the query point.
    // if true, lb is a closer node than rt.
    private boolean less(Node n, Point2D q) {
        double d1 = (n.lb != null) ? n.lb.rect.distanceSquaredTo(q) : Double.POSITIVE_INFINITY;
        double d2 = (n.rt != null) ? n.rt.rect.distanceSquaredTo(q) : Double.POSITIVE_INFINITY;
        
        return d1 < d2;
    }
    
    // unit testing of the methods
    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.1, 0.1));
        kdTree.insert(new Point2D(0.5, 0.5));
        kdTree.insert(new Point2D(0.7, 0.7));
        kdTree.insert(new Point2D(0.9, 0.2));
        
        kdTree.draw();
    }
    
    private static class Node {
        public Point2D point;  // the point
        public RectHV rect;  // the axis-aligned rectangle corresponding to this node
        public Node lb;  // the left/bottom subtree
        public Node rt;  // the right/top subtree
        
        public Node(Point2D p, RectHV r) {
            point = p;
            rect = r;
        }
    }
}