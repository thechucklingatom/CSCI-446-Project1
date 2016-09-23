package csci.pkg446.project1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author thechucklingatom
 */
public class LocalSearch extends Algorithm {

    boolean solutionFound = false;
    int numberOfColors;
    int currentColor;
    int rounds;
    ArrayList<Point.Color> colorList;
    ArrayList<Chromosome> population;
    Graph graph;
    Random rand;

    public LocalSearch() {
        rand = new Random();
        numberOfColors = 3;
        currentColor = 0;
        rounds = 0;
        colorList = new ArrayList();
        colorList.add(Point.Color.RED);
        colorList.add(Point.Color.BLUE);
        colorList.add(Point.Color.GREEN);
        colorList.add(Point.Color.YELLOW);
        population = new ArrayList();
    }

    @Override
    public Graph SolveGraph(Graph graphToSolve) {

        graph = graphToSolve;

        generatePopulation(10);

        while (!graphColored() && rounds < 1000) {
            runTournament();
            breed();
            rounds++;
        }
        
        if(graphColored()){
            System.out.println("It took " + rounds + " rounds to find a match using"
                    + " a genetic algorithm.");
        }else{
            System.out.println("genetic algorithm took the full " + rounds
                    + " rounds without finding a solution.");
        }
        
        return graph;
    }

    private boolean graphColored() {
        for (Chromosome candidate : population) {
            if (candidate.getConflicts() == 0) {
                graph.setPoints(candidate.getChromosome());
                solutionFound = true;
                return true;
            }
        }
        return false;
    }

    public void generatePopulation(int populationSize) {
        for (int i = 0; i < populationSize; i++) {
            Chromosome chromosome = createRandomChromosome();
            fitness(chromosome);
            population.add(chromosome);
        }
    }

    private Chromosome createRandomChromosome() {
        ArrayList<Point> chromosomeList = (ArrayList<Point>) graph.getPoints();
        for (int i = 0; i < chromosomeList.size(); i++) {
            chromosomeList.get(i).setColor(colorList.get(rand.nextInt(numberOfColors)).toString());
        }

        return new Chromosome(chromosomeList);
    }

    private void fitness(Chromosome chromosome) {
        int conflicts = 0;

        ArrayList<Point> graphPoints = (ArrayList<Point>) graph.getPoints();
        for (int i = 0; i < graph.getPoints().size(); i++) {
            if (!noConflicts(graphPoints.get(i), chromosome.getChromosome().get(i).c(), chromosome)) {
                conflicts++;
            }
        }

        chromosome.setConflicts(conflicts);
    }

    private boolean noConflicts(Point point, String color, Chromosome chromosome) {
        ArrayList<Edge> edges = (ArrayList<Edge>) graph.getEdgesFromPoint(point);

        for (Edge e : edges) {

            if (e.start().equals(point)) {
                if (chromosome.getChromosome().get(graph.getPoints().indexOf(e.end())).c().equalsIgnoreCase(color)) {
                    return false;
                }
            } else if (chromosome.getChromosome().get(graph.getPoints().indexOf(e.start())).c().equalsIgnoreCase(color)) {
                return false;
            }
        }

        return true;
    }

    private void runTournament() {

        ArrayList<Chromosome> newPopulation = new ArrayList<>();

        while (population.size() > 1) {

            int firstContender = rand.nextInt(population.size());
            Chromosome gladiator = population.get(firstContender);
            population.remove(gladiator);
            int secondContender = rand.nextInt(population.size());
            Chromosome viking = population.get(secondContender);
            population.remove(secondContender);

            if (gladiator.getConflicts() > viking.getConflicts()) {
                newPopulation.add(gladiator);
            } else {
                newPopulation.add(viking);
            }
        }
        
        if (population.size() == 1){
            newPopulation.addAll(population);
        }

        population = newPopulation;
    }

    private void breed() {
        ArrayList<Chromosome> newPopulation = new ArrayList<>();

        while (population.size() > 1) {

            int firstParent = rand.nextInt(population.size());
            Chromosome parent1 = population.get(firstParent);
            population.remove(firstParent);
            int secondParent = rand.nextInt(population.size());
            Chromosome parent2 = population.get(secondParent);
            population.remove(secondParent);

            List<Chromosome> family = crossover(parent1, parent2);
            newPopulation.addAll(family);
        }
        
        if(population.size() == 1){
            newPopulation.addAll(population);
        }

        population = newPopulation;
    }

    private List<Chromosome> crossover(Chromosome parent1, Chromosome parent2) {
        int crossoverPoint = rand.nextInt(parent1.getChromosome().size());
        ArrayList<Chromosome> family = new ArrayList<>();
        family.add(parent1);
        family.add(parent2);

        ArrayList<Point> parent1List = parent1.getChromosome();
        ArrayList<Point> parent2List = parent2.getChromosome();
        ArrayList<Point> child1List = new ArrayList();
        ArrayList<Point> child2List = new ArrayList();
        for (int i = 0; i < parent1.getChromosome().size(); i++) {
            if (i < crossoverPoint) {
                child1List.add(parent1List.get(i));
                child2List.add(parent2List.get(i));
            } else {
                child1List.add(parent2List.get(i));
                child2List.add(parent1List.get(i));
            }
        }

        Chromosome childChromosome1 = new Chromosome(child1List);
        Chromosome childChromosome2 = new Chromosome(child2List);
        fitness(childChromosome1);
        fitness(childChromosome2);

        int randomOffspring = rand.nextInt(2);
        if (randomOffspring == 0) {
            mutateChromosome(childChromosome1);
        } else {
            mutateChromosome(childChromosome2);
        }

        family.add(childChromosome1);
        family.add(childChromosome2);

        return family;
    }

    private void mutateChromosome(Chromosome chromosome) {
        ArrayList<Point> genome = chromosome.getChromosome();
        ArrayList<Point> graphPoints = (ArrayList<Point>) graph.getPoints();
        for (int i = 0; i < graph.getPoints().size(); i++) {
            if (!noConflicts(graphPoints.get(i), chromosome.getChromosome().get(i).c(), chromosome)) {
                genome.get(i).setColor(colorList.get(rand.nextInt(numberOfColors)).toString());
            }
        }

        chromosome.setChromosome(genome);
        fitness(chromosome);
    }

}
