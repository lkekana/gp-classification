package GP.functions;

import GP.UnopNode;
import GP.Node;

public class CosSquaredNode extends UnopNode {
    public CosSquaredNode(Node child) {
        super(child);
    }

    @Override
    public double evaluate(double[] input) {
        double value = Math.cos(getChild().evaluate(input));
        return value * value;
    }

    @Override
    public String toString() {
        return "cos^2(" + getChild().toString() + ")";
    }
}