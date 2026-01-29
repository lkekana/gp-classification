# Genetic Programming for Financial Time Series Prediction

## Overview

Made as my final assignment in my Artificial Intelligence course, this project demonstrates an advanced machine learning technique called Genetic Programming (GP) applied to financial market prediction. The system automatically evolves mathematical formulas that can predict whether Bitcoin prices will go up or down based on historical price data.

## What is Genetic Programming?

Genetic Programming is an artificial intelligence technique inspired by biological evolution. Instead of manually creating a formula or algorithm, GP starts with a random population of potential solutions (in this case, mathematical expressions) and evolves them over many generations using principles similar to natural selection:

-   **Selection**: Better-performing formulas are more likely to be chosen for reproduction.
-   **Crossover**: Good formulas are combined to create new formulas.
-   **Mutation**: Random changes are introduced to maintain diversity.
-   **Survival of the fittest**: Over time, the population improves as better formulas survive.

Think of it like breeding mathematical expressions - the "fittest" formulas (those that make better predictions) are more likely to pass their characteristics to the next generation.

## The Problem: Financial Market Prediction

Financial markets are notoriously difficult to predict due to their complex, chaotic nature. This project attempts to solve a binary classification problem: given historical price data (`Open`, `High`, `Low`, `Close` prices), predict whether the next price movement will be **up** (`1`) or **down** (`0`).

This is a challenging problem because:
-   Markets are influenced by countless factors.
-   Price movements often appear random.
-   Patterns that exist may change over time.
-   Small improvements in prediction accuracy can have significant financial value.

## The Solution: Evolved Mathematical Expressions

Rather than using traditional machine learning approaches, this system evolves custom mathematical formulas tailored to the specific dataset. Each formula takes the input price data and outputs a prediction. The system automatically discovers which mathematical operations and combinations work best for the prediction task.

For example, an evolved formula might look like: `sin(Close) + log(High/Low) * sqrt(Open)`- though the actual evolved formulas are usually much more complex.

### Technical Approach

The system implements a complete genetic programming pipeline:

1.  **Initialization**: Creates a random population of mathematical expression trees.
2.  **Evaluation**: Tests each formula against historical data to measure prediction accuracy.
3.  **Selection**: Chooses the best-performing formulas to create the next generation.
4.  **Variation**: Applies crossover and mutation to create new formulas.
5.  **Iteration**: Repeats the process for hundreds of generations.
6.  **Result**: Returns the best-performing formula discovered.

The system includes sophisticated features like:
-   Tree depth limits to prevent overly complex formulas.
-   Multiple mathematical operations (trigonometric, logarithmic, arithmetic).
    - Arithmetic operations: $+$, $-$, $*$, $/$
    - Trigonometric functions: $sin(x)$, $cos(x)$, $tan(x)$, $sin^2(x)$, $cos^2(x)$, $tan^2(x)$
    - Exponential and logarithmic functions: $e^x$ ($exp(x)$), $ln(x)$
    - Root and absolute value functions: $sqrt(x)$, $|x|$
-   Comprehensive performance metrics (accuracy, precision, recall, F-score).

### Design Specifications

The algorithm was implemented with the following configuration parameters:
- **Population Size**: 300 (chosen for population diversity)
- **Maximum Generations**: 500 (allows sufficient convergence time)
- **Mutation Rate**: 0.05 (kept low to prevent destruction of good solutions while allowing gradual improvements)
- **Crossover Rate**: 0.8 (promotes information exchange between successful individuals)
- **Tournament Size**: 7 (provides balanced selection)
- **Elitism Rate**: 0.05 (ensures that the best solutions are preserved across generations)
- **Tree Depth**: 3–4 (prevents combinatorial explosion and memory constraints)

### Algorithm Features

The implementation incorporates the following features:
- **Tournament Selection**: Provides good balance between exploration and exploitation
- **Elitism**: Preserves the best individuals to prevent loss of optimal solutions
- **Subtree Crossover**: Operates on non-terminal nodes only to maintain tree structure integrity
- **Grow and Shrink Mutation**: Prevents trees from becoming too large or too small
- **Termination Condition**: Uses iteration count to ensure adequate exploration
- **Diversity Maintenance**: Generates random trees when insufficient programs are selected
- **Local Search**: Uses a Simulated Annealing algorithm to refine the best solution found by GP


## Results

### Dataset Analysis
The given dataset in `Euro_USD Stock/` with the training data in `BTC_train.csv` and test data in `BTC_test.csv` was thoroughly analyzed before implementation.

The dataset consists of Bitcoin (BTC) historical data with training and test sets containing 998 and 263 instances respectively. Analysis revealed the characteristics below:
- The training data contains 470 instances of class "0" and 528 instances of class "1", indicating a slight bias toward the positive class.
- The first four columns (Open, High, Low, Close) appear to be normalized, having similar distributions.
- The AdjClose column shows significant outliers, with 126 out of 1000 values falling between Q3 and the maximum value of 16, while the mean is 0.00 and Q3 is 1.09.

Due to the distribution of the AdjClose column, this feature was excluded from the GP implementation to prevent outliers from negatively impacting prediction accuracy.

### Algorithm Performance

![Training and Test Accuracy over Generations](img/screenshot1.png)

| Dataset | True Positives | True Negatives | False Positives | False Negatives |
|---------|-----------------|----------------|-----------------|-----------------|
| Training | 410 | 466 | 4 | 118 |
| Test | 125 | 130 | 0 | 8 |

| Dataset | Accuracy | Precision | Recall | F-Score |
|---------|----------|-----------|--------|---------|
| Training | 87.78% | 99.03% | 77.65% | 0.8705 |
| Test | 96.96% | 100% | 93.98% | 0.9690 |

The algorithm achieved solid performance with 87.78% training accuracy and 96.96% test
accuracy. The high precision (99.03% training, 100% test) indicates very few false positives, while good recall (77.65% training, 93.98% test) shows effective identification of positive cases. The improvement from training to test suggests the evolved mathematical expression generalizes well to unseen data.


The best evolved solution was:
$$
\tan^2\big(\tan(\cos(\cos^2(\text{Close})))\big) \,/\, \sin(\text{Close} - \text{Open})
$$

GP’s exceptional performance can be attributed to many key factors. Most importantly, the algorithm’s ability to evolve domain-specific mathematical expressions allows it to discover complex non-linear relationships that are particularly relevant to the given data. The evolved expression represents a feature that traditional algorithms did not discover.

The data-driven approach of GP proved valuable, as it automatically identified pat- terns specific to the Bitcoin dataset without requiring manual feature engineering. Additionally, the exclusion of the problematic AdjClose column prevented noise from affecting the model, while the fine-tuned evolutionary parameters (population size, mutation rates, and selection pressure) contributed to effective search through the solution space.

An interesting observation is that GP showed better performance on the test set (96.96%) than on the training set (87.78%), which is unusual in the given context. This suggests that the expression captures fundamental underlying patterns in the data rather than overfitting to training-specific noise.

## Technical Requirements

-   Java 8 or higher
-   CSV data files for training and testing
-   Configuration file for algorithm parameters

## How to Run

1.  Clone the repository.
2.  Run the main program:
    ```bash
    java -jar Main.jar
    ```
3.  View results in the console output.

Or, to compile and run from source:
```bash
make run
```

### Note:
- If the `config.properties` file is not found, the program will use default values for all parameters.
- The loaded configuration is printed to the console at runtime for verification.

## Personal Insights

> What I learned?
- Given my really old and really slow laptop, how important tuning of GP parameters (population size, depth limits, mutation/crossover balance) is to control bloat, improve convergence and manage runtime.
- The importance of checking and filtering the training data set for feature normalization and outlier handling, like excluding volatile features (e.g., AdjClose) to improve stability.
- The trade-offs between precision and recall in financial classification; high precision minimized false signals.

> Challenges faced?
- Managing tree bloat required grow/shrink mutation and subtree constraints (including division by zero errors).
- Tournament size and elitism strongly impacted diversity; too much elitism led to premature convergence.

> Relevance to my career?
- It reinforced my interest in Machine Learning and AI, especially evolutionary algorithms.
- The project gave me experience in applying AI techniques to real-world problems, which is valuable for roles in data science and quantitative analysis.
- The project enhanced my Java programming skills and understanding of algorithm design.
- The assignment gave me the opportunity to actually code a binary classification algorithm from scratch, which helped turn the theory into a practical skill and gave me a valuable skill in the AI/ML field.

## About This Project

This project showcases advanced knowledge in:
-   Machine learning and artificial intelligence
-   Evolutionary algorithms and genetic programming
-   Financial data analysis
-   Java programming and object-oriented design
-   Performance optimization and algorithm design

The implementation demonstrates the ability to tackle complex, real-world problems using cutting-edge AI techniques and provides a foundation for applying genetic programming to other domains beyond finance.