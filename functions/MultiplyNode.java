package GP.functions;

import GP.BinopNode;
import GP.Node;

public class MultiplyNode extends BinopNode {
    public MultiplyNode(Node leftChild, Node rightChild) {
        super(leftChild, rightChild);
    }

    @Override
    public double evaluate(double[] input) {
        return getLeftChild().evaluate(input) * getRightChild().evaluate(input);
    }

    @Override
    public String toString() {
        return getLeftChild().toString() + " * " + getRightChild().toString();
    }
}