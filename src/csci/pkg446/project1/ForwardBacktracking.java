/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci.pkg446.project1;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author 765Alpha
 */
public class ForwardBacktracking extends Backtracking{

    private List<Point> points;
    private List<Edge> edges;
    private Graph g;
    private int index;
    //these three lists are meant to keep track of our forward checking
    private ArrayList<Integer>[] domainList;
    private Stack<Integer>[] lastColor;
    private boolean[] isDone;

    @Override
    public Graph SolveGraph(Graph graphToSolve) {
        g = graphToSolve;
        points = g.getPoints();
        edges = g.getEdges();
        index = 0;
        initiateForward(3);
        if (dfs(3) == null) {
            initiateForward(4);
            return dfs(4);
        }
        return null;
    }


    private Graph dfs(int numC) {
        Point curPoint = g.getCurrentPoint();
        Stack<Integer> editedNodes = new Stack();
        //if this iteration would cause an arrayoutofboundsexception, g.index++ == points.size
        if (index + 1 == points.size()) {
            //must be seperate so we don't go into the else statement
            if(isSolved()){
                return g;
            }
        } else {
            //iterate through the colors
            for (int i = 0; i < numC; i++) {
                if(checkDomain(index)){
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
                    
                    if (!confEdges(curPoint) && forwardCheck(editedNodes, i)) {
                        //using this method as an iterator
                        g.getNextPoint();
                        index++;
                        Graph answer = dfs(numC);
                        if (answer != null) {
                            return answer;
                        } 
                    }
                    reverseStep(editedNodes);
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
    
    //--------------------------------------------------------------------------
    //start the ForwardBacktracking specific methods
    private void initiateForward(int numColors){
        domainList = new ArrayList[points.size()];
        lastColor = new Stack[points.size()];
        isDone = new boolean[points.size()];
        for(int i = 0; i < numColors; i++){
            lastColor[i] = new Stack();
            domainList[i] = new ArrayList<>();
            isDone[i] = false;
            for(int j = 0; j < numColors; j++){
                //RED = 0, BLUE = 1, GREEN = 2, YELLOW = 3
                domainList[i].add(j);
            }
        }
    }
    
    private boolean forwardCheck(Stack<Integer> editedNodes, int colorIndex){
        //assume we are doing a check when we HAVE ASSIGNED a point a new color
        Point curPoint = g.getCurrentPoint();
        List<Edge> tempEdges = g.getEdgesFromPoint(curPoint);
        isDone[index] = true;

        for(Edge curEdge : tempEdges){
            //makes sure we grab the correct point on the edge to edit
            Point otherCurPoint;
            if(curEdge.end().equals(curPoint)){
                otherCurPoint = curEdge.start();
            } else {
                otherCurPoint = curEdge.end();
            }
            //grab the index of that otherCurPoint 'cause that's what we use
            int otherPointPos = points.indexOf(otherCurPoint);
            //catch if this point has already been assigned a color
            if(isDone[otherPointPos]){
            } else if(domainList[otherPointPos].contains(colorIndex)){
                if(domainList[otherPointPos].size() - 1 == 0){
                    return false;
                }
                //if we reach here, we are conflict free
                domainList[otherPointPos].remove(colorIndex);
                lastColor[otherPointPos].push(colorIndex);
                editedNodes.push(otherPointPos);
                //check if there's one option left
                if(domainList[otherPointPos].size() == 1){
                    //if so, assign that color and mark it as assigned
                    switch (colorIndex) {
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
                    isDone[index] = true;
                }
            }
        }
        return true;
    }
   
    private void reverseStep(Stack<Integer> stack){
        while(!stack.empty()){
            int i = stack.pop();
            if(isDone[i]){
                isDone[i] = false;
            } else {
                int color = lastColor[i].pop();
                domainList[i].add(color);
            }
        }
    }
    
    //this method is used to check the valid domains before assigning a color to a point
    private boolean checkDomain(int colorNum){
        //we use the this.index to assume our current Point in points.
        //RED = 0, BLUE = 1, GREEN = 2, YELLOW = 3
        if(!domainList[index].contains(colorNum)){
           return false; 
        }
        //true means good to go
        return true;
    }
}
