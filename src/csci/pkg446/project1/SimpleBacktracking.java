/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci.pkg446.project1;

import java.util.List;

/**
 *
 * @author 765Alpha
 */
public class SimpleBacktracking extends Backtracking {

    private List<Point> points;
    private List<Edge> edges;
    private Graph g;
    private int index;
    
    public SimpleBacktracking(){
        
    }

    @Override
    public Graph SolveGraph(Graph graphToSolve) {
        g = graphToSolve;
        points = g.getPoints();
        edges = g.getEdges();
        index = 0;
        Graph answer = dfs(3);
        if (answer == null) {
            return dfs(4);
        }
        return answer;
    }

    private Graph dfs(int numC) {
        Point curPoint = g.getCurrentPoint();
        //if this iteration would cause an arrayoutofboundsexception, g.index++ == points.size
        if (index + 1 == points.size()) {
            //must be seperate so we don't go into the else statement
            if(isSolved()){
                return g;
            }
        } else {
            //iterate through the colors
            for (int i = 0; i < numC; i++) {
                switch (i) {
                    case 0:
                        curPoint.setColor("RED");
                        break;
                    case 1:
                        curPoint.setColor("GREEN");
                        break;
                    case 2:
                        curPoint.setColor("BLUE");
                        break;
                    case 3:
                        curPoint.setColor("YELLOW");
                        break;
                    default:
                        curPoint.setColor("COLORLESS");
                }
                if (!confEdges(curPoint)) {
                    //using this method as an iterator
                    g.getNextPoint();
                    index++;
                    Graph answer = dfs(numC);
                    if (answer != null) {
                        return answer;
                    }
                }
            }
        }
        //</iterate> if we failed to apply a legal color
        curPoint.setColor("COLORLESS");
        //using this method as an iterator
        g.getPreviousPoint();
        index--;
        return null;
    }

    private boolean isSolved() {
        for (Point curPoint : points) {
            if (curPoint.c().equals("COLORLESS")) {
                return false;
            }
        }
        for (Edge curEdge : edges) {
            if (curEdge.end().c().equals(curEdge.start().c())) {
                return false;
            }
        }
        return true;
    }

    //for this method to be called, the curPoint must be colored
    private boolean confEdges(Point curPoint) {
        List<Edge> connections = g.getEdgesFromPoint(curPoint);
        for (Edge e : connections) {
            //for this to be true, both the end and the start will have to be the same color
            //where one would be the curPoint and the other would be the next point
            if (e.end().c().equals(e.start().c())) {
                return false;
            }
        }
        return true;
    }
}
