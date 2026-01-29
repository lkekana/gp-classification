package GP.functions;

import GP.BinopNode;
import GP.Node;

public class MinNode extends BinopNode {
    public MinNode(Node leftChild, Node rightChild) {
        super(leftChild, rightChild);
    }

    @Override
    public double evaluate(double[] input) {
        return Math.min(getLeftChild().evaluate(input), getRightChild().evaluate(input));
    }

    @Override
    public String toString() {
        return "min(" + getLeftChild().toString() + ", " + getRightChild().toString() + ")";
    }
}
