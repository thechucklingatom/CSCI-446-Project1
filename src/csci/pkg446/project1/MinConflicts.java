/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci.pkg446.project1;

import java.util.List;
import java.util.Random;

/**
 *
 * @author thew
 */
public class MinConflicts extends Algorithm {

    private Random rng;
    private int curColors;
    private List<Point> points;
    private List<Edge> edges;

    public MinConflicts() {
        rng = new Random();
        points = null;
        edges = null;
        curColors = 3;
        // initialize variables
    }

    @Override
    public Graph SolveGraph(Graph graphToSolve) {
        // randomly assign nodes Color and then correct them each to the
        // state that contains the least constraints
        // Constraint := An edge with a Point of the same Color (+1)
        // will check graph with 50 starting comparisons (minConstraint 50 times)
        // this has been shown to solve most complex problems (like n-queens)

        Graph g = graphToSolve;
        points = randomColor(g);        
        edges = g.getEdges();
        for (Point p : points) {
            updateEdges(p);
        } // apply random color to edges

        int maxCount = points.size() + 50;
        int curCount = 0;
        int index = curCount % points.size();
        Point curPoint;
        while (curCount < maxCount) {   // attempt 3 color solution
            // grab our current point from our list
            curPoint = points.get(index);
            // get all edges with our current point
            List<Edge> curEdges = g.getEdgesFromPoint(curPoint);
            // find a color with the minimum number of conflicts at this Point
            String minConflict = getConflictColor(curEdges);
            // update our current Point color
            curPoint.setColor(minConflict);
            // update our list of points color
            points.set(index, curPoint);
            // update all edges with the points colors
            updateEdges(curPoint);
            // return valid graph if it exists
            if (validGraph()) {
                return new Graph(edges, points);
            }
            // not valid graph, update index and continue finding miniumum conflicts
            index = ++index % points.size();
            curCount++;
        }
        curColors = 4; // now try 4 color solution
        points = randomColor(g);
        for (Point p : points) {
            updateEdges(p);
        }
        curCount = 0;
        while (curCount < maxCount) {   // attempt 4 color solution
            // grab our current point from our list
            curPoint = points.get(index);
            // get all edges with our current point
            List<Edge> curEdges = g.getEdgesFromPoint(curPoint);
            // find a color with the minimum number of conflicts at this Point
            String minConflict = getConflictColor(curEdges);
            // update our current Point color
            curPoint.setColor(minConflict);
            // update our list of points color
            points.set(index, curPoint);
            // update all edges with the points colors
            updateEdges(curPoint);
            // return valid graph if it exists
            if (validGraph()) {
                return new Graph(edges, points);
            }
            // not valid graph, update index and continue finding miniumum conflicts
            index = ++index % points.size();
            curCount++;
        }

        return null;
    }
    
    private boolean validGraph() {
        return edges.stream().noneMatch((e) -> (e.start().c().equals(e.end().c())));
    }
    
    private void updateEdges(Point p) {
        edges.stream().forEach((e) -> {
            if (e.start().equals(p)) {
                e.start().setColor(p.c());
            } else if (e.end().equals(p)) {
                e.end().setColor(p.c());
            }
        });
    }

    private String getConflictColor(List<Edge> edges) {
        int index = 0;
        int[] clrs;
        if (curColors < 4) {
            clrs = new int[3];
            for (Edge curEdge : edges) {
                String cnsColor = isConflict(curEdge);
                switch (cnsColor) {
                    case "Red":
                        clrs[0]++;
                        break;
                    case "Green":
                        clrs[1]++;
                        break;
                    case "Blue":
                        clrs[2]++;
                        break;
                    default:
                        break;
                }
            }
            int conflicts = edges.size() + 1; // assumes all points same color

            for (int i = 0; i < 3; i++) {
                if (clrs[i] < conflicts) {
                    conflicts = clrs[i];
                    index = i;
                }
            }
        } else {
            clrs = new int[4];
            for (Edge curEdge : edges) {
                // count the color conflicts for these edges
                String cnsColor = isConflict(curEdge);
                switch (cnsColor) {
                    case "Red":
                        clrs[0]++;
                        break;
                    case "Green":
                        clrs[1]++;
                        break;
                    case "Blue":
                        clrs[2]++;
                        break;
                    case "Yellow":
                        clrs[3]++;
                        break;
                    default:
                        break;
                }
            }
            int conflicts = edges.size() + 1; // assumes all points same color

            for (int i = 0; i < 4; i++) {
                if (clrs[i] < conflicts) {
                    conflicts = clrs[i];
                    index = i;
                }
            }
        }
        switch (index) {
            case 0:
                return "Red";
            case 1:
                return "Blue";
            case 2:
                return "Green";
            case 3:
                return "Yellow";
            default:
                return "Colorless";
        }
    }

    private List<Point> randomColor(Graph g) {
        List<Point> rPoints = g.getPoints();
        for (Point p : rPoints) {
            int rnd = rng.nextInt();
            if (curColors < 4) {
                switch (rnd % 3) {
                    case 0:
                        p.setColor("Red");
                        break;
                    case 1:
                        p.setColor("Blue");
                        break;
                    default:
                        p.setColor("Green");
                        break;
                } // <4 colors
            } else {
                switch (rnd % 4) {
                    case 0:
                        p.setColor("Red");
                        break;
                    case 1:
                        p.setColor("Blue");
                        break;
                    case 2:
                        p.setColor("Green");
                        break;
                    default:
                        p.setColor("Yellow");
                        break;
                }
            } // >= 4 colors
        }
        return rPoints;
    }

    private List<Edge> findEdges(Point p, List<Edge> edges) {
        List<Edge> match = edges;
        match.clear();
        for (Edge e : edges) {
            if (e.start().equals(p) || e.end().equals(p)) {
                match.add(e);
            }
        }
        return match;
    }

    private String isConflict(Edge e) {
        if (e.start().c().equals(e.end().c())) {
            return e.start().c();
        } else {
            return "Colorless";
        }
    }
}
