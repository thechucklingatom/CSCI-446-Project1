
package csci.pkg446.project1;

/**
 *
 * @author Mathew Gostnell
 */

public class Edge {
    private final Point start;
    private final Point end;
    
    public Edge(Point start, Point end) {
        this.start = start;
        this.end = end;
    }
    
    public double slope() {
        return (end.y() - start.y()) / (end.x() - start.x());
    }
    
    public double intercept() {
        return start.y() - (start.x() * slope());
    }
    
    public double length() {
        double rise = end.y() - start.y();
        double run = end.x() - start.x();
        rise *= rise;
        run *= run;
        return Math.pow(rise + run, 0.5);
    }
    
    public Point start() {
        return this.start;
    }
    
    public Point end() {
        return this.end;
    }
    
    public void print() {
        System.out.print("S:(" + start.x() + ", " + start.y() + ") <-> E:(");
        System.out.print(end.x() + ", " + end.y() + ")\n");
                
    }
}
