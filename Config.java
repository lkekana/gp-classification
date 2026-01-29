package GP;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    public static final boolean USE_DEFAULT_SEED; // Use default seed for random number generation
    public static final long DEFAULT_SEED;

    // Genetic Programming parameters
    public static final int POPULATION_SIZE; // Size of the population
    public static final int MAX_GENERATIONS; // Maximum number of generations
    public static final double MUTATION_RATE; // Probability of mutation
    public static final double CROSSOVER_RATE; // Probability of crossover
    public static final int TOURNAMENT_SIZE; // Size of tournament for selection
    public static final double ELITISM_RATE; // Proportion of elite individuals to retain
    public static final int MIN_TREE_DEPTH; // Minimum depth of trees (for GP)
    public static final int MAX_TREE_DEPTH; // Maximum depth of trees (for GP)
    public static final int SELECTION_TYPE; // 1 for roulette wheel selection, 2 for tournament selection
    public static final boolean LOCAL_SEARCH_AFTER_SELECTION; // Self-explanatory
    public static final int TERMINATION_CONDITION; // 1 for number of generations, 2 for stagnation, 3 for both
    public static final int ITERATIONS_WITHOUT_IMPROVEMENT; // Number of iterations without improvement
    public static final boolean DEBUG_PRINT; // Enable or disable debug printing

    static {
        Properties properties = new Properties();

        // Load the properties file if it exists
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            System.out.println("Properties file not found. Using default values.");
        }

        // Load values from the properties file or use defaults
        USE_DEFAULT_SEED = Boolean.parseBoolean(properties.getProperty("USE_DEFAULT_SEED", "true"));
        DEFAULT_SEED = Long.parseLong(properties.getProperty("DEFAULT_SEED", "2025"));

        POPULATION_SIZE = Integer.parseInt(properties.getProperty("POPULATION_SIZE", "300"));
        MAX_GENERATIONS = Integer.parseInt(properties.getProperty("MAX_GENERATIONS", "500"));
        MUTATION_RATE = Double.parseDouble(properties.getProperty("MUTATION_RATE", "0.05"));
        CROSSOVER_RATE = Double.parseDouble(properties.getProperty("CROSSOVER_RATE", "0.8"));
        TOURNAMENT_SIZE = Integer.parseInt(properties.getProperty("TOURNAMENT_SIZE", "7"));
        ELITISM_RATE = Double.parseDouble(properties.getProperty("ELITISM_RATE", "0.05"));
        MIN_TREE_DEPTH = Integer.parseInt(properties.getProperty("MIN_TREE_DEPTH", "3"));
        MAX_TREE_DEPTH = Integer.parseInt(properties.getProperty("MAX_TREE_DEPTH", "4"));
        SELECTION_TYPE = Integer.parseInt(properties.getProperty("SELECTION_TYPE", "2"));
        LOCAL_SEARCH_AFTER_SELECTION = Boolean.parseBoolean(properties.getProperty("LOCAL_SEARCH_AFTER_SELECTION", "false"));
        TERMINATION_CONDITION = Integer.parseInt(properties.getProperty("TERMINATION_CONDITION", "1"));
        ITERATIONS_WITHOUT_IMPROVEMENT = Integer.parseInt(properties.getProperty("ITERATIONS_WITHOUT_IMPROVEMENT", "20"));
        DEBUG_PRINT = Boolean.parseBoolean(properties.getProperty("DEBUG_PRINT", "false"));

        // print the loaded configuration
        MagicPrinter.printPurple("\n\n<CONFIGURATION>");
        MagicPrinter.printCyan("\tSeed config.");
        System.out.println("\tUSE_DEFAULT_SEED: " + USE_DEFAULT_SEED);
        System.out.println("\tDEFAULT_SEED: " + DEFAULT_SEED);

        MagicPrinter.printBlue("\n\tGenetic Programming config.");
        System.out.println("\tPOPULATION_SIZE: " + POPULATION_SIZE);
        System.out.println("\tMAX_GENERATIONS: " + MAX_GENERATIONS);
        System.out.println("\tMUTATION_RATE: " + MUTATION_RATE);
        System.out.println("\tCROSSOVER_RATE: " + CROSSOVER_RATE);
        System.out.println("\tTOURNAMENT_SIZE: " + TOURNAMENT_SIZE);
        System.out.println("\tELITISM_RATE: " + ELITISM_RATE);
        System.out.println("\tMIN_TREE_DEPTH: " + MIN_TREE_DEPTH);
        System.out.println("\tMAX_TREE_DEPTH: " + MAX_TREE_DEPTH);
        System.out.println("\tSELECTION_TYPE: " + SELECTION_TYPE);
        System.out.println("\tLOCAL_SEARCH_AFTER_SELECTION: " + LOCAL_SEARCH_AFTER_SELECTION);
        System.out.println("\tTERMINATION_CONDITION: " + TERMINATION_CONDITION);
        System.out.println("\tITERATIONS_WITHOUT_IMPROVEMENT: " + ITERATIONS_WITHOUT_IMPROVEMENT);
        System.out.println("\tDEBUG_PRINT: " + DEBUG_PRINT);
        MagicPrinter.printPurple("</CONFIGURATION>\n\n");
    }

    private Config() {
        // Private constructor to prevent instantiation
    }
}
