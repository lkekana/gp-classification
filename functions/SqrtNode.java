package GP.functions;

import GP.UnopNode;
import GP.Node;

public class SqrtNode extends UnopNode {
    public SqrtNode(Node child) {
        super(child);
    }

    @Override
    public double evaluate(double[] input) {
        return Math.sqrt(getChild().evaluate(input));
    }

    @Override
    public String toString() {
        return "sqrt(" + getChild().toString() + ")";
    }
}