
package csci.pkg446.project1;

import java.util.ArrayList;

/**
 *
 * @author Mathew Gostnell
 */
public class Driver {
    public static void main(String[] args) {        
        Generator g = new Generator(20);
        
        System.out.println("The graph is:\n" + g.getGraph().toString());
        
        ArrayList<Algorithm> algorithmList = new ArrayList();
        
        LocalSearch ls = new LocalSearch();
        
        algorithmList.add(ls);
        
        for(int i = 10; i < 100; i+=10){
            
            Graph toSolve = g.generateNewGraph(i);
            System.out.println(toSolve.toString());
            for(Algorithm a : algorithmList){
                Graph solution = a.SolveGraph(toSolve);
                System.out.println("The solution graph is:\n" + solution.toString());
            }
            
        }
        
    }
}
