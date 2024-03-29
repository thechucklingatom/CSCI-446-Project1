package csci.pkg446.project1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Mathew Gostnell
 */
public class Generator {

    private Random rng = new Random();
    private int numPnts;    // used for creating n points in Graph generation
    private ArrayList<Edge> edges;  // current list of edges of graph
    private ArrayList<Point> points;    // current list of points on graph
    private Graph generatedGraph;

    public Generator(int numPnts) {
        this.numPnts = numPnts;
        points = new ArrayList<>();
        edges = new ArrayList<>();
        generatePoints();
        generateEdges();
        createGraph();
    }
    
    public Graph generateNewGraph(int numPnts) {
        this.numPnts = numPnts;
        edges.clear();
        points.clear();
        generatePoints();
        generateEdges();
        return createGraph();
    }
    
    private void generateEdges() {
        // while points are available
        // pick our current points.get(i) and look at all other possible edges
        // sort the edges by length
        // if we have a valid edge, insert edge into edges ArrayList
        ArrayList<Edge> posEdges = new ArrayList<>();
        for (Point p1 : points) {            
            posEdges.clear();
            for (Point p2 : points) {
                if (p1 != p2) {
                    posEdges.add(new Edge(p1, p2));
                }
            }
            // sort edges array by length
            Collections.sort(posEdges, Edge.getCompByLength());
            
            boolean allClear;
            for (Edge e1: posEdges) {
                allClear = true;
                for (Edge e2: edges) {
                    if (isIntercept(e1, e2)) {
                        allClear = false;
                    }
                }
                if (allClear) {
                    edges.add(e1);
                }
            }
        }
                
    }    

    private void generatePoints() {
        while (points.size() < numPnts) {
            double xCor = rng.nextDouble();
            double yCor = rng.nextDouble();
            if (isUnique(xCor, yCor) == true) {
                points.add(new Point(xCor, yCor));
            }
        }
    }

    /**
     * Checks to see if two edges intercept each other for Graph generation
     *
     * @param e1 Edge 1 used in intercept calculation
     * @param e2 Edge 2 used in intercept calculation
     * @return True/False if e1 & e2 intercept each other
     */
    public boolean isIntercept(Edge e1, Edge e2) {
        // calculate the Point p where e1 and e2 would intercept if they were lines
        double xCor = (e2.intercept() - e1.intercept()) / (e1.slope() - e2.slope());
        double yCor = (xCor * e1.slope()) + e1.intercept();
        Point p = new Point(xCor, yCor);
        //p.print();

        // check to see if Point P is on line-segment e1 and e2
        return onLine(p, e1) && onLine(p, e2);
    }

    /**
     * Check to see if a point is on the same coordinates (x, y)
     *
     * @param p1 First point for comparison
     * @param p2 Second point for comparison
     * @return boolean true if the p1 matches the (x, y) coordinates of p2
     */
    private boolean match(Point p1, Point p2) {
        int check1 = Double.compare(p1.x(), p2.x());
        int check2 = Double.compare(p1.y(), p2.y());
        return check1 == 0 && check2 == 0;
    }

    private boolean isUnique(double x, double y) {
        Point test = new Point(x, y);
        boolean check = true;
        for (int i = 0; i < points.size(); i++) {
            if (match(points.get(i), test) == true) {
                check = false;
            }
        }
        return check;
    }

    private boolean onLine(Point p, Edge e) {
        // check if p is within boundary box of e.start & e.end
        double minX = Math.min(e.start().x(), e.end().x());
        double maxX = Math.max(e.start().x(), e.end().x());
        double minY = Math.min(e.start().y(), e.end().y());
        double maxY = Math.max(e.start().y(), e.end().y());

        //System.out.println("Boundary: (" + minX + ", " + maxX + "), (" + minY + ", " + maxY + ")");

        return minX <= p.x() && p.x() <= maxX && minY <= p.y() && p.y() <= maxY;

    }
    
    private Graph createGraph(){
        generatedGraph = new Graph((List)edges, (List)points);
        return getGraph();
    }
    
    public Graph getGraph(){
        return generatedGraph;
    }
}
