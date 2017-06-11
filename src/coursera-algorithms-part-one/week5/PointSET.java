import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
    
    private TreeSet<Point2D> points;
    
    public PointSET() {
        points = new TreeSet<Point2D>();
    }
    
    public boolean isEmpty() {
        return points.isEmpty();
    }
    
    public int size() {
        return points.size();
    }
    
    public void insert(Point2D point) {
        if (point == null) throw new NullPointerException("The point cannot be null.");
        if (contains(point)) return;
        points.add(point);
    }
    
    public boolean contains(Point2D point) {
        if (point == null) throw new NullPointerException("The point cannot be null.");
        return points.contains(point);
    }
    
    public void draw() {
        if (isEmpty()) return;      
        for (Point2D point : points) {
            point.draw();
        }
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException("The rect cannot be null.");
        Queue<Point2D> pointsInsideTheRectagle = new Queue<Point2D>();
        for (Point2D point : points) {
            if (rect.contains(point)) {
                pointsInsideTheRectagle.enqueue(point);
            }
        }
        return pointsInsideTheRectagle;
    }
    
    public Point2D nearest(Point2D point) {
        if (point == null) throw new NullPointerException("The point cannot be null.");
        if (points.isEmpty()) return null;
        Point2D nearestNeighbor = null;
        double distanceToNeighbor = Double.MAX_VALUE;
        for (Point2D currentPoint : points) {
            double currentDistance = point.distanceTo(currentPoint);
            if (currentDistance < distanceToNeighbor) {
                distanceToNeighbor = currentDistance;
                nearestNeighbor = currentPoint;
            }
        }
        return nearestNeighbor;
    }
}