
package csci.pkg446.project1;

import java.util.ArrayList;

/**
 *
 * @author Mathew Gostnell
 */
public class Driver {
    public static void main(String[] args) {        
        Generator g = new Generator(10);
        
        System.out.println("The graph is:\n" + g.getGraph().toString());
        
        ArrayList<Algorithm> algorithmList = new ArrayList();
        
        LocalSearch ls = new LocalSearch();
        SimpleBacktracking sb = new SimpleBacktracking();
        ForwardBacktracking fb = new ForwardBacktracking();
        MinConflicts mc = new MinConflicts();
        
        algorithmList.add(ls);
        algorithmList.add(sb);
        algorithmList.add(fb);
        algorithmList.add(mc);
        
        Graph toSolve = g.generateNewGraph(10);
        //System.out.println(toSolve.toString());
        for(Algorithm a : algorithmList){
            System.out.println(a.toString());
            Graph solution = a.SolveGraph(toSolve);
            if(solution != null){
                System.out.println("The solution graph is:\n" + solution.toString());
            } else {
                System.out.println("Coloring not Found.");
            }
        }  
    }
}
