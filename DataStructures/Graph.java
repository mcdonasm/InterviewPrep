import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * Graph Class that implements a simple Node class, using it to encapsulate various graph/tree functionality.
 *
 * BFS
 * DFS
 * BST
 * Graph Balancing
 */
public class Graph {

    public static class Node<T extends Comparable> {
        public T data;
        public List<Node<T>> children;
        public Node<T> parent;

        public Node(T data) {
            this.data = data;
        }

        public Node(T data, Node<T> parent) {
            this(data);
            this.parent = parent;
        }

        public Node(T data, List<Node<T>> children) {
            this.data = data;
            this.children = children;
        }

        public Node(T data, Node<T> parent, List<Node<T>> children) {
            this(data, children);
            this.parent = parent;
        }

    }

    /**
     * Implementation of a Binary Search Tree. BSTs rules are as followes:
     * - A node may only have at most, 2 children.
     * - All left children (index 0) must be less than their parent.
     * - All right children (index 1) must be larger than their parent.
     * - No duplicates allowed. If a duplicate is attempted to be added, null will be returned.
     *
     * Methods:
     * - add(T data): adds a new node, returning a reference to the node object that was just added, or null if the value is already in the BST.
     * - remove(T data): removes the node with the value 'data', returning true if the value was removed, false otherwise (the value isn't in the tree).
     * - contains(T data): returns true if 'data' is in the tree, false otherwise.
     * - balance(): balances the tree, making the height of the left and right children of the root at most 1 level different.
     *
     * @param <T>
     */
    public static class BST<T extends Comparable> {
        public Node<T> root;
        public Set<T> values = new HashSet<>();

        public BST() {
        }

        public BST(T... values) {
            this.addAll(values);
        }

        public BST(List<T> values) {
            this.addAll(values);
        }

        public void addAll(List<T> values) {
            values.forEach(this::add);
        }

        public void addAll(T... values) {
            addAll(Arrays.asList(values));
        }

        public Node<T> add(T data) {
            if(this.root == null) {
                values.add(data);
                root = newNode(data, null);
                return root;
            }
            return addWorker(root, data);
        }

        public boolean remove(T data) {
            Node<T> removeNode = get(data);
            if(removeNode == null) {
                return false;
            }
            //Brute Force
            List<T> removedValues = getAllValues(removeNode, new ArrayList<>());
            removedValues.remove(removeNode.data);
            if(removeNode.parent != null) {
                removeNode.parent.children.remove(removeNode);
                removeNode.parent = null;
            }

            removedValues.forEach(this::add);

            values.remove(data);
            return true;
        }

        /**
         * Returns the node in the graph, which has a given value. Null if the value isn't present.
         */
        public Node<T> get(T data) {
            return getWorker(root, data);
        }

        /**
         * Recursive method for getting all values in a graph below a given root.
         */
        public List<T> getAllValues(Node<T> current, List<T> results) {
            results.add(current.data);
            if(current.children.get(0) != null)
                getAllValues(current.children.get(0), results);
            if(current.children.get(1) != null)
                getAllValues(current.children.get(1), results);
            return results;
        }

        public int getHeight() {
            return measureHeight(root);
        }

        public static int measureHeight(BST graph) {
            return measureHeight(graph.root);
        }

        public static int measureHeight(Node root) {
            if(root.children.size() == 0) {
                return 1;
            }
            return measureHeightWorker(root, 1);
        }

        private static int measureHeightWorker(Node current, int height) {
            if(current.children.size() == 0) {
                return height +1;
            } else {
                int leftHeight = 0;
                int rightHeight = 0;
                if(current.children.get(0) != null) {
                    leftHeight = measureHeightWorker((Node) current.children.get(0), 0);
                }
                if(current.children.get(1) != null) {
                    rightHeight = measureHeightWorker((Node) current.children.get(1), 0);
                }
                return Math.max(leftHeight, rightHeight) + 1;
            }
        }

        /*
         ***************************************************************************
         * Printing Methods
         ***************************************************************************
         */

        public String prettyPrint() {
            int height = this.getHeight();
            // use height to calculate the space between each of the nodes
            StringBuilder sb = new StringBuilder();
            switch (height) {
                case 0:
                    sb.append("(EMPTY)");
                    break;
                case 1:
                    printSingle(sb, root.data);
                    break;
                case 2:

            }


            // build string from bottom up
            return this.toString();
        }

        protected StringBuilder printAtom(StringBuilder sb, Node<T> parent) {
            sb.append("    ");
            printSingle(sb, parent.data).append("\n");

            if(parent.children.size() == 0) {
                return sb;
            }

            if(parent.children.get(0) == null) {

            }
            sb.append("    /   \\    ").append("\n");
            printSingle(sb, parent.children.get(0).data).append("   ");
            printSingle(sb, parent.children.get(1).data);

            return sb;
        }

        protected String printAtom(Node<T> parent) {
            StringBuilder sb = new StringBuilder();
            return printAtom(sb, parent).toString();
        }

        protected StringBuilder printSingle(StringBuilder sb, T val) {
            sb.append('(');
            switch (val.toString().length()) {
                case 0:
                    sb.append(" ! ");
                    break;
                case 1:
                    sb.append(' ').append(val.toString()).append(' ');
                    break;
                case 2:
                    sb.append(' ');
                case 3:
                    sb.append(val.toString());
                    break;
                default:
                    sb.append(val.toString().substring(0,2)).append('.');
                    break;
            }
            sb.append(')');
            return sb;
        }

        protected String printSingle(T val) {
            StringBuilder sb = new StringBuilder();
            return printSingle(sb, val).toString();
        }

        /*
         ***************************************************************************
         * Private Methods
         ***************************************************************************
         */

        /**
         * Recursive worker for adding a new value to the tree.
         */
        private Node<T> addWorker(Node<T> current, T data) {
            int result = current.data.compareTo(data);

            if(result > 0) {
                if(current.children.get(0) == null) {
                    values.add(data);
                    Node<T> newNode = newNode(data, current);
                    current.children.add(0, newNode);
                    return newNode;
                } else {
                    return addWorker(current.children.get(0), data);
                }
            } else if(result == 0) {
                return null;
            } else {
                if(current.children.get(1) == null) {
                    values.add(data);
                    Node<T> newNode = newNode(data, current);
                    current.children.add(1, newNode);
                    return newNode;
                } else {
                    return addWorker(current.children.get(1), data);
                }
            }
        }

        /**
         * Recursive worker for getting a node with a given value
         */
        private Node<T> getWorker(Node<T> current, T data) {
            if(current == null || data == null) {
                throw new IllegalArgumentException("Null value given to getWorker().");
            }

            int result = current.data.compareTo(data);

            if(result > 0) {
                if(current.children.get(0) == null) {
                    return null;
                } else {
                    return getWorker(current.children.get(0), data);
                }
            } else if(result == 0) {
                return current;
            } else {
                if(current.children.get(1) == null) {
                    return null;
                } else {
                    return getWorker(current.children.get(1), data);
                }
            }
        }

        /**
         * Creates a new node with children at a capacity of 2, filled with null.
         *
         * This is added so that the initial capacity isn't a potential issue.
         */
        private Node<T> newNode(T data, Node<T> current) {
            List<Node<T>> childrenNodes = new ArrayList<>(2);
            childrenNodes.add(null);
            childrenNodes.add(null);
            return new Node<T>(data, current, childrenNodes);
        }
    }
}
