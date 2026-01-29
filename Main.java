package GP;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        if (Config.USE_DEFAULT_SEED) Utils.initGlobalRandom(Config.DEFAULT_SEED);

        GeneticProgram gp = new GeneticProgram("Euro_USD Stock/BTC_train.csv", false, -1);
        long startTime = System.currentTimeMillis();
        Individual bestIndividual = gp.run();
        long endTime = System.currentTimeMillis();
        System.out.println("Best individual: " + bestIndividual);
        System.out.println("Time taken: " + (endTime - startTime) + "ms");


        MagicPrinter.printGreen("\n\nTraining statistics:");
        System.out.println("Accuracy: " + (bestIndividual.getFitness() * 100) + "%");

        // Calculate precision, recall, and F-score
        double trainingPrecision = bestIndividual.truePositives / (double) (bestIndividual.truePositives + bestIndividual.falsePositives);
        double trainingRecall = bestIndividual.truePositives / (double) (bestIndividual.truePositives + bestIndividual.falseNegatives);
        double trainingFScore = 2 * (trainingPrecision * trainingRecall) / (trainingPrecision + trainingRecall);
        System.out.println("True Positives: " + bestIndividual.truePositives);
        System.out.println("False Positives: " + bestIndividual.falsePositives);
        System.out.println("False Negatives: " + bestIndividual.falseNegatives);
        System.out.println("True Negatives: " + bestIndividual.trueNegatives);
        System.out.println("Precision: " + trainingPrecision);
        System.out.println("Recall: " + trainingRecall);
        System.out.println("F-Score: " + trainingFScore);

        // run test cases on the best individual and calculate accuracy
        ArrayList<DataPoint> testCases = Utils.readFile("Euro_USD Stock/BTC_test.csv");
        if (testCases == null || testCases.size() == 0) {
            System.out.println("No test cases found in the file.");
            System.exit(0);
        }

        int correct = 0;

        int truePositives = 0;
        int falsePositives = 0;
        int falseNegatives = 0;
        int trueNegatives = 0;

        for (DataPoint testCase : testCases) {
            double[] input = testCase.getData();
            int expectedOutput = testCase.output;
            double prediction = bestIndividual.root.evaluate(input);
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

        MagicPrinter.printGreen("\n\nTest case statistics:");
        double accuracy = (double) correct / testCases.size();
        System.out.println("Accuracy: " + (accuracy * 100) + "%");

        // Calculate precision, recall, and F-score
        double testPrecision = truePositives / (double) (truePositives + falsePositives);
        double testRecall = truePositives / (double) (truePositives + falseNegatives);
        double testFScore = 2 * (testPrecision * testRecall) / (testPrecision + testRecall);

        System.out.println("True Positives: " + truePositives);
        System.out.println("False Positives: " + falsePositives);
        System.out.println("False Negatives: " + falseNegatives);
        System.out.println("True Negatives: " + trueNegatives);
        System.out.println("Precision: " + testPrecision);
        System.out.println("Recall: " + testRecall);
        System.out.println("F-Score: " + testFScore);
        System.out.println();
    }
}
