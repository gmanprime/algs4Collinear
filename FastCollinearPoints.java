import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FastCollinearPoints {

    private final ArrayList<LineSegment> lineSeg = new ArrayList<>();
    private final HashMap<Double, List<Point>> slopeToEndPoints = new HashMap<>();

    /* Find all line segments containing 4 or more points */



    public FastCollinearPoints(Point[] pts) {
        checkNull(pts); // checks if input array is Empty

        for (Point pt : pts) {
            checkNull(pt); // checks if any point values are empty
        }

        Point[] orderedPoints = pts.clone(); // copies points into a kind of auxiliary array
        Arrays.sort(orderedPoints); // Java API, Arrays Sort Method
        dCheck(orderedPoints); // check weather any of the adjacent values are the same

        // For each element in the ordered points array
        for (int i = 0; i < orderedPoints.length; i++) {

            // sort array elements from i to the last element
            // stable sort / Merge sort
            Arrays.sort(orderedPoints, i, orderedPoints.length);

            // set current origin point
            Point origin = orderedPoints[i];

            // Sorts the array from following position to end of array using custom comparator
            // i.e. sort by values above and by comparator values next
            // stable sort / merge sort
            Arrays.sort(orderedPoints, i + 1, orderedPoints.length, origin.slopeOrder());


            int counter = 0; // set up counter variable to count number of points in line segment
            double prevSlope = Double.NaN; // Set the previous Slope value to NaN to Start
            Point prevPoint = origin; // set the previous points as origin for future compares

            for (int j = i + 1; j < orderedPoints.length; j++) {
                // nested for current i to end of ordered points list

                // set new variable pt tp the current array value
                Point pt = orderedPoints[j];

                // set the slope variable to the slope from origin to the point pt
                double slope = origin.slopeTo(pt);

                // if the current slope is equal to the previous slope
                if (Double.compare(slope, prevSlope) == 0) {
                    counter += 1; // increase number of points in line segment
                } else {
                    // check if the number of points in line segment are already at 3 or greater
                    if (counter >= 3) {
                        checkAndAdd(origin, prevPoint, prevSlope);
                    }

                    prevSlope = slope;
                    counter = 1;
                }
                prevPoint = pt;
            }

            if (counter >= 3) {
                checkAndAdd(origin, prevPoint, prevSlope);
            }
        }
    }

    private void checkAndAdd(Point start, Point end, double slope) {

        List<Point> ptExist = slopeToEndPoints.get(slope);

        if (ptExist == null) {
            ptExist = new ArrayList<>(); // empty the array
            ptExist.add(end);

            slopeToEndPoints.put(slope, ptExist);

            lineSeg.add(new LineSegment(start, end));
        } else {
            if (!ptExist.contains(end)) {
                // add if point is non existent
                ptExist.add(end);

                lineSeg.add(new LineSegment(start, end));
            }
        }
    }

    private void dCheck(Point[] orderedPointValues) {
        // throw error if any adjacent two points are equal
        for (int i = 0; i < orderedPointValues.length - 1; i++) {
            if (orderedPointValues[i].compareTo(orderedPointValues[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     * the number of line segments
     */
    public int numberOfSegments() {
        return lineSeg.size();
    }

    /**
     * the line segments
     */
    public LineSegment[] segments() {
        return lineSeg.toArray(new LineSegment[]{});
    }

    private void checkNull(Object obj) {
        /* Check Weather an Object Value is null */
        if (obj == null) {
            throw new IllegalArgumentException();
        }
    }
    public static void main(String[] args) {
        /* Read input Data From File */

        In inputData = new In(args[0]);
        int n = inputData.readInt();
        Point[] pts = new Point[n];

        for (int i = 0; i < n; i++) {
            int x = inputData.readInt();
            int y = inputData.readInt();
            pts[i] = new Point(x, y);
        }

        /* Plot Data Points */
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : pts) {
            p.draw();
        }
        StdDraw.show();

        /*  display and draw the line segments */
        FastCollinearPoints collinear = new FastCollinearPoints(pts);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
