package csci.pkg446.project1;

import java.util.Comparator;

/**
 *
 * @author Mathew Gostnell
 */
public class Edge {

    private final Point start;  // starting Point for line-segment aka Edge
    private final Point end;    // ending Point for line-segment aka Edge

    /**
     * Constructor used to create an edge between two points.
     *
     * @param start Point used for the start of the Edge
     * @param end Point used for the end of the Edge
     */
    public Edge(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    // returns the slope of the given Edge
    public double slope() {
        return (end.y() - start.y()) / (end.x() - start.x());
    }

    // returns the slope-intercept of the given Edge
    public double intercept() {
        return start.y() - (start.x() * slope());
    }

    // returns the length of the given edge
    public double length() {
        double rise = end.y() - start.y();
        double run = end.x() - start.x();
        rise *= rise;
        run *= run;
        return Math.pow(rise + run, 0.5);
    }

    // get private variable start
    public Point start() {
        return this.start;
    }

    // get private variable end
    public Point end() {
        return this.end;
    }

    // print out coordinate information regarding this Edge
    public void print() {
        System.out.printf("(%.4f, %.4f), (%.4f, %.4f)\t[%.4f]\n", start.x(), start.y(), end.x(), end.y(), length());
    }
    
    @Override
    public String toString(){
        return String.format("(%.4f, %.4f), (%.4f, %.4f)\t[%.4f]", start.x(), start.y(), end.x(), end.y(), length());
    }

    public static Comparator<Edge> getCompByLength() {
        Comparator comp = (Comparator<Edge>) (Edge e1, Edge e2) -> Double.compare(e1.length(), e2.length());
        return comp;
    }
}
