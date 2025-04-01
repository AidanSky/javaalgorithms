import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    // constructs the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }                     
 
    // draws this point
    public void draw() {
        StdDraw.point(x, y);
    }
    
    // draws the line segment from this point to that point
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }                  

    // string representation
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
 
    // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y > that.y) return 1;
        if (this.y < that.y) return -1;
        if (this.x > that.x) return 1;
        if (this.x < that.x) return -1;
        return 0;
    }

    // the slope between this point and that point
    public double slopeTo(Point that) {
        if (this.y == that.y && this.x == that.x) return Double.NEGATIVE_INFINITY;  
        if (this.y == that.y) return 0.0;
        if (this.x == that.x) return Double.POSITIVE_INFINITY;
        return (double) (that.y - this.y) / (that.x - this.x);
    }      

    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        return new slopeCompare();
    }

    private class slopeCompare implements Comparator<Point> {
        public int compare(Point a, Point b) {
            double slopea = slopeTo(a);
            double slopeb = slopeTo(b);
            return Double.compare(slopea, slopeb);
        }
    }
 }