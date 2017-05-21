import java.util.ArrayList;
import java.util.Arrays;

/**
 * Examines 4 points at a time and checks whether they all lie on the same line segment, returning all such line segments
 */
public class BruteCollinearPoints {

    private LineSegment[] lineSegments;
    
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException("The points array cannot be null.");
        }
        Point[] pointsClone = points.clone();
        Arrays.sort(pointsClone);
        checkDuplicatedPoints(pointsClone);
        lineSegments = findCollinearPoints(pointsClone);
    }
    
    private void checkDuplicatedPoints(Point[] points) {    
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null || points[i + 1] == null) {
                throw new NullPointerException("There are null Points in the array.");
            }
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("There are duplicated points in the array.");
            }
        }
    }
    
    private LineSegment[] findCollinearPoints(Point[] points) {
        
        ArrayList<LineSegment> lineSegmentsFound = new ArrayList<LineSegment>(); 
        
        Arrays.sort(points);
        
        for (int firstPointIndex = 0; firstPointIndex < points.length - 3; firstPointIndex++) {
            for (int secondPointIndex = firstPointIndex + 1; secondPointIndex < points.length - 2; secondPointIndex++) {
                for (int thirdPointIndex = secondPointIndex + 1; thirdPointIndex < points.length - 1; thirdPointIndex++) {
                    for (int fourthPointIndex = thirdPointIndex + 1; fourthPointIndex < points.length; fourthPointIndex++) {
                        
                        Point firstPoint = points[firstPointIndex];
                        Point secondPoint = points[secondPointIndex];
                        Point thirdPoint = points[thirdPointIndex];
                        Point fourthPoint = points[fourthPointIndex];
                        
                        double firstSecondSlop = firstPoint.slopeTo(secondPoint);
                        double secondThirdSlop = secondPoint.slopeTo(thirdPoint);
                        double thirdFourthSlop = thirdPoint.slopeTo(fourthPoint);
                                               
                        // Note the usage of the non-short-circuit logic operator
                        if (firstSecondSlop == secondThirdSlop & secondThirdSlop == thirdFourthSlop) { 
                            LineSegment lineSegment = new LineSegment(firstPoint, fourthPoint);                      
                            lineSegmentsFound.add(lineSegment);                   
                        }
                    }
                }
            }
        }
        
        return lineSegmentsFound.toArray(new LineSegment[0]);
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        // Clone the object to avoid returning a reference to the mutable object of the class
        // Do not expose the internal representation 
        return lineSegments.clone();
    }
}
