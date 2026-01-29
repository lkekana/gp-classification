package GP.functions;

import GP.UnopNode;
import GP.Node;

public class SinSquaredNode extends UnopNode {
    public SinSquaredNode(Node child) {
        super(child);
    }

    @Override
    public double evaluate(double[] input) {
        double value = Math.sin(getChild().evaluate(input));
        return value * value;
    }

    @Override
    public String toString() {
        return "sin^2(" + getChild().toString() + ")";
    }
}