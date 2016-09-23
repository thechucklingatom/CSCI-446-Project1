package csci.pkg446.project1;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thechucklingatom
 */
public class LocalSearch extends Algorithm{

    int numberOfColors;
    int currentColor;
    ArrayList<Point.Color> colorList;
    
    
    public LocalSearch(){
        numberOfColors = 3;
        currentColor = 0;
        colorList = new ArrayList();
        colorList.add(Point.Color.RED);
        colorList.add(Point.Color.BLUE);
        colorList.add(Point.Color.GREEN);
        colorList.add(Point.Color.YELLOW);
    }
    
    /**
     * pseudocode for hill climbing. will be using the amount of edges as the value
     * function HILL-CLIMBING(problem) returns a state that is a local maximum
     * current = MAKE-NODE(problem.lNITIAL-STATE)
     * loop do
     *  neighbor = a highest-valued successor of current
     *  if neighbor.VALUE less than or equal current.VALUE then return current.STATE
     * current = neighbor
     * @param graphToSolve graph to color
     * @return the colored graph or null if it cant be colored.
     */
    @Override
    public Graph SolveGraph(Graph graphToSolve) {
        
        List<Edge> edgeList = graphToSolve.getEdgesFromPoint(
                graphToSolve.getStartPoint());
        
        while(edgeList.size() < 3){
            edgeList = graphToSolve.getEdgesFromPoint(
                    graphToSolve.getNextPoint());
        }
        
        List<Point> points = graphToSolve.getPoints();
        
        Point currentPoint = graphToSolve.getCurrentPoint();
            
        currentPoint.setColor(colorList
                .get(currentColor % numberOfColors)
                .toString()
                .toLowerCase());

        currentColor++;

        for(Edge e : edgeList){
            if(e.start().equals(currentPoint) && e.start().c().equals("Colorless")){
                e.end().setColor(colorList
                        .get(currentColor % numberOfColors)
                        .toString()
                        .toLowerCase());
            }else if(e.end().equals(currentPoint) && e.start().c().equals("Colorless")){
                e.start().setColor(colorList
                        .get(currentColor % numberOfColors)
                        .toString()
                        .toLowerCase());
            }
        }
        
        while(!graphColored(graphToSolve)){
            
        }
        
        return graphToSolve;
    }
    
    public boolean graphColored(Graph toCheck){
        List<Point> pointList = toCheck.getPoints();
        
        return pointList.stream().noneMatch((p) -> (p.c().equalsIgnoreCase("colorless")));
    }
    
}
