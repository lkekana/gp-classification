package GP;
import java.util.List;

public class GeneticProgram {
    List<DataPoint> dataPoints;
    Population population;
    long seed;
    boolean useLocalSearch;
    TerminalNode[] terminals;

    public GeneticProgram(String filename, boolean useLocalSearch, long seed) {
        dataPoints = Utils.readFile(filename);
        if (dataPoints == null || dataPoints.size() == 0) {
            System.out.println("No data points found in the file.");
            System.exit(0);
        }

        terminals = Utils.createTerminals();
        this.seed = seed;
        if (seed == -1) {
            this.seed = Utils.generateRandomSeed();
        }
        population = new Population(Config.POPULATION_SIZE);
        this.useLocalSearch = useLocalSearch;
    }

    public void localSearch() {
        if (useLocalSearch) {
            for (int i = 0; i < population.populationSize; i++) {
                SAA saa = new SAA(population.individuals[i], 1000);
                Individual localSearchIndividual = saa.run();
                population.individuals[i] = localSearchIndividual;
            }
        }
    }

    // The main run() method that will be used to run the Genetic Algorithm
    public Individual run() {
        // Initialise the population
        population.initialisePopulation(terminals);

        // Calculate the fitness of all individuals
        DataPoint[] dataPointsArray = new DataPoint[dataPoints.size()];
        population.calculateFitness(dataPoints.toArray(dataPointsArray));

        int iterations = 0;
        if (Config.TERMINATION_CONDITION == 1) {
            while (iterations < Config.MAX_GENERATIONS) {
                // System.out.println("iteration"+iterations);
                // Select fitter individuals for reproduction
                Population parents = population.selectParents(dataPointsArray);
                if (Config.DEBUG_PRINT) System.out.println("Selected parents: " + parents);

                if (useLocalSearch && Config.LOCAL_SEARCH_AFTER_SELECTION) {
                    localSearch();
                }

                // Recombine individuals
                Population offspring = parents.crossover();

                // Mutate individuals
                offspring.mutate(Config.MUTATION_RATE, terminals);

                // Perform local search if required
                // Note: The local search is performed after mutation in this case
                if (useLocalSearch && !Config.LOCAL_SEARCH_AFTER_SELECTION) {
                    localSearch();
                }

                // Apply elitism: combine best parents with best offspring
                population.applyElitism(parents, offspring, dataPointsArray);

                // Evaluate fitness of all individuals
                population.calculateFitness(dataPointsArray);
                if (Config.DEBUG_PRINT) System.out.println("iteration "+iterations + ": " + population.bestIndividual(dataPointsArray).getFitness());

                // Generate a new population
                // population = offspring;
                iterations++;
            }
        }
        else if (Config.TERMINATION_CONDITION == 2) {
            double[] bestFitnesses = new double[Config.ITERATIONS_WITHOUT_IMPROVEMENT];
            int stagnationCount = 0;
            while (stagnationCount < Config.ITERATIONS_WITHOUT_IMPROVEMENT) {
                if (Config.DEBUG_PRINT) System.out.println("iteration"+iterations);
                // Select fitter individual for reproduction
                Population parents = population.selectParents(dataPointsArray);

                if (useLocalSearch && Config.LOCAL_SEARCH_AFTER_SELECTION) {
                    localSearch();
                }

                // Recombine individuals
                Population offspring = parents.crossover();

                // Mutate individuals
                offspring.mutate(Config.MUTATION_RATE, terminals);

                if (useLocalSearch && !Config.LOCAL_SEARCH_AFTER_SELECTION) {
                    localSearch();
                }

                // Evaluate fitness of all individuals
                offspring.calculateFitness(dataPointsArray);

                // Generate a new population
                population = offspring;

                Individual i = population.getBestIndividual(dataPointsArray);
                double bestFitness = i.getFitness();
                if (bestFitnesses[stagnationCount % Config.ITERATIONS_WITHOUT_IMPROVEMENT] == bestFitness) {
                    stagnationCount++;
                } else {
                    bestFitnesses[stagnationCount % Config.ITERATIONS_WITHOUT_IMPROVEMENT] = bestFitness;
                    stagnationCount = 0;
                }
                if (Config.DEBUG_PRINT) System.out.println("iteration "+iterations + ": " + bestFitness);
            }
        }
        else if (Config.TERMINATION_CONDITION == 3) {
            double[] bestFitnesses = new double[Config.ITERATIONS_WITHOUT_IMPROVEMENT];
            int stagnationCount = 0;
            while (iterations < Config.MAX_GENERATIONS && stagnationCount < Config.ITERATIONS_WITHOUT_IMPROVEMENT) {
                // System.out.println("iteration"+iterations);
                // Select fitter individual for reproduction
                Population parents = population.selectParents(dataPointsArray);

                if (useLocalSearch && Config.LOCAL_SEARCH_AFTER_SELECTION) {
                    localSearch();
                }

                // Recombine individuals
                Population offspring = parents.crossover();

                // Mutate individuals
                offspring.mutate(Config.MUTATION_RATE, terminals);

                if (useLocalSearch && !Config.LOCAL_SEARCH_AFTER_SELECTION) {
                    localSearch();
                }

                // Evaluate fitness of all individuals
                offspring.calculateFitness(dataPointsArray);

                // Generate a new population
                population = offspring;

                Individual i = population.getBestIndividual(dataPointsArray);
                double bestFitness = i.getFitness();
                if (bestFitnesses[stagnationCount % Config.ITERATIONS_WITHOUT_IMPROVEMENT] == bestFitness) {
                    stagnationCount++;
                } else {
                    bestFitnesses[stagnationCount % Config.ITERATIONS_WITHOUT_IMPROVEMENT] = bestFitness;
                    stagnationCount = 0;
                }

                if (Config.DEBUG_PRINT) System.out.println("iteration "+iterations + ": " + bestFitness);
                iterations++;
            }
        }

        // Return the best individual
        Individual bestIndividual = population.getBestIndividual(dataPointsArray);
        // System.out.println("Best individual: " + bestIndividual);
        return bestIndividual;
    }
}


