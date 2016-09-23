package csci.pkg446.project1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thechucklingatom
 */
public final class Graph implements Serializable{
    private List<Edge> edges;
    private List<Point> points;
    private int index;
    
    public Graph(List<Edge> edges, List<Point> points){
        updateGraph(edges, points);
    }
    
    public void updateGraph(List<Edge> edges, List<Point> points){
        this.edges = edges;
        this.points = points;
        index = 0;
    }
    
    public List<Edge> getEdges(){
        return edges;
    }
    
    public List<Point> getPoints(){
        return points;
    }
    
    public Point getStartPoint(){
        return points.get(0);
    }
    
    public Point getCurrentPoint(){
        return points.get(index);
    }
    
    public Point getNextPoint(){
        if(index < points.size() - 1){
            index++;
        }
        return points.get(index);
    }
    
    public Point getPreviousPoint(){
        if(index != 0){
            index--;
            return points.get(index);
        }
        return null;
    }
    
    public List<Edge> getEdgesFromPoint(Point toGetEdgesFor){
        List<Edge> toReturn = new ArrayList();
        edges.stream().filter((edge) -> (edge.start().equals(toGetEdgesFor)
                || edge.end().equals(toGetEdgesFor))).forEach((edge) -> {
                    toReturn.add(edge);
        });
        
        return toReturn;
    }
    
    public void setPoints(ArrayList<Point> points){
        this.points = points;
    }
}
