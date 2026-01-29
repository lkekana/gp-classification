package GP.functions;

import GP.UnopNode;
import GP.Node;

public class CosNode extends UnopNode {
    public CosNode(Node child) {
        super(child);
    }

    @Override
    public double evaluate(double[] input) {
        return Math.cos(getChild().evaluate(input));
    }

    @Override
    public String toString() {
        return "cos(" + getChild().toString() + ")";
    }
}