package GP;

// a generic interface for all nodes in the tree
public interface Node {
    double evaluate(double[] input);
    String toString();
}