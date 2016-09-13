
package csci.pkg446.project1;

/**
 *
 * @author Mathew Gostnell
 */
public class Driver {
    public static void main(String[] args) {
        //TODO here
        Point p1 = new Point(0.0, 0.0);
        Point p2 = new Point(1.0, 1.0);
        Edge e1 = new Edge(p1, p2);
        e1.print();
        System.out.println("y = " + e1.slope() + "x + " + e1.intercept());
        
        Point p3 = new Point(1.0, 0.0);
        Point p4 = new Point(0.0, 1.0);
        Edge e2 =  new Edge(p3, p4);
        e2.print();
        System.out.println("y = " + e2.slope() + "x + " + e2.intercept());
        
        Generator g = new Generator(50);
        System.out.println(g.isIntercept(e1, e2));
        
    }
}
