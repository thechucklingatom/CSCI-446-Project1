package csci.pkg446.project1;

import java.util.ArrayList;

/**
 *
 * @author thechucklingatom
 */
public class Chromosome {
    
    private int conflicts;
    private int nonConflictNodes;
    private ArrayList<Point> points;
    
    Chromosome(ArrayList<Point> points){
        this.points = points;
        this.conflicts = -1;
    }
    
    /**
     * @return the conflicts
     */
    public int getConflicts() {
        return conflicts;
    }

    /**
     * @param conflicts the conflicts to set
     */
    public void setConflicts(int conflicts) {
        this.conflicts = conflicts;
        setNonConflictNodes(points.size() - conflicts);
    }

    /**
     * @return the nonConflictNodes
     */
    public int getNonConflictNodes() {
        return nonConflictNodes;
    }

    /**
     * @param nonConflictNodes the nonConflictNodes to set
     */
    public void setNonConflictNodes(int nonConflictNodes) {
        this.nonConflictNodes = nonConflictNodes;
    }

    /**
     * @return the points
     */
    public ArrayList<Point> getChromosome() {
        return points;
    }

    /**
     * @param points the points to set
     */
    public void setChromosome(ArrayList<Point> points) {
        this.points = points;
    }
    
     
    
}
