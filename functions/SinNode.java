package GP.functions;

import GP.UnopNode;
import GP.Node;

public class SinNode extends UnopNode {
    public SinNode(Node child) {
        super(child);
    }

    @Override
    public double evaluate(double[] input) {
        return Math.sin(getChild().evaluate(input));
    }

    @Override
    public String toString() {
        return "sin(" + getChild().toString() + ")";
    }
}