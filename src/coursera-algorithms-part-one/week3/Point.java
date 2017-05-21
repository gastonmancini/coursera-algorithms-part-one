import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

/**
 * An immutable data type for points in the plane
 */
public class Point implements Comparable<Point> {

    private final int xCoordinate;
    private final int yCoordinate;
    private final Comparator<Point> bySlope = new BySlope();

    public Point(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    /**
     * Draws this point to standard draw
     */
    public void draw() {
        StdDraw.point(xCoordinate, yCoordinate);
    }

    /**
     * Draws the line segment between this point and the specified point to standard draw
     */
    public void drawTo(Point target) {
        StdDraw.line(this.xCoordinate, this.yCoordinate, target.xCoordinate, target.yCoordinate);
    }

    /**
     * Returns the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        
        if (that == null) {
            throw new NullPointerException("The point cannot be null.");
        }
        
        if (this.yCoordinate == that.yCoordinate) {
            if (this.xCoordinate == that.xCoordinate) {
                return Double.NEGATIVE_INFINITY; // Equal lines
            }
            
            return (double) +0; // Horizontal line
        }
        
        
        if (this.xCoordinate == that.xCoordinate) {
            return Double.POSITIVE_INFINITY; // Vertical line
        }

        return (double) (that.yCoordinate - this.yCoordinate) / (double) (that.xCoordinate - this.xCoordinate);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate
     */
    public int compareTo(Point that) {
        
        if (that == null) {
            throw new NullPointerException("The point cannot be null.");
        }
        
        if (this.yCoordinate < that.yCoordinate ||
           (this.yCoordinate == that.yCoordinate && this.xCoordinate < that.xCoordinate)) {
            return -1;
        }
        if (this.xCoordinate == that.xCoordinate &&
            this.yCoordinate == that.yCoordinate) {
            return 0;
        }
        return 1;
    }

    /**
     * Compares two points by the slope they make with this point
     */
    public Comparator<Point> slopeOrder() {
        return bySlope;
    }

    private class BySlope implements Comparator<Point> {

        @Override
        public int compare(Point point1, Point point2) {

            if (point1 == null || point2 == null) {
                throw new NullPointerException("The point cannot be null.");
            }
            
            double slope1 = slopeTo(point1);
            double slope2 = slopeTo(point2);
            
            if (slope1 < slope2) {
                return -1;
            }
            
            if (slope1 > slope2) {
                return 1;
            }
            
            return 0;
        }      
    }

    public String toString() {
        return "(" + xCoordinate + ", " + yCoordinate + ")";
    }
}