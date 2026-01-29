package GP;

public class BinopNode extends OperatorNode {
    private Node leftChild = null;
    private Node rightChild = null;

    public BinopNode(Node leftChild, Node rightChild) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
        if (leftChild instanceof TerminalNode) {
            ((TerminalNode) leftChild).parent = this;
        }
        if (leftChild instanceof OperatorNode) {
            ((OperatorNode) leftChild).parent = this;
        }
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
        if (rightChild instanceof TerminalNode) {
            ((TerminalNode) rightChild).parent = this;
        }
        if (rightChild instanceof OperatorNode) {
            ((OperatorNode) rightChild).parent = this;
        }
    }

    @Override
    public double evaluate(double[] input) {
        throw new UnsupportedOperationException("BinopNode.evaluate() not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("BinopNode.toString() not implemented");
    }

    
}