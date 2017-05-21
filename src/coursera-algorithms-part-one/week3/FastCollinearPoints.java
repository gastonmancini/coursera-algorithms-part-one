import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FastCollinearPoints {

    private ArrayList<LineSegment> lineSegments = new ArrayList<LineSegment>();

    public FastCollinearPoints(Point[] points) {
        
        if (points == null) {
            throw new NullPointerException("The points array cannot be null.");
        }
        
        Point[] pointsClone = points.clone();
        Arrays.sort(pointsClone);
        
        checkDuplicatedPoints(pointsClone);
        
        findCollinearPoints(pointsClone);
    }
    
    private void checkDuplicatedPoints(Point[] points) {    
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null || points[i + 1] == null) {
                throw new NullPointerException("There are null points in the array.");
            }
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("There are duplicated points in the array.");
            }
        }
    }
    
    private void findCollinearPoints(Point[] points) {
        
        Map<Double, ArrayList<Point>> lineSegmentsFound = new HashMap<Double, ArrayList<Point>>();
        
        for (Point originPoint : points) { // Think of p as the origin.
            
            Arrays.sort(points, originPoint.slopeOrder()); // Sort the points according to the slopes they makes with p.
            
            ArrayList<Point> pointsList = new ArrayList<Point>();
            
            double slope = 0;
            double previousSlope = Double.NEGATIVE_INFINITY;
            
            // Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p. 
            // If so, these points, together with p, are collinear
            for (int i = 1; i < points.length; i++) {
                
                Point adjacentPoint = points[i];
                slope = originPoint.slopeTo(adjacentPoint);

                if (slope != previousSlope) {
                    
                    if (pointsList.size() >= 3) {
                        pointsList.add(originPoint);      
                        includeSegmentIfItsNew(lineSegmentsFound, previousSlope, pointsList);
                    }
                    
                    pointsList.clear();
                }
                
                pointsList.add(adjacentPoint);        
                previousSlope = slope;
            }
        }
    }

    private void includeSegmentIfItsNew(Map<Double, ArrayList<Point>> lineSegmentsFound, double slope,
            ArrayList<Point> points) {

        Collections.sort(points);
        Point originPoint = points.get(0);
        Point lastPoint = points.get(points.size() - 1);
        
        if (!lineSegmentsFound.containsKey(slope)) {
            lineSegmentsFound.put(slope, new ArrayList<Point>(Arrays.asList(lastPoint)));
            lineSegments.add(new LineSegment(originPoint, lastPoint));
        } else {
            for (Point currentLastPoint : lineSegmentsFound.get(slope)) {
                if (currentLastPoint.compareTo(lastPoint) == 0) {
                    return;
                }
            }
            lineSegmentsFound.get(slope).add(lastPoint);
            lineSegments.add(new LineSegment(originPoint, lastPoint));
        }
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }
}
