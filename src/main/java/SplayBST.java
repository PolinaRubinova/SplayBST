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

    // Рекурсивно находим размер дерева

    private int size(Node<T> node) {
        if (node == null) return 0;
        return 1 + size(node.left) + size(node.right);
    }

    // С помощью функции find находим элемент,
    // если он был в дереве, то, из-за функции splay,
    // он стал корнем. Поэтому, если find вернул что угодно
    // (null или другой элемент), кроме нашего элемента -> false

    public  boolean contains(T element) {
        return find(element) != null &&
                Objects.requireNonNull(find(element)).value == element;
    }


    // Эта функция возвращает новый корень Splay Tree
    // Если элемент присутствует в дереве, он перемещается в корень.

    private Node<T> find(T value) {
        if (root == null) return null;
        return root = splay(root, value);
    }


    // Добавление элемента в дерево

    // Если элемента нет в множестве, функция добавляет его в дерево и возвращает true.
    // В ином случае функция оставляет множество нетронутым и возвращает false.

    @Override
    public boolean add(T element) {

        if (root == null) {
            root = new Node<>(element);
            return true;
        }

        root = splay(root, element);
        if (root.value == element) return false;

        int comparison = element.compareTo(root.value);
        Node<T> newNode = new Node<>(element);

        // Если элемент меньше значения корня, то корень
        // становится правым потомком нового узла,
        // а левый потомок корня становится левым потомком
        // нового узла
        if (comparison < 0) {
            newNode.right = root;
            newNode.left = root.left;
            root.left = null;
        }

        // Если элемент больше значения корня, то корень
        // становится левым потомком нового узла,
        // а правый потомок корня становится правым потомком
        // нового узла
        else {
            newNode.left = root;
            newNode.right = root.right;
            root.right = null;
        }

        // Новый узел становится корнем
        root = newNode;

        return true;
    }

    // Функция удаления элемента из дерева

    @Override
    public boolean remove(Object object) {
        T element = (T) object;
        if (root == null) return false;

        root = splay(root, element);
        if (root.value != element) return false;

        root.left = splay(root.left, element);

        // Если левого потомка нет,
        // корнем становится правый потомок
        if (root.left == null) {
            root = root.right;
        }

        // Если левый потомок существует
        // правый потомок корня становится правым
        // потомком левого потомка корня,
        // а левый потомок становится корнем
        else {
            root.left.right = root.right;
            root = root.left;
        }
        return true;
    }


    // Эта функция помещает ключ в корень, если элемент
    // присутствует в дереве. Если ключ отсутствует,
    // он переносит последний доступный элемент в корневой каталог.
    // Эта функция изменяет дерево и возвращает новый корень.

    private Node<T> splay(Node<T> node, T element) {
        if (node == null || node.value == element) return node;

        //Если ключ находится в левом поддереве
        int cmp1 = element.compareTo(node.value);
        if (cmp1 < 0) {
            // Если ключ не в дереве -> мы закончили
            if (node.left == null) return node;

            // Zig-Zig (Left Left)
            int cmp2 = element.compareTo(node.left.value);
            if (cmp2 < 0) {
                // Сначала рекурсивно приведем ключ как корень left-left
                node.left.left = splay(node.left.left, element);
                // Сделаем первое вращение для корня,
                // второе вращение выполняется после else
                node = rotateRight(node);
            }

            // Zig-Zag (Left Right)
            else if (cmp2 > 0) {
                // Сначала рекурсивно приведем ключ как корень left-right
                node.left.right = splay(node.left.right, element);
                // Сделаем первое вращение для root.left
                if (node.left.right != null)
                    node.left = rotateLeft(node.left);
            }

            // Сделаем второе вращение для корня
            return  (node.left == null) ? node : rotateRight(node);
        }

        //Если ключ находится в правом поддереве
        else {
            // Если ключ не в дереве -> мы закончили
            if (node.right == null) return node;

            // Zag-Zig (Right Left)
            int cmp2 = element.compareTo(node.right.value);
            if (cmp2 < 0) {
                // Приведем ключ как корень right-left
                node.right.left  = splay(node.right.left, element);
                // Сделаем первое вращение для root.right
                if (node.right.left != null)
                    node.right = rotateRight(node.right);
            }

            // Zag-Zag (Right Right)
            else if (cmp2 > 0) {
                // Приведем ключ как корень right-right
                // и сделаем первое вращение
                node.right.right = splay(node.right.right, element);
                node = rotateLeft(node);
            }

            // Сделаем второе вращение для корня
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

    // Правое вращение подерева с корнем y
    private Node<T> rotateRight(Node<T> x) {
        Node<T> y = x.left;
        x.left = y.right;
        y.right = x;
        return y;
    }

    // Левое вращение подерева с корнем x
    private Node<T> rotateLeft(Node<T> x) {
        Node<T> y = x.right;
        x.right = y.left;
        y.left = x;
        return y;
    }


    // Визуализация
    public void printTree() {
        int maxLevel = maxLevel(root);
        printTree(Collections.singletonList(root), 1, maxLevel);
    }

    private static <T extends Comparable<?>> void printTree(List<Node<T>> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || isAllElementsNull(nodes)) return;

        int floor = maxLevel - level;
        int lines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        printWhiteSpaces(firstSpaces);
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
            printWhiteSpaces(betweenSpaces);
        }
        System.out.println();

        for (int i = 1; i <= lines; i++) {
            for (Node<T> node : nodes) {
                printWhiteSpaces(firstSpaces - i);
                if (node == null) {
                    printWhiteSpaces(lines + lines + i + 1);
                    continue;
                }

                if (node.left != null) {
                    System.out.print("/");
                } else printWhiteSpaces(1);

                printWhiteSpaces(i + i - 1);

                if (node.right != null) {
                    System.out.print("\\");
                } else printWhiteSpaces(1);

                printWhiteSpaces(lines + lines - i);
            }
            System.out.println();
        }
        printTree(newNodes, level + 1, maxLevel);
    }

    // Печатаем пробелы
    private static void printWhiteSpaces(int count) {
        for (int i = 0; i < count; i++) System.out.print(" ");
    }

    private static <T extends Comparable<?>> int maxLevel(Node<T> node) {
        return (node == null) ? 0 :
                Math.max(maxLevel(node.left), maxLevel(node.right)) + 1;
    }

    // Проверяем, все ли элементы равны null
    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }
        return true;
    }
}