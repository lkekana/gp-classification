package GP;

public class TerminalNode implements Node {
    private final String name;
    private final int index;
    public OperatorNode parent = null;

    public TerminalNode(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public TerminalNode clone() {
        return new TerminalNode(name, index);
    }

    @Override
    public double evaluate(double[] input) {
        return input[index];
    }

    @Override
    public String toString() {
        return name;
    }

    
}