package GP.functions;

import GP.BinopNode;
import GP.Node;

public class MaxNode extends BinopNode {
    public MaxNode(Node leftChild, Node rightChild) {
        super(leftChild, rightChild);
    }

    @Override
    public double evaluate(double[] input) {
        return Math.max(getLeftChild().evaluate(input), getRightChild().evaluate(input));
    }

    @Override
    public String toString() {
        return "max(" + getLeftChild().toString() + ", " + getRightChild().toString() + ")";
    }
}
