import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final List<LineSegment> lineSeg = new ArrayList<>();

    /* Find all lise segments with 4 points */

    public BruteCollinearPoints(Point[] points) {
        checkNull(points);
        for (Point p : points) {
            checkNull(p);
        }

        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        dCheck(sortedPoints);
        for (int i = 0; i < sortedPoints.length; i++) {
            for (int j = i + 1; j < sortedPoints.length; j++) {
                for (int k = j + 1; k < sortedPoints.length; k++) {
                    for (int x = k + 1; x < sortedPoints.length; x++) {
                        Point pt1 = sortedPoints[i];
                        Point pt2 = sortedPoints[j];
                        Point pt3 = sortedPoints[k];
                        Point pt4 = sortedPoints[x];
                        // the array [pt1, pt2, pt3, pt4] is organized ascending order.

                        if (collinearity(pt1, pt2, pt3, pt4)) {
                            lineSeg.add(new LineSegment(pt1, pt4));
                        }
                    }
                }
            }
        }
    }

    private void dCheck(Point[] sortedPointValues) {
        for (int i = 0; i < sortedPointValues.length - 1; i++) {
            if (sortedPointValues[i].compareTo(sortedPointValues[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    private boolean collinearity(Point pt1, Point pt2, Point pt3, Point pt4) {
        double s1 = pt1.slopeTo(pt2);
        double s2 = pt2.slopeTo(pt3);
        double s3 = pt3.slopeTo(pt4);

        return s2 == s3 && s1 == s2;
    }

    public int numberOfSegments() {
        /* returns number of line segments */
        return lineSeg.size();
    }

    public LineSegment[] segments() {
        /* return the line segment */
        return lineSeg.toArray(new LineSegment[0]);
    }

    private void checkNull(Object obj) {
        /* Check Weather an Object Value is null */
        if (obj == null) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {
    //    Read The Point values from file
        In inputData = new In(args[0]);
        int n = inputData.readInt();
        Point[] pts = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = inputData.readInt();
            int y = inputData.readInt();
            pts[i] = new Point(x, y);
        }
        /* plot the values */

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : pts) {
            p.draw();
        }
        StdDraw.show();

        /*  display and draw the line segments */

        BruteCollinearPoints collinear = new BruteCollinearPoints(pts);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
