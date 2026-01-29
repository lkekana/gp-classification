package GP;

import java.util.ArrayList;

public class TreeOperations {

    public static ArrayList<Node> getAllNodesBFS(OperatorNode root) {
        ArrayList<Node> allNodes = new ArrayList<>();
        if (root == null) return allNodes;

        ArrayList<Node> queue = new ArrayList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node currentNode = queue.remove(0);
            allNodes.add(currentNode);

            if (currentNode instanceof UnopNode) {
                Node child = ((UnopNode) currentNode).getChild();
                if (child != null) {
                    queue.add(child);
                }
            } else if (currentNode instanceof BinopNode) {
                Node leftChild = ((BinopNode) currentNode).getLeftChild();
                Node rightChild = ((BinopNode) currentNode).getRightChild();
                if (leftChild != null) {
                    queue.add(leftChild);
                }
                if (rightChild != null) {
                    queue.add(rightChild);
                }
            }
        }

        // for (Node node : allNodes) {
        //     //print parents
        //     if (node instanceof OperatorNode) {
        //         System.out.println("Node: " + node + ", Parent: " + ((OperatorNode) node).parent);
        //     }
        //     else if (node instanceof TerminalNode) {
        //         System.out.println("Node: " + node + ", Parent: " + ((TerminalNode) node).parent);
        //     }
        //     else {
        //         System.out.println("Node: " + node + ", Parent: null");
        //     }
        // }

        return allNodes;
    }

    public static ArrayList<Node> getAllNodesDFS(OperatorNode root) {
        ArrayList<Node> allNodes = new ArrayList<>();
        if (root == null) return allNodes;

        ArrayList<Node> stack = new ArrayList<>();
        stack.add(root);

        while (!stack.isEmpty()) {
            Node currentNode = stack.remove(stack.size() - 1);
            allNodes.add(currentNode);

            if (currentNode instanceof UnopNode) {
                Node child = ((UnopNode) currentNode).getChild();
                if (child != null) {
                    stack.add(child);
                }
            } else if (currentNode instanceof BinopNode) {
                Node leftChild = ((BinopNode) currentNode).getLeftChild();
                Node rightChild = ((BinopNode) currentNode).getRightChild();
                if (rightChild != null) {
                    stack.add(rightChild);
                }
                if (leftChild != null) {
                    stack.add(leftChild);
                }
            }
        }

        return allNodes;
    }

    public static ArrayList<TerminalNode> getTerminalNodes(OperatorNode root, ArrayList<TerminalNode> terminalNodes) {
        if (root == null) return new ArrayList<>();

        if (root instanceof UnopNode) {
            Node child = ((UnopNode) root).getChild();
            if (child == null) {
                throw new IllegalArgumentException("Function node has no child");
            }
            if (child instanceof TerminalNode) {
                TerminalNode c = ((TerminalNode) child);
                terminalNodes.add(c);
            }
            else if (child instanceof OperatorNode) {
                OperatorNode c = ((OperatorNode) child);
                terminalNodes.addAll(getTerminalNodes(c, terminalNodes));
            }
            return terminalNodes;
        }

        if (root instanceof BinopNode) {
            Node leftChild = ((BinopNode) root).getLeftChild();
            Node rightChild = ((BinopNode) root).getRightChild();
            if (leftChild == null || rightChild == null) {
                throw new IllegalArgumentException("Function node has no child");
            }
            if (leftChild instanceof TerminalNode) {
                TerminalNode c = ((TerminalNode) leftChild);
                terminalNodes.add(c);
            }
            else if (leftChild instanceof OperatorNode) {
                OperatorNode c = ((OperatorNode) leftChild);
                terminalNodes.addAll(getTerminalNodes(c, terminalNodes));
            }
            if (rightChild instanceof TerminalNode) {
                TerminalNode c = ((TerminalNode) rightChild);
                terminalNodes.add(c);
            }
            else if (rightChild instanceof OperatorNode) {
                OperatorNode c = ((OperatorNode) rightChild);
                terminalNodes.addAll(getTerminalNodes(c, terminalNodes));
            }
            return terminalNodes;
        }

        throw new IllegalArgumentException("Unknown node type: " + root.getClass().getName());
    }

    public static ArrayList<OperatorNode> getNonTerminalNodes(OperatorNode root, ArrayList<OperatorNode> nonTerminalNodes) {
        if (root == null) return new ArrayList<>();

        if (root instanceof UnopNode) {
            Node child = ((UnopNode) root).getChild();
            if (child == null) {
                throw new IllegalArgumentException("Function node has no child");
            }
            nonTerminalNodes.add(root);
            if (child instanceof OperatorNode) {
                OperatorNode c = ((OperatorNode) child);
                nonTerminalNodes.add(c);
                nonTerminalNodes.addAll(getNonTerminalNodes(c, nonTerminalNodes));
            }
            return nonTerminalNodes;
        }

        if (root instanceof BinopNode) {
            Node leftChild = ((BinopNode) root).getLeftChild();
            Node rightChild = ((BinopNode) root).getRightChild();
            if (leftChild == null || rightChild == null) {
                throw new IllegalArgumentException("Function node has no child");
            }
            nonTerminalNodes.add(root);
            if (leftChild instanceof OperatorNode) {
                OperatorNode c = ((OperatorNode) leftChild);
                nonTerminalNodes.add(c);
                nonTerminalNodes.addAll(getNonTerminalNodes(c, nonTerminalNodes));
            }
            if (rightChild instanceof OperatorNode) {
                OperatorNode c = ((OperatorNode) rightChild);
                nonTerminalNodes.add(c);
                nonTerminalNodes.addAll(getNonTerminalNodes(c, nonTerminalNodes));
            }
            return nonTerminalNodes;
        }

        throw new IllegalArgumentException("Unknown node type: " + root.getClass().getName());
    }

    public static Node getRandomNode(OperatorNode root) {
        if (root == null) {
            throw new IllegalArgumentException("Tree is empty");
        }
        ArrayList<Node> allNodes = getAllNodesBFS(root);
        if (allNodes.isEmpty()) {
            throw new IllegalArgumentException("Tree is empty");
        }
        int randomIndex = Utils.getGlobalRandom().nextInt(allNodes.size());
        if (Config.DEBUG_PRINT) System.out.println("getRandomNode result: " + allNodes.get(randomIndex));
        return allNodes.get(randomIndex);
    }

    public static TerminalNode getRandomTerminalNode(OperatorNode root) {
        if (root == null) {
            throw new IllegalArgumentException("Tree is empty");
        }
        ArrayList<TerminalNode> terminalNodes = getTerminalNodes(root, new ArrayList<>());
        if (terminalNodes.isEmpty()) {
            throw new IllegalArgumentException("Tree has no terminal nodes");
        }
        int randomIndex = Utils.getGlobalRandom().nextInt(terminalNodes.size());
        if (Config.DEBUG_PRINT) System.out.println("getRandomTerminalNode result: " + terminalNodes.get(randomIndex));
        return terminalNodes.get(randomIndex);
    }

    public static OperatorNode getRandomNonTerminalNode(OperatorNode root) {
        if (root == null) {
            throw new IllegalArgumentException("Tree is empty");
        }
        ArrayList<OperatorNode> nonTerminalNodes = getNonTerminalNodes(root, new ArrayList<>());
        if (nonTerminalNodes.isEmpty()) {
            throw new IllegalArgumentException("Tree has no non-terminal nodes");
        }
        int randomIndex = Utils.getGlobalRandom().nextInt(nonTerminalNodes.size());
        if (Config.DEBUG_PRINT) System.out.println("getRandomNonTerminalNode result: " + nonTerminalNodes.get(randomIndex));
        return nonTerminalNodes.get(randomIndex);
    }

    // get parent
    // public static Node getParent(OperatorNode root, Node child) {
    //     System.out.println("getParent: " + root + " " + child);
    //     if (root == null || child == null) {
    //         return null;
    //     }
    //     if (root instanceof UnopNode) {
    //         if (((UnopNode) root).getChild() == child) {
    //             return root;
    //         }
    //         return getParent(((UnopNode) root).getChild(), child);
    //     }
    //     if (root instanceof BinopNode) {
    //         if (((BinopNode) root).getLeftChild() == child || ((BinopNode) root).getRightChild() == child) {
    //             return root;
    //         }
    //         Node leftParent = getParent(((BinopNode) root).getLeftChild(), child);
    //         if (leftParent != null) {
    //             return leftParent;
    //         }
    //         return getParent(((BinopNode) root).getRightChild(), child);
    //     }
    //     if (root instanceof TerminalNode) {
    //         return null;
    //     }
    //     throw new IllegalArgumentException("Unknown node type: " + root.getClass().getName());
    // }

    public static OperatorNode getParent(Node node) {
        if (node == null) {
            throw new IllegalArgumentException("Node is null");
        }
        if (Config.DEBUG_PRINT) System.out.println("getParent: " + node);
        if (node instanceof TerminalNode) {
            return ((TerminalNode) node).parent;
        } else if (node instanceof OperatorNode) {
            return ((OperatorNode) node).parent;
        }
        throw new IllegalArgumentException("Unknown node type: " + node.getClass().getName());
    }


    // swap nodes
    public static void swapNodes(OperatorNode root, Node node1, Node node2) {
        if (root == null || node1 == null || node2 == null) {
            if (Config.DEBUG_PRINT) MagicPrinter.printError("Tree or nodes are null. root=" + root + " node1=" + node1 + " node2=" + node2);
            return;
        }
        if (node1 == node2) {
            System.out.println("Both nodes are the same. No swap needed.");
            return;
        }

        // Get the parents of node1 and node2
        Node parent1 = getParent(node1);
        Node parent2 = getParent(node2);

        if (parent1 == null || parent2 == null) {
            System.out.println("One or both nodes do not have a parent.");
            return;
        }

        if (Config.DEBUG_PRINT) System.out.println("swapping nodes: " + node1 + " and " + node2);

        if (Config.DEBUG_PRINT) System.out.println("before swap: " + parent1);
        if (parent1 instanceof UnopNode) {
            ((UnopNode) parent1).setChild(node2);
            if (Config.DEBUG_PRINT) System.out.println("1: set: " + ((UnopNode) parent1).getChild() + " to " + node2);
        } else if (parent1 instanceof BinopNode) {
            if (((BinopNode) parent1).getLeftChild() == node1) {
                ((BinopNode) parent1).setLeftChild(node2);
                if (Config.DEBUG_PRINT) System.out.println("2: set: " + ((BinopNode) parent1).getLeftChild() + " to " + node2);
            } else {
                ((BinopNode) parent1).setRightChild(node2);
                if (Config.DEBUG_PRINT) System.out.println("3: set: " + ((BinopNode) parent1).getRightChild() + " to " + node2);
            }
        }
        else {
            System.out.println("Parent1 is not a valid operator node.");
            return;
        }
        if (Config.DEBUG_PRINT) System.out.println("after swap: " + parent1);
        
        if (Config.DEBUG_PRINT) System.out.println("before swap: " + parent2);
        if (parent2 instanceof UnopNode) {
            ((UnopNode) parent2).setChild(node1);
        } else if (parent2 instanceof BinopNode) {
            if (((BinopNode) parent2).getLeftChild() == node2) {
                ((BinopNode) parent2).setLeftChild(node1);
            } else {
                ((BinopNode) parent2).setRightChild(node1);
            }
        }
        else {
            System.out.println("Parent2 is not a valid operator node.");
            return;
        }
        if (Config.DEBUG_PRINT) System.out.println("after swap: " + parent2);
    }

    public static int getHeight(Node root) {
        if (root == null) {
            return 0;
        }
        if (root instanceof TerminalNode) {
            return 1;
        }
        if (root instanceof UnopNode) {
            return 1 + getHeight(((UnopNode) root).getChild());
        }
        if (root instanceof BinopNode) {
            return 1 + Math.max(getHeight(((BinopNode) root).getLeftChild()),
                    getHeight(((BinopNode) root).getRightChild()));
        }
        throw new IllegalArgumentException("Unknown node type: " + root.getClass().getName());
    }

    public static int getNumberOfNodes(Node root) {
        if (root == null) {
            return 0;
        }
        if (root instanceof TerminalNode) {
            return 1;
        }
        if (root instanceof UnopNode) {
            return 1 + getNumberOfNodes(((UnopNode) root).getChild());
        }
        if (root instanceof BinopNode) {
            return 1 + getNumberOfNodes(((BinopNode) root).getLeftChild())
                    + getNumberOfNodes(((BinopNode) root).getRightChild());
        }
        throw new IllegalArgumentException("Unknown node type: " + root.getClass().getName());
    }

    // public static OperatorNode getTreeRoot(Node node) {
    //     if (node == null) {
    //         throw new IllegalArgumentException("Node is null");
    //     }
    //     while (node != null) {
    //         if (node instanceof OperatorNode) {
    //             OperatorNode n = ((OperatorNode) node).parent;
    //             if 
    //         }
    //         if (node instanceof TerminalNode) {
    //             node = ((TerminalNode) node).parent;
    //         }
    //     }
        
    // }
}
