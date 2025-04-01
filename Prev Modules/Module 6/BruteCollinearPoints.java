import java.util.Arrays;
import java.util.ArrayList;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> Segments = new ArrayList<LineSegment>();
    private int counter = 0;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        // each # in array is a Point, does a linesegment need to be created for each combination?

        // throw exception if array is null
        if (points == null) throw new IllegalArgumentException("Points are null");

        // throw exception if any points are null while iterating through them

        // throw exception if any points are repeated (checked via neg infinity)

        for (int i = 0; i < points.length-3; i++) {
            for (int j = i+1; j < points.length-2; j++) {
                for (int k = j+1; k < points.length-1; k++) {
                    for (int l = k+1; l < points.length; l++) {

                        // check for errors: 
                        if (points[i] == null || points[j] == null || points[k] == null || points[l] == null) {
                            throw new IllegalArgumentException("null point detected");
                        }
                        if (points[j] == points[i] || points[k] == points[i] || points[l] == points[i]) {
                            throw new IllegalArgumentException("Duplicate point detected");
                        }
                        // if i and j same slope, k and l same slope, i and l same slope, create line from two furthest apart
                        double slopeij = points[i].slopeTo(points[j]);
                        double slopeik = points[i].slopeTo(points[k]);
                        double slopeil = points[i].slopeTo(points[l]);

                        if (Double.compare(slopeij, slopeik) == 0 && Double.compare(slopeij, slopeil) == 0) {
                            
                            // only add between largest and smallest
                            // int first = points[i].compareTo(points[j]);
                            // int second = points[k].compareTo(points[l]);
                            // int third = points[i].compareTo(points[l]);
                            // int fourth = points[j].compareTo(points[k]);
                            Point[] PointsForLine = {points[i], points[j], points[k], points[l]};
                            Arrays.sort(PointsForLine);
                            
                            Segments.add(new LineSegment(PointsForLine[0], PointsForLine[3]));
                            counter++;
                        }
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