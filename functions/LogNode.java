package GP.functions;

import GP.UnopNode;
import GP.Node;

public class LogNode extends UnopNode {
    public LogNode(Node child) {
        super(child);
    }

    @Override
    public double evaluate(double[] input) {
        return Math.log(getChild().evaluate(input));
    }

    @Override
    public String toString() {
        return "log(" + getChild().toString() + ")";
    }
}