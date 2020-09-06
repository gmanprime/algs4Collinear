import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class FastCollinearPoints {

    private final ArrayList<LineSegment> lineSeg = new ArrayList<>();
    private final DataManager matchingSlopePoints = new DataManager();

    // private final HashMap<Double, List<Point>> slopeToEndPoints = new HashMap<>();

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
            // stable sort / Merge sort over default point values "y"
            Arrays.sort(orderedPoints, i, orderedPoints.length);

            // set current origin point
            Point origin = orderedPoints[i];

            // Sorts the array from following position to end of array using custom comparator
            // i.e. sort by values above and by comparator values next
            // stable sort / Merge sort over default point values "x"
            Arrays.sort(orderedPoints, i + 1, orderedPoints.length, origin.slopeOrder());

            /* @Observe i+1 index seems off. Investigate Further */


            // set up counter variable to count number of points in line segment
            int counter = 0;

            // Set the reference Slope value to NaN (Not a Number) to Start
            double refSlope = Double.NaN;

            // set the reference point to be the same as origin for future compares
            Point refPt = origin;

            for (int j = i + 1; j < orderedPoints.length; j++) {
                // nested for current i to end of ordered points list

                // set new variable pt tp the current array value
                Point pt = orderedPoints[j];

                // get slope from current origin to all following points
                double slope = origin.slopeTo(pt);

                // if the current slope is equal to the previous slope
                if (Double.compare(slope, refSlope) == 0) {
                    counter += 1; // increase number of points in line segment
                } else {
                    // check if the number of points in line segment are already at 3 or greater
                    if (counter >= 3) {
                        checkAndAdd(origin, refPt, refSlope);
                    }

                    refSlope = slope;
                    counter = 1;
                }
                refPt = pt;
            }

            if (counter >= 3) {
                checkAndAdd(origin, refPt, refSlope);
            }
        }
    }

    private class DataManager {

        private class DataNode {
            private final double key;
            private ArrayList<Point> value;

            public DataNode(double key) {
                this.key = key;
            }

            public void setValue(ArrayList<Point> value) {
                Collections.sort(value);
                this.value = value;
            }

            public double getKey() {
                return key;
            }

            public ArrayList<Point> getValue() {
                return value;
            }
        }

        // method to compare two points
        private final Comparator<DataNode> comp = (v1, v2) -> (int) Math.signum(v1.getKey() - v2.getKey());

        private final ArrayList<DataNode> dataCollection = new ArrayList<>();

        public void put(double key, ArrayList<Point> value) {

            DataNode input = new DataNode(key);
            input.setValue(value);

            dataCollection.add(input);
        }

        public ArrayList<Point> get(double query) {
            int index = Collections.binarySearch(dataCollection, new DataNode(query), comp);
            return dataCollection.get(index).getValue();
        }
    }

    private void checkAndAdd(Point start, Point end, double slope) {

        // get all points in Hashmap with a certain slope
        ArrayList<Point> ptExist = matchingSlopePoints.get(slope);

        // If there are no points with the given Slope value:
        if (ptExist == null) {
            ptExist = new ArrayList<>(); // set the point list of points with certain slopes to empty array
            ptExist.add(end); // add the End point to the array

            // add ptExist to Hashmap with the slope Key
            matchingSlopePoints.put(slope, ptExist);

            // add a new line segment that goes from start to end
            lineSeg.add(new LineSegment(start, end));
        } else {
            // if the list of points with slope "slope" do exist
            // and if the the list of points with slope "slope" dont contain the end point
            if (!ptExist.contains(end)) {
                // add if point is non existent
                ptExist.add(end); // add the end points with slope "slope"

                // create a new line segment going from start to end
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
