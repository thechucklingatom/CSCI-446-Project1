
package csci.pkg446.project1;

/**
 *
 * @author Mathew Gostnell
 */
public class Driver {
    public static void main(String[] args) {        
        Generator g = new Generator(100);
        
        System.out.println("The graph is:\n" + g.getGraph().toString());
        
        LocalSearch ls = new LocalSearch();
       
        ls.SolveGraph(g.getGraph());
//        
//        int i = 0;
//        while(i < 1000){
//            ls.SolveGraph(g.getGraph());
//            g.generateNewGraph(i + 7);
//        }
        
    }
}
