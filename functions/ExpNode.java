package GP.functions;

import GP.UnopNode;
import GP.Node;

public class ExpNode extends UnopNode {
    public ExpNode(Node child) {
        super(child);
    }

    @Override
    public double evaluate(double[] input) {
        return Math.exp(getChild().evaluate(input));
    }

    @Override
    public String toString() {
        return "e^(" + getChild().toString() + ")";
    }
}