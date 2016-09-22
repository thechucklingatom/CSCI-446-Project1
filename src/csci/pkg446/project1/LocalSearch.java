package csci.pkg446.project1;

/**
 *
 * @author thechucklingatom
 */
public class LocalSearch extends Algorithm{

    
    /**
     * pseudocode for hill climbing. will be using the amount of edges as the value
     * function HILL-CLIMBING(problem) returns a state that is a local maximum
     * current = MAKE-NODE(problem.lNITIAL-STATE)
     * loop do
     *  neighbor = a highest-valued successor of current
     *  if neighbor.VALUE less than or equal current.VALUE then return current.STATE
     * current = neighbor
     * @param graphToSolve
     * @return 
     */
    @Override
    public Graph SolveGraph(Graph graphToSolve) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
