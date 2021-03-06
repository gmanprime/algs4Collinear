/* *****************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        // Determines Slope from This Point Object To a give point That
        if (x == that.x && y == that.y) {
            // If the X and Y components of this point is equal to the coordinates of point "that"
            return Double.NEGATIVE_INFINITY;
        } else if (x == that.x) {
            // if this point is Directly Above or Below point "that"
            return Double.POSITIVE_INFINITY;
        } else if (y == that.y) {
            // if this point is Directly to the left or right of point "that"
            return +0.0;
        } else {
            // if non of the Above are true, then compute and return the Slope value
            return (double) (that.y - y) / (that.x - x);
        }
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        if (y != that.y) {
            return y - that.y;
        } else {
            return x - that.x;
        }
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */

    private class SlopeOrderComparator implements Comparator<Point> {

        /* Comparator class that provides Methods for:
        *   1) compare two points in a custom order of values
        *
        */
        @Override
        public int compare(Point pt1, Point pt2) {
            /* Compare the slope from this point to the two other points and return 1, 0, or -1
            * depending on whether the slope increases, is equal to or decreases from the first
            * slope value
            *  */

            // get the difference in slope
            double slopeDiff = slopeTo(pt1) - slopeTo(pt2);
            // convert to 1,0, or -1 for greater than, equal to or less than and return value as int
            return (int) Math.signum(slopeDiff);
        }
    }

    public Comparator<Point> slopeOrder() {
        return new SlopeOrderComparator();
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}
