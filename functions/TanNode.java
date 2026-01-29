package GP.functions;

import GP.UnopNode;
import GP.Node;

public class TanNode extends UnopNode {
    public TanNode(Node child) {
        super(child);
    }

    @Override
    public double evaluate(double[] input) {
        return Math.tan(getChild().evaluate(input));
    }

    @Override
    public String toString() {
        return "tan(" + getChild().toString() + ")";
    }
}