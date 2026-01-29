package GP.functions;

import GP.UnopNode;
import GP.Node;

public class AbsNode extends UnopNode {
    public AbsNode(Node child) {
        super(child);
    }

    @Override
    public double evaluate(double[] input) {
        return Math.abs(getChild().evaluate(input));
    }

    @Override
    public String toString() {
        return "|" + getChild().toString() + "|";
    }
}