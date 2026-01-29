package GP;

/*
 * A class that implements Simulated Annealing Algorithm for the Travelling Salesman Problem (TSP) using,
 * - a given Graph object
 * - given start and end Node objects
 * - a maximum number of iterations
 */

import java.util.LinkedList;
import java.util.List;

public class SAA {
    private Individual givenIndividual;
    private Individual bestIndividual;
    private int maxIterations;
    private double initialTemperature = 100;
    private double totalFitness = 0;
    private double averageFitness = 0;
    private int numIterations = 0;
    private List<Double> averageFitnesses = new LinkedList<>();
    private List<Double> bestFitnesses = new LinkedList<>();

    public SAA(Individual givenIndividual, int maxIterations) {
        this.givenIndividual = givenIndividual;
        this.maxIterations = maxIterations;
    }

    void setInitialTemperature(double initialTemperature){
        this.initialTemperature = initialTemperature;
    }

    public void prepareAverageFitness(Individual individual){
        totalFitness += individual.getFitness();
        numIterations++;
        averageFitness = totalFitness / numIterations;
    }

    public Individual run(){
        Individual currentIndividual = givenIndividual;
        bestIndividual = givenIndividual;
        prepareAverageFitness(currentIndividual);

        double currentTemp = initialTemperature;
        int t = 1;
        for (int i = 0; i < maxIterations; i++) {
            Individual newIndividual = perturbIndividual(currentIndividual);
            double fitnessDifference = newIndividual.getFitness() - currentIndividual.getFitness();
            prepareAverageFitness(currentIndividual);

            if (fitnessDifference < 0) {
                currentIndividual = newIndividual;
            }
            else{
                double probability = Math.exp(-fitnessDifference / currentTemp);
                double min = Math.min(1, probability);
                double r = Utils.getGlobalRandom().nextDouble();
                if (r < min) {
                    currentIndividual = newIndividual;
                }
            }

            if (currentIndividual.getFitness() > bestIndividual.getFitness()) {
                bestIndividual = currentIndividual;
            }
            collectExperimentData(averageFitness, bestIndividual.getFitness());
            t = t + 1;
            currentTemp = initialTemperature / Math.log(t + 1);
        }

        return bestIndividual;
    }

    public void experimentRun(){
        long startTime = System.currentTimeMillis();
        Individual best = run();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("**********************************************");
        System.out.println("EXPERIMENT RESULTS");
        System.out.println("Best Individual: " + best);
        System.out.println("Fitness: " + best.getFitness());
        System.out.println("Runtime: " + duration + "ms");
        System.out.println("Average Fitness: " + averageFitness);
    }

    private Individual perturbIndividual(Individual individual){
        // Perturb the individual by making a random change
        Individual newIndividual = new Individual(individual);
        // Perform perturbation logic here
        return newIndividual;
    }

    private void collectExperimentData(double averageFitness, double bestFitness){
        averageFitnesses.add(averageFitness);
        bestFitnesses.add(bestFitness);
    }
}
