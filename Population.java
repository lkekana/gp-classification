package GP;

import java.util.HashSet;
import java.util.Set;

public class Population {
    public Individual[] individuals;
    public int populationSize;

    public Population(int populationSize) {
        this.populationSize = populationSize;
        individuals = new Individual[populationSize];
        for (int i = 0; i < populationSize; i++) {
            individuals[i] = null;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < individuals.length; i++) {
            if (Config.DEBUG_PRINT)
                System.out.println("ttt:" + individuals[i]);
            sb.append("Individual ").append(i).append(": ").append(individuals[i].toString()).append("\n");
        }
        return sb.toString();
    }

    public void initialisePopulation(TerminalNode[] terminals) {
        for (int i = 0; i < populationSize; i++) {
            individuals[i] = new Individual();
            individuals[i].initialise(Utils.treeGenerator, terminals);
        }
        if (Config.DEBUG_PRINT)
            System.out.println("Population initialised: " + this.toString());
    }

    public void calculateFitness(DataPoint[] inputs) {
        for (Individual individual : individuals) {
            individual.calculateFitness(inputs);
        }
    }

    public Individual bestIndividual(DataPoint[] trainingData) {
        for (Individual individual : individuals) {
            individual.calculateFitness(trainingData);
        }
        Individual bestIndividual = individuals[0];
        for (Individual individual : individuals) {
            if (individual.getFitness() > bestIndividual.getFitness()) {
                bestIndividual = individual;
            }
        }
        return bestIndividual;
    }

    public Population selectParents(DataPoint[] input) {
        Population parents = new Population(individuals.length / 2);

        if (Config.SELECTION_TYPE == 1) {
            // Roulette wheel selection
            int totalFitness = 0;
            double[] fitnessValues = new double[individuals.length];
            for (int i = 0; i < individuals.length; i++) {
                fitnessValues[i] = individuals[i].calculateFitness(input);
                totalFitness += fitnessValues[i];
            }

            double[] probabilities = new double[individuals.length];
            for (int i = 0; i < individuals.length; i++) {
                probabilities[i] = fitnessValues[i] / totalFitness;
            }

            Set<Integer> selectedIndices = new HashSet<>();
            int parentCount = 0;
            while (parentCount < parents.individuals.length) {
                double randomValue = Utils.getGlobalRandom().nextDouble();
                double cumulativeProbability = 0;
                for (int j = 0; j < individuals.length; j++) {
                    if (selectedIndices.contains(j)) {
                        continue; // Skip already selected individuals
                    }
                    cumulativeProbability += probabilities[j];
                    if (randomValue <= cumulativeProbability) {
                        parents.individuals[parentCount] = individuals[j];
                        selectedIndices.add(j); // Mark this individual as selected
                        parentCount++; // Increment the count of selected parents
                        break;
                    }
                }
            }
        } else if (Config.SELECTION_TYPE == 2) {
            // Tournament selection
            Set<Individual> selectedParents = new HashSet<>();
            int parentCount = 0; // Track the number of parents selected
            while (parentCount < parents.individuals.length) {
                Individual[] tournament = new Individual[Config.TOURNAMENT_SIZE];
                for (int j = 0; j < Config.TOURNAMENT_SIZE; j++) {
                    int r;
                    do {
                        r = Utils.getGlobalRandom().nextInt(individuals.length);
                    } while (selectedParents.contains(individuals[r])); // Ensure no duplicates in the tournament
                    tournament[j] = individuals[r];
                }
                Individual fittest = tournament[0];
                for (int j = 1; j < Config.TOURNAMENT_SIZE; j++) {
                    if (tournament[j].getFitness() > fittest.getFitness()) {
                        fittest = tournament[j];
                    }
                }
                parents.individuals[parentCount] = fittest;
                selectedParents.add(fittest); // Mark this individual as selected
                parentCount++; // Increment the count of selected parents
            }
        }
        return parents;
    }

    public Population crossover() {
        Population offspring = new Population(individuals.length * 2); // Create a population of size n
        int parentCount = individuals.length;

        // Validate that all individuals are non-null
        for (int i = 0; i < parentCount; i++) {
            if (individuals[i] == null) {
                throw new IllegalStateException("Individual at index " + i + " is null");
            }
        }

        int offspringIndex = 0;
        Set<String> uniqueTrees = new HashSet<>(); // To track unique trees
        for (int i = 0; i < parentCount; i += 2) {
            Individual parent1 = individuals[i];
            Individual parent2 = individuals[(i + 1) % parentCount]; // Wrap around if needed

            if (parent1.toString().equals(parent2.toString())) {
                if (Config.DEBUG_PRINT)
                    MagicPrinter.printRed("Skipping crossover for identical parents: " + parent1.toString());
                continue; // Skip crossover for this pair
            }

            if (Config.DEBUG_PRINT)
                MagicPrinter.printBlue("Parent 1: " + parent1.toString());
            if (Config.DEBUG_PRINT)
                MagicPrinter.printBlue("Parent 2: " + parent2.toString());

            // Perform crossover
            Individual[] children = subtreeCrossover(parent1, parent2);
            for (Individual child : children) {
                if (child != null && uniqueTrees.add(child.toString())) { // Add only if unique
                    offspring.individuals[offspringIndex++] = child;
                }
            }
        }

        // If the original population size is odd, copy the last individual
        if (parentCount % 2 != 0) {
            if (uniqueTrees.add(individuals[parentCount - 1].toString())) {
                offspring.individuals[offspringIndex++] = individuals[parentCount - 1];
            }
        }

        // Fill missing offspring slots if offspringIndex is less than required
        while (offspringIndex < Config.POPULATION_SIZE) {
            // Fallback: randomly pick one parent (or offspring) and copy it
            int randIndex = Utils.getGlobalRandom().nextInt(individuals.length);
            if (individuals[randIndex] == null) {
                continue; // Skip null individuals
            }
            if (uniqueTrees.add(individuals[randIndex].toString())) { // Add only if unique
                offspring.individuals[offspringIndex++] = individuals[randIndex];
                continue;
            }

            // add randomly generated individual if no unique individuals left
            Individual randomIndividual = new Individual();
            randomIndividual.initialise(Utils.treeGenerator, Utils.createTerminals());

            if (uniqueTrees.add(randomIndividual.toString())) { // Add only if unique
                if (Config.DEBUG_PRINT)
                    MagicPrinter.printYellow("Adding random individual: " + randomIndividual.toString());

                offspring.individuals[offspringIndex++] = randomIndividual;
            }
        }

        // Create a new population with the correct size
        Population finalOffspring = new Population(Config.POPULATION_SIZE);
        for (int i = 0; i < Math.min(Config.POPULATION_SIZE, offspringIndex); i++) {
            finalOffspring.individuals[i] = offspring.individuals[i];
        }

        if (Config.DEBUG_PRINT)
            System.out.println(offspring.individuals.length + " vs " + offspringIndex + " vs "
                    + finalOffspring.individuals.length);
        if (Config.DEBUG_PRINT)
            System.out.println("Offspring: " + finalOffspring.toString());
        return finalOffspring;
    }

    // perform subtree crossover
    private Individual[] subtreeCrossover(Individual partner1, Individual partner2) {
        Individual[] offspring = new Individual[2];
        offspring[0] = new Individual(partner1);
        offspring[1] = new Individual(partner2);

        OperatorNode crossoverPoint1 = null;
        while (crossoverPoint1 == null || crossoverPoint1 == partner1.root) {
            crossoverPoint1 = TreeOperations.getRandomNonTerminalNode(partner1.root);
            if (crossoverPoint1 == null) {
                if (Config.DEBUG_PRINT) System.out.println("Error: Crossover point 1 is null, retrying...");
            }
            if (crossoverPoint1 == partner1.root) {
                if (Config.DEBUG_PRINT) System.out.println("Error: Crossover point 1 is the root, retrying...");
                if (Config.DEBUG_PRINT) MagicPrinter.printCyan("root: " + partner1.root.toString());
            }
        }

        OperatorNode crossoverPoint2 = null;
        while (crossoverPoint2 == null || crossoverPoint2 == partner2.root) {
            crossoverPoint2 = TreeOperations.getRandomNonTerminalNode(partner2.root);
            if (crossoverPoint2 == null) {
                if (Config.DEBUG_PRINT) System.out.println("Error: Crossover point 2 is null, retrying...");
            }
            if (crossoverPoint2 == partner2.root) {
                if (Config.DEBUG_PRINT) System.out.println("Error: Crossover point 2 is the root, retrying...");
                if (Config.DEBUG_PRINT) MagicPrinter.printCyan("root: " + partner2.root.toString());
            }
        }

        if (Config.DEBUG_PRINT)
            MagicPrinter.printPurple("Crossover point 1: " + crossoverPoint1.toString());
        if (Config.DEBUG_PRINT)
            MagicPrinter.printPurple("Crossover point 2: " + crossoverPoint2.toString());

        TreeOperations.swapNodes(partner1.root, crossoverPoint1, crossoverPoint2);
        TreeOperations.swapNodes(partner2.root, crossoverPoint1, crossoverPoint2);
        if (Config.DEBUG_PRINT)
            System.out.println(partner1.root);
        // System.out.println(c);
        offspring[0].root = partner1.root;
        offspring[1].root = partner2.root;

        if (Config.DEBUG_PRINT)
            MagicPrinter.printGreen("Offspring 1: " + offspring[0].toString());
        if (Config.DEBUG_PRINT)
            MagicPrinter.printGreen("Offspring 2: " + offspring[1].toString());
        return offspring;
    }

    public void mutate(double mutationRate, TerminalNode[] terminals) {
        for (Individual individual : individuals) {
            if (individual == null) {
                continue;
            }
            individual.mutate(mutationRate, terminals);
        }
    }

    public void applyElitism(Population parents, Population offspring, DataPoint[] trainingData) {
        // Calculate fitness for all individuals
        parents.calculateFitness(trainingData);
        offspring.calculateFitness(trainingData);

        // Determine how many elite individuals to keep
        int eliteCount = (int) (Config.POPULATION_SIZE * Config.ELITISM_RATE);
        int offspringCount = Config.POPULATION_SIZE - eliteCount;

        // Step 1: Sort parents by fitness (descending order)
        Individual[] sortedParents = parents.individuals.clone();
        java.util.Arrays.sort(sortedParents, (a, b) -> Double.compare(b.getFitness(), a.getFitness()));

        // Step 2: Sort offspring by fitness (descending order)
        Individual[] sortedOffspring = offspring.individuals.clone();
        java.util.Arrays.sort(sortedOffspring, (a, b) -> Double.compare(b.getFitness(), a.getFitness()));

        // Step 3: Add elite individuals from parents to the current population
        int index = 0;
        for (int i = 0; i < eliteCount && i < sortedParents.length; i++) {
            this.individuals[index++] = sortedParents[i];
        }

        // Step 4: Add the best offspring to fill the remaining slots
        for (int i = 0; i < offspringCount && i < sortedOffspring.length; i++) {
            this.individuals[index++] = sortedOffspring[i];
        }

        if (Config.DEBUG_PRINT)
            System.out.println("Elitism applied: " + eliteCount + " elite individuals preserved");
    }

    public void replacePopulation(Population offspring) {
        for (int i = 0; i < individuals.length; i++) {
            individuals[i] = offspring.individuals[i];
        }
    }

    public Individual getBestIndividual(DataPoint[] trainingData) {
        for (Individual individual : individuals) {
            individual.calculateFitness(trainingData);
        }
        Individual bestIndividual = individuals[0];
        for (Individual individual : individuals) {
            if (individual.getFitness() > bestIndividual.getFitness()) {
                bestIndividual = individual;
            }
        }
        return bestIndividual;
    }
}
