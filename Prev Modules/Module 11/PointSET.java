import edu.princeton.cs.algs4.SET;
import java.util.ArrayList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
    private SET<Point2D> set = new SET<>();

    public         PointSET()                               // construct an empty set of points 
    {
        // construct kd tree
        this.set = new SET<Point2D>();
    }
    public           boolean isEmpty()                      // is the set empty? 
    {
        if (set.size() == 0) {
            return true;
        }
        return false;
    }
    public               int size()                         // number of points in the set 
    {
        return set.size();
    }
    public              void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if (p == null) {
            throw new IllegalArgumentException("point cannot be null");
        }
        if (set.contains(p)) {
            return;
        }

        set.add(p);
    }
    public           boolean contains(Point2D p)            // does the set contain point p? 
    {
        if (p == null) { throw new IllegalArgumentException("p cannot be null"); }
        return set.contains(p);

    }
    public              void draw()                         // draw all points to standard draw 
    {
        for (Point2D p : set) {
            p.draw();
        }
    }
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary) 
    {
        // to make > linear time, check BST for lowest and highest ? if possible
        // search for min and max within confines of given rect, return all elements within

        // error handling
        if (rect == null) throw new IllegalArgumentException("can't be null");

        // define iterable to be returned
        ArrayList<Point2D> items = new ArrayList<>();

        for (Point2D p : set) {
            if (rect.contains(p)) {
                items.add(p);
            }
        }
        return items;
    }
    public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
    {
        if (p == null) throw new IllegalArgumentException("p cannot be null");

        double distance = -1;
        Point2D nearest = null;

        if (set.isEmpty()) { return nearest; }
        
        // check distance between all points? or is that too inefficient
        for (Point2D points : set) {
            if (points.equals(p)) { continue; }
            if (distance == -1) {
                distance = p.distanceTo(points);
            }
            if (p.distanceTo(points) < distance) {
                distance = p.distanceTo(points);
                nearest = points;
            }
        }
        return nearest;
    }
 
    public static void main(String[] args)                  // unit testing of the methods (optional) 
    {
        return;
    }
 }