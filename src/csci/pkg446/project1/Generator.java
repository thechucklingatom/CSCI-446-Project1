
package csci.pkg446.project1;

import java.util.ArrayList;

/**
 *
 * @author Mathew
 */
public class Generator {
    private int numPnts;
    private ArrayList<Edge> edges;
    private ArrayList<Point> points;
    
    public Generator(int numPnts) {
        this.numPnts = numPnts;
    }
    
    public boolean isIntercept(Edge e1, Edge e2) {
        double xCor = (e2.intercept() - e1.intercept()) / (e1.slope() - e2.slope());
        double yCor = (xCor * e1.slope()) + e1.intercept();
        Point p = new Point(xCor, yCor);
        p.print();
        
        return onLine(p, e1);        
    }
    
    private boolean onLine(Point p, Edge e) {
        // check if p is within boundary box of e.start & e.end
        double minX = Math.min(e.start().x(), e.end().x());
        double maxX = Math.max(e.start().x(), e.end().x());
        double minY = Math.min(e.start().y(), e.end().y());
        double maxY = Math.max(e.start().y(), e.end().y());
        
        System.out.println("Boundary: (" + minX + ", " + maxX + "), (" + minY + ", " + maxY + ")");
        
        return minX <= p.x() && p.x() <= maxX && minY <= p.y() && p.y() <= maxY;
        
    }
}
