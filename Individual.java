package GP;

// A class to represent an individual in the population for a Genetic Programming algorithm

public class Individual {
    public OperatorNode root; // Represents the root node of the individual's tree
    private double fitness; // Represents the fitness value of the individual

    public int truePositives = 0;
    public int falsePositives = 0;
    public int falseNegatives = 0;
    public int trueNegatives = 0;


    public Individual() {
        fitness = 0;
    }

    public Individual(Individual i){
        this.fitness = i.fitness;
        this.truePositives = i.truePositives;
        this.falsePositives = i.falsePositives;
        this.falseNegatives = i.falseNegatives;
        this.trueNegatives = i.trueNegatives;
        this.root = i.root;
    }

    public String toString() {
        return root.toString();
    }

    public void initialise(TreeGenerator treeGenerator, TerminalNode[] input) {
        int depth = Utils.getGlobalRandom().nextInt(Config.MAX_TREE_DEPTH - Config.MIN_TREE_DEPTH + 1) + Config.MIN_TREE_DEPTH;
        if (Config.DEBUG_PRINT) System.out.println("initialise: depth = " + depth);
        root = treeGenerator.generateRandomTree(depth, input);
    }

    // calculate fitness of the individual using the fitness function
    public double calculateFitness(DataPoint[] trainingData) {
        int correct = 0;

        truePositives = 0;
        falsePositives = 0;
        falseNegatives = 0;
        trueNegatives = 0;

        for (int i = 0; i < trainingData.length; i++) {
            double[] input = trainingData[i].getData();
            int expectedOutput = trainingData[i].output;
            double prediction = root.evaluate(input);
            int predictedClass = Utils.squash(prediction);
            if (predictedClass == expectedOutput) {
                correct++;
            }

            if (predictedClass == 1 && expectedOutput == 1) {
                truePositives++;
            } else if (predictedClass == 1 && expectedOutput == 0) {
                falsePositives++;
            } else if (predictedClass == 0 && expectedOutput == 1) {
                falseNegatives++;
            } else {
                trueNegatives++;
            }
        }
        fitness = (double) correct / trainingData.length; // Fitness is the proportion of correct predictions
        return fitness;
    }

    public double getFitness() {
        return fitness;
    }

    // Grow mutation works by randomly selecting a terminal and replacing it with a subtree.
    public void growMutate(TreeGenerator treeGenerator, TerminalNode[] input) {
        // int currentTreeDepth = TreeOperations.getHeight(root);

        // int newSubtreeDepth = Utils.getGlobalRandom().nextInt(Config.MAX_TREE_DEPTH - Config.MIN_TREE_DEPTH + 1) + Config.MIN_TREE_DEPTH;
        // Node newSubtree = treeGenerator.generateRandomTree(depth, input);
        Node newSubtree = null;
        while (newSubtree == null) {
            int newSubtreeDepth = Utils.getGlobalRandom().nextInt(Config.MAX_TREE_DEPTH) + 1;
            if (Config.DEBUG_PRINT) MagicPrinter.printBlue("Generating new subtree with depth: " + newSubtreeDepth);
            newSubtree = treeGenerator.generateRandomTree(newSubtreeDepth, input);
            if (Config.DEBUG_PRINT) System.out.println("Generated new subtree: " + newSubtree + " with height: " + TreeOperations.getHeight(newSubtree));
        }

        Node randomNode = null;
        while (randomNode == null) {
            randomNode = TreeOperations.getRandomTerminalNode(root);
        }

        Node parent = TreeOperations.getParent(randomNode);
        if (parent != null) {
            if (parent instanceof BinopNode) {
                if (((BinopNode) parent).getLeftChild() == randomNode) {
                    ((BinopNode) parent).setLeftChild(newSubtree);
                } else {
                    ((BinopNode) parent).setRightChild(newSubtree);
                }
            } else if (parent instanceof UnopNode) {
                ((UnopNode) parent).setChild(newSubtree);
            }
        }
    }

    // Shrink mutation is an operator that replaces a randomly selected subtree with a randomly created terminal node.
    public void shrinkMutate(TreeGenerator treeGenerator, TerminalNode[] input) {
        int currentTreeDepth = TreeOperations.getHeight(root);
        // Node randomNode = TreeOperations.getRandomNonTerminalNode(root);
        Node randomNode = null;
        while (randomNode == null || currentTreeDepth - TreeOperations.getHeight(randomNode) < Config.MIN_TREE_DEPTH) {
            randomNode = TreeOperations.getRandomNonTerminalNode(root);
        }

        Node parent = TreeOperations.getParent(randomNode);
        if (parent != null) {
            int randomIndex = Utils.getGlobalRandom().nextInt(input.length);
            TerminalNode newNode = input[randomIndex].clone();
            if (parent instanceof BinopNode) {
                if (((BinopNode) parent).getLeftChild() == randomNode) {
                    ((BinopNode) parent).setLeftChild(newNode);
                } else {
                    ((BinopNode) parent).setRightChild(newNode);
                }
            } else if (parent instanceof UnopNode) {
                ((UnopNode) parent).setChild(newNode);
            }
        }
    }

    // use grow-and-shrink mutation on the tree
    public void mutate(double mutationRate, TerminalNode[] terminals) {
        if (Config.DEBUG_PRINT) System.out.println("pre-mutation: " + this.toString());
        if (Utils.getGlobalRandom().nextDouble() < mutationRate) {
            if (Config.DEBUG_PRINT) MagicPrinter.printRainbow("Mutation is happening!");

            // if (Utils.getGlobalRandom().nextBoolean()) {
            //     growMutate(Utils.treeGenerator, terminals);
            // } else {
            //     shrinkMutate(Utils.treeGenerator, terminals);
            // }

            // Check the tree depth and decide the mutation type
            int treeDepth = TreeOperations.getHeight(root);
            if (treeDepth > Config.MAX_TREE_DEPTH) {
                if (Config.DEBUG_PRINT) MagicPrinter.printRed("Tree depth exceeds max depth. Using shrink mutation.");
                shrinkMutate(Utils.treeGenerator, terminals);
            } else {
                if (Config.DEBUG_PRINT) MagicPrinter.printGreen("Tree depth is within limits. Using grow mutation.");
                growMutate(Utils.treeGenerator, terminals);
            }
        }

        if (Config.DEBUG_PRINT) System.out.println("post-mutation: " + this.toString());
    }
}
