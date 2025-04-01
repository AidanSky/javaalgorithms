import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> Segments = new ArrayList<LineSegment>();
    private int counter = 0;
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        // create origin, then order the rest of the points by their slope 
        // create origin point, then check if there are 3 points connected to it
        // check each pair
        // compare slopes of all pairs of pairs

        // error handling 
        if (points == null) throw new IllegalArgumentException("Points are null");

        // create clone 
        Point[] pointsCopy = points.clone();
        for (int t = 0; t < pointsCopy.length; t++) {
            if (pointsCopy[t] == null) throw new IllegalArgumentException("Point cannot be null");
        }

        Arrays.sort(pointsCopy);
        // check for duplicates
        for (int r = 0; r < pointsCopy.length - 1; r++) {
            if (pointsCopy[r].compareTo(pointsCopy[r + 1]) == 0) {
                throw new IllegalArgumentException("Duplicate points found");
            }
        }

        for (int i = 0; i < points.length; i++) {
            // error handling -- needs to also check for duplicates
            Point origin = pointsCopy[i];
            Point[] originless = new Point[points.length-1];

            // create copy of copy without origin point that can be sorted via slopeorder
            int offset = 0;
            for (int j = 0; j < pointsCopy.length; j++) {
                if (j != i) {
                    originless[offset++] = pointsCopy[j];
                }
            }

            // sort through all other points via Arrays.sort via slopeorder
            Arrays.sort(originless, origin.slopeOrder());

            // create array list, point will be added whenever collinear > 3 and new point is recognized

            // iterate through all other points and count identical ones
            for (int k = 0; k < originless.length; k++) { // iterate through originless points, check if slopes are identical, if identical, increase collinear counts and add to vector
                double slope = origin.slopeTo(originless[k]);
                int start = k;
                while (k + 1 < originless.length && origin.slopeTo(originless[k+1]) == slope) { // iterate through sorted slopes until no longer matches
                    k++;
                }
                int alpha = k - start + 1;

                if (alpha >= 3) { // keep track of when at least 3 collinear points are found, at which point go backwords 'collinear' amount of times and put all into array
                    Point[] CollinearArray = new Point[alpha+1];
                    CollinearArray[0] = origin;
                    for (int b = 0; b < alpha; b++) {
                        CollinearArray[b+1] = originless[start+b];
                    }
                    // add to segment as long as origin is the smallest in array
                    Arrays.sort(CollinearArray);
                    if (CollinearArray[0] == origin) {
                        Segments.add(new LineSegment(CollinearArray[0], CollinearArray[alpha]));
                        counter++;
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return counter;
    }

    // the line segments  
    public LineSegment[] segments() {
        return Segments.toArray(new LineSegment[counter]);
    }               
 }