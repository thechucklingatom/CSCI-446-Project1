
package csci.pkg446.project1;

/**
 *
 * @author Mathew Gostnell
 */
public class Driver {
    public static void main(String[] args) {        
        Generator g = new Generator(6);
        
        LocalSearch ls = new LocalSearch();
        
        int i = 0;
        while(i < 1000){
            ls.SolveGraph(g.getGraph());
            g.generateNewGraph(i + 7);
        }
        
    }
}
