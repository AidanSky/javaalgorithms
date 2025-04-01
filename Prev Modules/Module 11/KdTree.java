import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
    private Node root;
    private int counter;
    private RectHV rect;

    private class Node {
        // should be able to iterate via both x and y, color should determine which?

        Point2D point;
        Node left;
        Node right;
        double x;
        double y;
        int depth; // track depth of point for insertion
        boolean vertical; // this should be used to determine if x or y should be used to sort in next branch/insert, might be easier than repeating axis call
        // how to track depth for modulo
        RectHV rect;

        public Node(Point2D point) {
            this.point = point;
            this.x = point.x();
            this.y = point.y();
            this.left = null;
            this.right = null;
            this.vertical = true; // is this necessary?
            this.rect = null;
        }
    }

    public         KdTree()                               // construct an empty set of points 
    {
        this.rect = new RectHV(0, 0, 1, 1);
        root = null;
        counter = 0;
    }

    public           boolean isEmpty()                      // is the set empty? 
    {
        if (root == null) { return true; }
        return false;
    }
    public               int size()                         // number of points in the set 
    {
        // determine by amount of times (insert) is called?
        // determine by amount of rectangles?
        return counter;
    }
    private Node insertPoint(Node root, Point2D p, int depth) { // helper function for insertion simplification, do rectangles need to be created?
        boolean vertical;

        // error handling 
        if (p == null) {
            throw new IllegalArgumentException("point cannot be null");
        }

        // if no root, create and return root
        if (root == null) {
            Node newNode = new Node(p);
            newNode.depth = 0;
            newNode.vertical = true;
            newNode.rect = new RectHV(0, 0, 1, 1);
            counter++;
            return newNode;
        }

        // check dimensionality and increase depth for next iteration
        if (depth % 2 == 0) {
            vertical = true;
            root.vertical = true;
        } else { vertical = root.vertical = false; }

        if (p.equals(root.point)) { return root; }

        // insert point when null found
        if (vertical) { // insert based on y axis
            if (p.x() < root.x) { // is dummy variable needed for recursion so root isn't replaced?
                root.left = insertPoint(root.left, p, depth+1);
                if (root.left != null) {
                    root.left.rect = new RectHV(root.rect.xmin(), root.rect.ymin(), root.x, root.rect.ymax());
                }
            } else {
                root.right = insertPoint(root.right, p, depth+1);
                if (root.right != null) {
                    root.right.rect = new RectHV(root.x, root.rect.ymin(), root.rect.xmax(), root.rect.ymax());
                }
            }
        } else { // insert based on x axis
            if (p.y() < root.y) {
                root.left = insertPoint(root.left, p, depth+1);
                if (root.left != null) {
                    root.left.rect = new RectHV(root.rect.xmin(), root.rect.ymin(), root.rect.xmax(), root.y);
                }
            } else {
                root.right = insertPoint(root.right, p, depth+1);
                if (root.right != null) {
                    root.right.rect = new RectHV(root.rect.xmin(), root.y, root.rect.xmax(), root.rect.ymax());
                }
            }
        }

        return root;
    }
    public              void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        // // initialize new node that will be inserted
        // Node newNode = new Node(p);

        // // error handling
        // if (p == null) {
        //     throw new IllegalArgumentException("point cannot be null");
        // }
        // if (newNode.left.point == p || newNode.right.point == p) { // rework this
        //     throw new IllegalArgumentException("cannot be identical to point already in set");
        // }

        // // if first node in set, return
        // if (root == null) {
        //     root.point = p; 
        //     return;
        // }

        // // define node to traverse through list 
        // Node traversal = root;

        // // create rectangle?

        // // determine directionality with modulo

        // if (traversal.left.point == null || traversal.right.point == null) { // if null, found place to insert, needs to check if exists/continas first tho
        //     // MUST INCLUDE DIRECTIONALITY !
        //     if (p.x > traversal.point.x) {
        //         traversal.left.point = p;
        //     } else if (p.x < traversal.point.x) {
        //         traversal.right.point = p;
        //     } else { return; } // Return nothing if point is the same? ?? ? ? ?

        // } else if (p > newNode.left.point) { // THIS NEEDS TO CHANGE FOR X AND Y DEPENDING ON LEVEL || IF NOT YET NULL, RECURSIVELY CALL UNTIL NULL DEPENDING ON SIDE
        //     insert(p);
        // } else if (p < newNode.right.point) { // this code is garbage
        //     insert(p);
        // }
        root = insertPoint(root, p, 0);
    }
    public           boolean contains(Point2D p)            // does the set contain point p? 
    {
        // to make sure don't have to gothrough all of them, should only go down necessary path to check ?
        // does this hafta use rectangles ?
        if (p == null) {
            throw new IllegalArgumentException("cannot be null");
        }
        return containsHelper(root, p, 0);
    }
    private boolean containsHelper(Node search, Point2D p, int depth) {
        boolean vertical;

        if (search == null) { return false; } // if no tree, cannot contain it
        
        if (search.point.equals(p)) { return true; } // if this is the same point, is true

        if (search.left == null && search.right == null) { return false; } // if nothing left in tree and not found, must be false

        if (depth % 2 == 0) { vertical = true; } else { vertical = false; }

        if (vertical) {
            if (p.x() < search.x) {
                return containsHelper(search.left, p, depth + 1);
            } else if (p.x() > search.x) {
                return containsHelper(search.right, p, depth + 1);
            } else {
                return containsHelper(search.left, p, depth + 1) || containsHelper(search.right, p, depth+1);
            }
        } else {
            if (p.y() < search.y) {
                return containsHelper(search.left, p, depth + 1);
            } else if (p.y() > search.y) {
                return containsHelper(search.right, p, depth + 1);
            } else {
                return containsHelper(search.left, p, depth + 1) || containsHelper(search.right, p, depth+1);
            }
        }
    }
    public              void draw()                         // draw all points to standard draw 
    {
        // Iterate through all points, draw a line at their x or y cord (vert or hori) until null (make sure both sides are drawn), needs to only go to next line, not just wall
        // how to draw rectangles? can be done at time of tree creation?
        // does vertical need to be stored as a separate bool for each point?
        // each point should create two new rectangles and store in the node?

        // if a given point is vertical, line should be drawn based on y axis, vice versa for horizontal
        // when splitting a point, draw one new rectangle to partition the space, old space can stay as old rectangle ?

        // iterate through all points 
        // create rectangle at upper end of current rectangle if vertical, left end if horizontal (should this change for left/right, top/bottom side?)
        // draw a point for each new rectangle created
            // is it easier to have a helper function for splitting at each end?
        drawHelper(root, 0);
        
    }
    private void drawHelper(Node traverse, int depth) {
        // draw rectangle + point for each new node introduced
        if (traverse == null) {
            return;
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        traverse.point.draw();

        // determine axis
        int axis = depth % 2;
        boolean vertical;
        if (axis == 0) { vertical = true; } else { vertical = false; }

        // determine where to split
        double split;
        if (vertical) { split = traverse.x; } else { split = traverse.y; }

        // recursively visit each node and create/draw a rectangle
        if (vertical) { 
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(traverse.x, traverse.rect.ymin(), traverse.x, traverse.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(traverse.rect.xmin(), traverse.y, traverse.rect.xmax(), traverse.y);
        }
        // splitRectangle(traverse, axis, split);
        drawHelper(traverse.left, depth +1);
        drawHelper(traverse.right, depth + 1);

        // when recursively calling, 'rect' variable should be determined as top or bottom depeneding on if higher or lower (dependant on axis)
        
    }
    // public void splitRectangle(Node travel, int axis, double split) {
    //     // do this when generating?
    //     RectHV leftRectangle; // = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), rect.ymax());
    //     RectHV rightRectangle; // = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), rect.ymax());

    //     if (axis == 0) { // split on the vertical
    //         leftRectangle = new RectHV(split, travel.rect.ymin(), travel.rect.xmax(), travel.rect.ymax());
    //         rightRectangle = new RectHV(travel.rect.xmin(), travel.rect.ymin(), split, travel.rect.ymax());
    //     } else { // split on horizontal
    //         leftRectangle = new RectHV(travel.rect.xmin(), split, travel.rect.xmax(), travel.rect.ymax());
    //         rightRectangle = new RectHV(travel.rect.xmin(), travel.rect.ymin(), travel.rect.xmax(), split);
    //     }
    //     leftRectangle.draw();
    //     rightRectangle.draw(); // should change color depending on if horizontal or vertical split

    //     if (travel.left != null) {
    //         travel.left.rect = leftRectangle;
    //     }
    //     if (travel.right != null) {
    //         travel.right.rect = rightRectangle;
    //     }

    // }

    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary) 
    {
        // iterate through tree, check if rect is greater than or less than point 

        // if higher/whatever of axis, move to the left

        // if intersects, check both sides, and point for if it is in the rectangle

        // error handling
        if (rect == null) { throw new IllegalArgumentException("cannot ben ull"); }

        // initialize array list
        ArrayList<Point2D> range = new ArrayList<>();

        // perform recursive function
        rangeHelper(root, 0, rect, range);

        return range;
    }
    private void rangeHelper(Node traverse, int depth, RectHV rect, ArrayList<Point2D> range) {
        if (traverse == null) {
            return;
        }
        boolean vertical;
        if (depth % 2 == 0) {
            vertical = true;
        } else { vertical = false; }

        if (rect.contains(traverse.point)) {
            range.add(traverse.point);
        }

        // this code also needs to check for if they intersect
        if (vertical) { // check if rect is left or right of point

            // check if entire thing is left or right
            if (rect.xmax() < traverse.x) {
                rangeHelper(traverse.left, depth + 1, rect, range);
            } else if (rect.xmin() > traverse.x) {
                rangeHelper(traverse.right, depth + 1, rect, range);
            } else {
                rangeHelper(traverse.left, depth + 1, rect, range);
                rangeHelper(traverse.right, depth + 1, rect, range);
            }
        } else { // check if above or below point
            if (rect.ymax() < traverse.y) {
                rangeHelper(traverse.left, depth+1, rect, range);
            } else if (rect.ymin() > traverse.y) {
                rangeHelper(traverse.right, depth+1, rect, range);
            } else {
                rangeHelper(traverse.left, depth+1, rect, range);
                rangeHelper(traverse.right, depth+1, rect, range);
            }
        }

        // if intersects, check both
        // if doesn't intersect, recursively sort through until something does, or null
        // if a rect intersects, check if the rectangle's owner is included
    }
    public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
    {
        // keep track of closest point to p so far
        // recursively go through all points to see if one can be found that is closer
            // when accessing a given point, determine which of its subtrees would contain a closer one
            // i.e. always choose the subtree that is in the same split as p

        if (p == null) { throw new IllegalArgumentException("p cannot be null"); }
        if (root == null) { return null; }
        return nearestHelper(root, 0, p, root.point);
    }
    private Point2D nearestHelper(Node traverse, int depth, Point2D trace, Point2D closest) {
        // if vertical node, check if left or right, then check if node is closest 
        // if horizontal, check if higher or lower, then check if node is closest
        if (traverse == null) { return closest; }

        boolean vertical; 
        if (depth % 2 == 0) { vertical = true; } else { vertical = false; }

        double closestDist = closest.distanceSquaredTo(trace);
        double currentDist = traverse.point.distanceSquaredTo(trace);

        if (currentDist < closestDist) {
            closest = traverse.point;
            closestDist = currentDist;
        }

        // determine sort priority
        Node priority, secondary;
        if (vertical) {
            if (trace.x() < traverse.x) {
                priority = traverse.left;
                secondary = traverse.right;
            } else {
                priority = traverse.right;
                secondary = traverse.left;
            }
        } else {
            if (trace.y() < traverse.y) {
                priority = traverse.left;
                secondary = traverse.right;
            } else {
                priority = traverse.right;
                secondary = traverse.left;
            }
        }

        closest = nearestHelper(priority, depth + 1, trace, closest);

        closestDist = closest.distanceSquaredTo(trace);

        if (secondary != null && secondary.rect != null) {
            double rectDist = secondary.rect.distanceSquaredTo(trace);
            if (rectDist <= closestDist) {
                closest = nearestHelper(secondary, depth + 1, trace, closest);
            }
        }

        // if (traverse.point.distanceTo(trace) < closest.distanceTo(trace)) {
        //     closest = traverse.point;
        // }

        // if (vertical) {
        //     if (trace.x() > traverse.x) {
        //         nearestHelper(traverse.left, depth + 1, trace, closest);
        //     } else if (trace.x() < traverse.x) {
        //         nearestHelper(traverse.right, depth + 1, trace, closest);
        //     } else { return closest; }
        // } else {
        //     if (trace.y() > traverse.y) {
        //         nearestHelper(traverse.left, depth + 1, trace, closest);
        //     } else if (trace.y() < traverse.y) {
        //         nearestHelper(traverse.right, depth + 1, trace, closest);
        //     } else { return closest; }
        // }

        // nearestHelper(traverse.left, depth + 1, trace, closest);
        // nearestHelper(traverse.right, depth + 1, trace, closest);

        return closest;
    }
 
    public static void main(String[] args) {
        return;
    }            // unit testing of the methods (optional) 
 }