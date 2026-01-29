package GP.functions;

import GP.BinopNode;
import GP.Node;

public class DivideNode extends BinopNode {
    public DivideNode(Node leftChild, Node rightChild) {
        super(leftChild, rightChild);
    }

    @Override
    public double evaluate(double[] input) {
        double rightValue = getRightChild().evaluate(input);
        if (rightValue == 0) {
            return 0; // Handle division by zero, as advised in the textbook notes
        }
        return getLeftChild().evaluate(input) / rightValue;
    }

    @Override
    public String toString() {
        return getLeftChild().toString() + " / " + getRightChild().toString();
    }
}