/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci.pkg446.project1;

import java.util.ArrayList;
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
        edges = g.getEdges();
        points = randomColor(g);
        curColors = 3;

        // max number of mindecisions we will make
        int maxCount = 150000; //after n = 30 will maxCount too small to solve Graph
        int curCount = 0;
        int index = 0;
        Point curPoint;
        do {
            curPoint = points.get(index);
            System.out.println("curPoint = " + curPoint);
            List<Edge> curEdges = findEdges(curPoint);
            System.out.println(curEdges.size() + "Edges to/from curPoint");
            String minConflict = getConflictColor(curEdges);
            System.out.println("\tMinimum Conflict Color Choice: " + minConflict);
            curPoint.setColor(minConflict);
            updateEdges(curPoint);
            System.out.println("\tUpdate edge and point colors");
            if (validGraph()) {
                System.out.println("\n\tTook " + curCount + " minConflict for n=" + points.size());
                return new Graph(edges, points);
            }
            // not valid graph, update index and continue finding miniumum conflicts
            curCount++;
            index = ++index%points.size();
            System.out.println("curCount:" + curCount + "\tRandom Index:" + index);
        } while (curCount < maxCount);

        curColors = 4; // now try 4 color solution
        points = randomColor(g);
        curCount = 0;
        do {   // attempt 4 color solution
            curPoint = points.get(index);
            System.out.println("curPoint = " + curPoint);
            List<Edge> curEdges = findEdges(curPoint);
            System.out.println(curEdges.size() + " Edges to/from curPoint");
            String minConflict = getConflictColor(curEdges);
            System.out.println("\tMinimum Conflict Color Choice: " + minConflict);
            curPoint.setColor(minConflict);
            updateEdges(curPoint);
            System.out.println("\tUpdate edge and point colors");
            if (validGraph()) {
                System.out.println("\n\tTook " + curCount + " minConflict for n=" + points.size());
                return new Graph(edges, points);
            }
            // not valid graph, update index and continue finding miniumum conflicts
            curCount++;
            index = ++index%points.size();
            System.out.println("curCount:" + curCount + "\tRandom Index:" + index);
        } while (curCount < maxCount);
        return null;
    }

    private boolean validGraph() {
        return edges.stream().noneMatch((e) -> (e.start().c().equals(e.end().c())));
    }

    private void updateEdges(Point p) {
        edges.stream().forEach((e) -> {
            if (e != null && e.start().equals(p)) {
                e.start().setColor(p.c());
            } else if (e != null && e.end().equals(p)) {
                e.end().setColor(p.c());
            }
        });
    }

    private List<Edge> findEdges(Point p) {
        List<Edge> pairs = new ArrayList<>();
        this.edges.stream().filter((e) -> (e.start().equals(p) || e.end().equals(p))).forEach((e) -> {
            pairs.add(e);
        });
        return pairs;
    }

    private String getConflictColor(List<Edge> edges) {
        int[] colConflicts;
        if (curColors < 4) {   // 3-color mode 
            colConflicts = new int[3];
            // count the conflicts and there are non-zero conflicts, find the min
            // if there are zero conflicts, grab all of them and randomly select one
            // counting conflicts
            for (Edge e : edges) {
                switch (isConflict(e)) {
                    case "Red":
                        colConflicts[0]++;
                        break;
                    case "Blue":
                        colConflicts[1]++;
                        break;
                    case "Green":
                        colConflicts[2]++;
                        break;
                    default:    // not a conflict!
                        break;
                }
            }

        } else { // 4-color mode
            colConflicts = new int[4];

            for (Edge e : edges) {
                switch (isConflict(e)) {
                    case "Red":
                        colConflicts[0]++;
                        break;
                    case "Blue":
                        colConflicts[1]++;
                        break;
                    case "Green":
                        colConflicts[2]++;
                        break;
                    case "Yellow":
                        colConflicts[3]++;
                        break;
                    default:    // not a conflict!
                        break;
                }
            }
        }

        // check for the min of all non-zeros or the presence of non-zeros
        List<Integer> matchIndex = new ArrayList<>();
        List<Integer> conflictIndex = new ArrayList<>();
        int curMin = Integer.MAX_VALUE;
        int colIndex = -1;

        for (int i = 0; i < colConflicts.length; i++) {
            if (colConflicts[i] == 0) {
                conflictIndex.add(i);
            } else if (colConflicts[i] < curMin) {
                matchIndex.clear();
                curMin = colConflicts[i];
                colIndex = i;
            } else if (colConflicts[i] == curMin) {
                matchIndex.add(i);
                colIndex = -1;
            }
        }
        if (conflictIndex.size() > 0) {
            // return random color from zero-conflicts list
            return getRandomColor(conflictIndex);
        } else if (matchIndex.size() > 0) {
            // return random color from matching min-conflict colors
            return getRandomColor(matchIndex);
        } else if (colIndex != -1 && colIndex != Integer.MAX_VALUE) {
            // return color that matches the minimum colors
            switch (colIndex) {
                case 0:
                    return "Red";
                case 1:
                    return "Blue";
                case 2:
                    return "Green";
                case 3:
                    return "Yellow";
                default:
                    System.out.println(":::RETURNING NO COLOR:::");
                    return " ";
            }
        } else {
            System.out.println(":::RETURNING NO COLOR:::");
            return " ";
        }
    }

    private String getRandomColor(List<Integer> possibleColors) {
        int index = rng.nextInt(possibleColors.size());
        int value = possibleColors.get(index);
        switch (value) {
            case 0:
                return "Red";
            case 1:
                return "Blue";
            case 2:
                return "Green";
            case 3:
                return "Yellow";
            default:
                return getRandomColor(possibleColors);
        }
    }

    private String isConflict(Edge e) {
        if (e.start().c().equals(e.end().c())) {
            return e.start().c();
        } else {
            return ""; //default case is empty string 
        }
    }

    private List<Point> randomColor(Graph g) {
        List<Point> pnts = g.getPoints();
        for (Point p : pnts) {
            int rnd = rng.nextInt(curColors);
            switch (rnd) {
                case 0:
                    p.setColor("Red");
                    break;
                case 1:
                    p.setColor("Blue");
                    break;
                case 2:
                    p.setColor("Green");
                    break;
                case 3:
                    p.setColor("Yellow");
                    break;
            }
            updateEdges(p);
        }
        return pnts;
    }
}
