package GP.functions;

import GP.UnopNode;
import GP.Node;

public class TanSquaredNode extends UnopNode {
    public TanSquaredNode(Node child) {
        super(child);
    }

    @Override
    public double evaluate(double[] input) {
        double value = Math.tan(getChild().evaluate(input));
        return value * value;
    }

    @Override
    public String toString() {
        return "tan^2(" + getChild().toString() + ")";
    }
}