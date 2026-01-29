package GP;

public class UnopNode extends OperatorNode {
    private Node child = null;

    public UnopNode(Node child) {
        this.child = child;
    }

    public Node getChild() {
        return child;
    }

    public void setChild(Node child) {
        this.child = child;
        if (child instanceof TerminalNode) {
            ((TerminalNode) child).parent = this;
        }
        if (child instanceof OperatorNode) {
            ((OperatorNode) child).parent = this;
        }
    }

    @Override
    public double evaluate(double[] input) {
        throw new UnsupportedOperationException("UnopNode.evaluate() not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("UnopNode.toString() not implemented");
    }

}
