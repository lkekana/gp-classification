package GP;

import GP.functions.*;

public class TreeGenerator {
    private Node generateRandomTreeHelper(int height, TerminalNode[] terminals) {
        if (terminals == null || terminals.length == 0) {
            throw new IllegalArgumentException("Terminals array cannot be null or empty");
        }

        // Base case: If height is 0, return a TerminalNode
        if (height == 0) {
            int randomIndex = Utils.getGlobalRandom().nextInt(terminals.length);
            return terminals[randomIndex].clone();
        }

        // Recursive case: Randomly decide between UnopNode and BinopNode
        if (Utils.getGlobalRandom().nextBoolean()) {
            Node child = generateRandomTreeHelper(height - 1, terminals);
            UnopNode result = createRandomUnopNode(child);
            if (child instanceof TerminalNode) {
                ((TerminalNode) child).parent = result;
            }
            if (child instanceof OperatorNode) {
                ((OperatorNode) child).parent = result;
            }
            return result;
        } else {
            Node leftChild = generateRandomTreeHelper(height - 1, terminals);
            Node rightChild = generateRandomTreeHelper(height - 1, terminals);
            BinopNode result = createRandomBinopNode(leftChild, rightChild);
            if (leftChild instanceof TerminalNode) {
                ((TerminalNode) leftChild).parent = result;
            }
            if (leftChild instanceof OperatorNode) {
                ((OperatorNode) leftChild).parent = result;
            }
            if (rightChild instanceof TerminalNode) {
                ((TerminalNode) rightChild).parent = result;
            }
            if (rightChild instanceof OperatorNode) {
                ((OperatorNode) rightChild).parent = result;
            }
            return result;
        }
    }

    public OperatorNode generateRandomTree(int height, TerminalNode[] terminals) {
        if (Config.DEBUG_PRINT) System.out.println("generateRandomTree: height = " + height);
        if (height < 0) {
            throw new IllegalArgumentException("Height cannot be negative");
        }
        if (terminals == null || terminals.length == 0) {
            throw new IllegalArgumentException("Terminals array cannot be null or empty");
        }

        Node root = generateRandomTreeHelper(height, terminals);
        if (root instanceof OperatorNode) {
            return (OperatorNode) root;
        } else {
            throw new IllegalStateException("Generated tree root is not an OperatorNode");
        }
    }

    private UnopNode createRandomUnopNode(Node child) {
        switch (Utils.getGlobalRandom().nextInt(10)) {
            case 0:
                return new SinNode(child);
            case 1:
                return new CosNode(child);
            case 2:
                return new TanNode(child);
            case 3:
                return new SinSquaredNode(child);
            case 4:
                return new CosSquaredNode(child);
            case 5:
                return new TanSquaredNode(child);

            case 6:
                return new ExpNode(child);
            case 7:
                return new LogNode(child);
            case 8:
                return new SqrtNode(child);
            case 9:
                return new AbsNode(child);

            default:
                throw new IllegalStateException("Unexpected value");
        }
    }

    private BinopNode createRandomBinopNode(Node leftChild, Node rightChild) {
        switch (Utils.getGlobalRandom().nextInt(4)) {
            case 0:
                return new AddNode(leftChild, rightChild);
            case 1:
                return new SubtractNode(leftChild, rightChild);
            case 2:
                return new MultiplyNode(leftChild, rightChild);
            case 3:
                return new DivideNode(leftChild, rightChild);
            case 4:
                return new MinNode(leftChild, rightChild);
            case 5:
                return new MaxNode(leftChild, rightChild);
            default:
                throw new IllegalStateException("Unexpected value");
        }
    }
}