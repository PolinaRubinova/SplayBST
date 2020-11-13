import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SplayBST<T extends Comparable<T>> extends AbstractSet<T> {

    private static class Node<T> {
        T value;
        Node<T> left = null;
        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node<T> node) {
        if (node == null) return 0;
        else return 1 + size(node.left) + size(node.right);
    }

    private boolean contains(T element) {
        return find(element) != null;
    }


    private Node<T> find(T value) {
        if (root == null) return null;

        root = splay(root, value);

        int comparison = value.compareTo(root.value);
        if (comparison == 0) {
            return root;
        }
        return null;
    }


    @Override
    public boolean add(T t) {

        if (root == null) {
            root = new Node<>(t);
            return true;
        }

        root = splay(root, t);

        int comparison = t.compareTo(root.value);
        Node<T> newNode = new Node<>(t);

        if (comparison < 0) {
            newNode.left = root.left;
            newNode.right = root;
            root.left = null;
        }

        else if (comparison > 0) {
            newNode.left = root;
            newNode.right = root.right;
            root.right = null;
        }
        root = newNode;

        return true;
    }

    @Override
    public boolean remove(Object object) {
        T element = (T) object;
        if (root == null) return false;

        root = splay(root, element);

        int comparison = element.compareTo(root.value);

        if (comparison == 0) {
            if (root.left == null) {
                root = root.right;
            } else {
                Node<T> x = root.right;
                root = splay(root.left, element);
                root.right = x;
            }
        }
        return true;
    }



    private Node<T> splay(Node<T> node, T element) {
        if (node == null || node.value == element) return null;

        int cmp1 = element.compareTo(node.value);

        if (cmp1 < 0) {
            if (node.left == null) return node;

            int cmp2 = element.compareTo(node.left.value);
            if (cmp2 < 0) {
                node.left.left = splay(node.left.left, element);
                node = rotateRight(node);
            }
            else if (cmp2 > 0) {
                node.left.right = splay(node.left.right, element);
                if (node.left.right != null)
                    node.left = rotateLeft(node.left);
            }

            return  (node.left == null) ? node : rotateRight(node);
        }

        else {
            if (node.right == null) return node;

            int cmp2 = element.compareTo(node.right.value);
            if (cmp2 < 0) {
                node.right.left  = splay(node.right.left, element);
                if (node.right.left != null)
                    node.right = rotateRight(node.right);
            }
            else if (cmp2 > 0) {
                node.right.right = splay(node.right.right, element);
                node = rotateLeft(node);
            }

            return (node.right == null) ? node : rotateLeft(node);
        }
    }


    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new SplayBSTIterator();
    }

    public class SplayBSTIterator implements Iterator<T> { //***********************************************************

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public T next() {
            return null;
        }

        @Override
        public void remove() {

        }
    }


    public int height() { return height(root); }
    private int height(Node<T> x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }


    private Node<T> rotateRight(Node<T> h) {
        Node<T> x = h.left;
        h.left = x.right;
        x.right = h;
        return x;
    }

    private Node<T> rotateLeft(Node<T> h) {
        Node<T> x = h.right;
        h.right = x.left;
        x.left = h;
        return x;
    }

    public void printTree() {
        int maxLevel = maxLevel(root);
        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static <T extends Comparable<?>> void printNodeInternal(List<Node<T>> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || isAllElementsNull(nodes)) return;

        int floor = maxLevel - level;
        int lines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        printWhitespaces(firstSpaces);
        ArrayList<Node<T>> newNodes = new ArrayList<>();

        for (Node<T> node : nodes) {
            if (node != null) {
                System.out.print(node.value);
                newNodes.add(node.left);
                newNodes.add(node.right);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }
            printWhitespaces(betweenSpaces);
        }
        System.out.println();

        for (int i = 1; i <= lines; i++) {
            for (Node<T> node : nodes) {
                printWhitespaces(firstSpaces - i);
                if (node == null) {
                    printWhitespaces(lines + lines + i + 1);
                    continue;
                }

                if (node.left != null) {
                    System.out.print("/");
                } else printWhitespaces(1);

                printWhitespaces(i + i - 1);

                if (node.right != null) {
                    System.out.print("\\");
                } else printWhitespaces(1);

                printWhitespaces(lines + lines - i);
            }
            System.out.println();
        }
        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++) System.out.print(" ");
    }

    private static <T extends Comparable<?>> int maxLevel(Node<T> node) {
        return (node == null) ? 0 :
                Math.max(maxLevel(node.left), maxLevel(node.right)) + 1;
    }

    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }
        return true;
    }
}