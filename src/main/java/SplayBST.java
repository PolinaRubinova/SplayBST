import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SplayBST<T extends Comparable<T>> extends AbstractSet<T> {

    public SplayBST() throws IOException {
    }

    public static class Node<T> {
        T value;
        Node<T> left = null;
        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    public Node<T> root = null;

    public Object getRootValue() {
        return root.value;
    }

    @Override
    public int size() {
        return size(root);
    }

    @Override
    public boolean isEmpty() {
        return root == null;
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

    @Override
    public boolean contains(Object object) {
        @SuppressWarnings("unchecked")
        T element = (T) object;
        return find(element) != null &&
                Objects.requireNonNull(find(element)).value == object;
    }

    // Эта функция возвращает новый корень Splay Tree
    // Если элемент присутствует в дереве, он перемещается в корень.

    public Node<T> find(T element) {
        if (root == null) return null;
        return root = splay(root, find(root, element).value);
    }

    // Эта операция выполняется как для обычного
    // бинарного дерева поиска, только после нее
    // запускается операция splay.

    private Node<T> find(Node<T> start, T element){
        int comparison = element.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, element);
        } else {
            if (start.right == null) return start;
            return find(start.right, element);
        }
    }


    public class Pair {
        public Node<T> left;
        public Node<T> right;

        Pair(Node<T> left, Node<T> right) {
            this.left = left;
            this.right = right;
        }
    }

    // Возвращаем два дерева, полученные отсечением правого или левого
    // поддерева от корня, в зависимости от того, содержит корень
    // элемент больше или не больше, чем element.
    public Pair split (T element) {
        if (root == null) return new Pair(null, null);

        Node<T> left = null;
        Node<T> right = null;

        root = splay(root, element);
        int comparison = element.compareTo(root.value);

        if (comparison > 0) {
            if (root.right != null) right = root.right;
            root.right = null;
            left = root;

        } else {
            if (root.left != null) left = root.left;
            root.left = null;
            right = root;
        }

        return new Pair(left, right);
    }

    // Находим максимальный элемент
    public Node<T> findMax(Node<T> start) {
        Node<T> max = start;
        if (start.right != null) {
            Node<T> mbMax = findMax(start.right);
            if (max.value.compareTo(mbMax.value) < 0) max = mbMax;
        }
        if (start.left != null) {
            Node<T> mbMax = findMax(start.left);
            if (max.value.compareTo(mbMax.value) < 0) max = mbMax;
        }
        return max;
    }

    // Находим минимальный элемент
    public Node<T> findMin(Node<T> start) {
        Node<T> min = start;
        if (start.left != null) {
            Node<T> mbMin = findMin(start.left);
            if (min.value.compareTo(mbMin.value) > 0) min = mbMin;
        }
        if (start.right != null) {
            Node<T> mbMin = findMin(start.right);
            if (min.value.compareTo(mbMin.value) > 0) min = mbMin;
        }
        return min;
    }

    // Для слияния деревьев leftTree и rightTree, в которых все ключи
    // leftTree меньше ключей в rightTree, запускаем splay от
    // самого большого элемента в дереве tree1 (функция findMax).

    // После этого корень tree1 содержит максимальный элемент,
    // при этом у него нет правого ребёнка.
    // Делаем tree2 правым поддеревом и возвращаем полученное дерево.

    public Node<T> merge (Node<T> left, Node<T> right) {
        if (left == null && right == null) return null;
        else if (left == null) return right;
        else if (right == null) return left;

        left = splay(left, findMax(left).value);
        right = splay(right, findMin(right).value);
        left.right = right;
        root = left;
        return root;
    }


    // Добавление элемента в дерево

    // Если элемента нет в дереве, функция добавляет его в
    // дерево и возвращает true. В ином случае функция оставляет
    // дерево нетронутым и возвращает false.

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

    // Запускаем split(element), который нам возвращает
    // деревья treeLeft и treeRight, их подвешиваем к element
    // как левое и правое поддеревья соответственно.

    public boolean addWithSplit(T element) {
        if (root == null) {
            root = new Node<>(element);
            return true;
        }
        if (root.value == element) return false;

        Pair splitRes = split(element);
        root = new Node<>(element);

        if (splitRes.left != null) root.left = splitRes.left;
        if (splitRes.right != null) root.right = splitRes.right;
        return true;
    }


    // Функция удаления элемента из дерева

    @Override
    public boolean remove(Object object) {
        @SuppressWarnings("unchecked")
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

        // Если левый потомок существует,
        // правый потомок корня становится правым
        // потомком левого потомка корня,
        // а левый потомок становится корнем
        else {
            root.left.right = root.right;
            root = root.left;
        }
        return true;
    }


    // Находим элемент в дереве, делаем Splay для него,
    // делаем текущим деревом Merge его детей.

    public boolean removeWithSplit(T element) {
        if (root == null) return false;

        root = splay(root, element);
        if (root.value != element) return false;

        root = merge(root.left, root.right);
        return true;
    }


    // Эта функция помещает элемент в корень, если элемент
    // присутствует в дереве. Если элемент отсутствует,
    // он переносит последний доступный элемент в корневой каталог.
    // Эта функция изменяет дерево и возвращает новый корень.

    // T = O(log2N), где N — число элементов в дереве.

    private Node<T> splay(Node<T> node, T element) {
        if (node == null || node.value == element) return node;

        //Если элемент находится в левом поддереве
        int cmp1 = element.compareTo(node.value);
        if (cmp1 < 0) {
            // Если элемент не в дереве -> мы закончили
            if (node.left == null) return node;

            // Zig-Zig (Left Left)
            int cmp2 = element.compareTo(node.left.value);
            if (cmp2 < 0) {
                // Сначала рекурсивно приведем элемент как корень left-left
                node.left.left = splay(node.left.left, element);
                // Сделаем первое вращение для корня,
                // второе вращение выполняется после else
                node = rotateRight(node);
            }

            // Zig-Zag (Left Right)
            else if (cmp2 > 0) {
                // Сначала рекурсивно приведем элемент как корень left-right
                node.left.right = splay(node.left.right, element);
                // Сделаем первое вращение для root.left
                if (node.left.right != null)
                    node.left = rotateLeft(node.left);
            }

            // Сделаем второе вращение для корня
            return  (node.left == null) ? node : rotateRight(node);
        }

        //Если элемент находится в правом поддереве
        else {
            // Если элемент не в дереве -> мы закончили
            if (node.right == null) return node;

            // Zag-Zig (Right Left)
            int cmp2 = element.compareTo(node.right.value);
            if (cmp2 < 0) {
                // Приведем элемент как корень right-left
                node.right.left  = splay(node.right.left, element);
                // Сделаем первое вращение для root.right
                if (node.right.left != null)
                    node.right = rotateRight(node.right);
            }

            // Zag-Zag (Right Right)
            else if (cmp2 > 0) {
                // Приведем элемент как корень right-right
                // и сделаем первое вращение
                node.right.right = splay(node.right.right, element);
                node = rotateLeft(node);
            }

            // Сделаем второе вращение для корня
            return (node.right == null) ? node : rotateLeft(node);
        }
    }

    // Правое вращение поддерева с корнем y
    private Node<T> rotateRight(Node<T> x) {
        Node<T> y = x.left;
        x.left = y.right;
        y.right = x;
        return y;
    }

    // Левое вращение поддерева с корнем x
    private Node<T> rotateLeft(Node<T> x) {
        Node<T> y = x.right;
        x.right = y.left;
        y.left = x;
        return y;
    }


    // Итератор как для бинарного дерева поиска.

    @Override
    public Iterator<T> iterator() {
        return new SplayBSTIterator();
    }

    public class SplayBSTIterator implements Iterator<T> {

        private final Stack<Node<T>> stack = new Stack<>();
        private Node<T> current = new Node<>(null);

        // Инициализируем, делая наименьший элемент корнем дерева
        private SplayBSTIterator() {
            if (root != null) root = splay(root, findMin(root).value);
            inOrderIterator(root);
        }

        // Из текущего узла «спускаемся» до самого нижнего
        // левого узла, добавляя в стек все посещенные узлы.
        private void inOrderIterator(Node<T> node) {
            Node<T> n = node;
            while (n != null) {
                stack.push(n);
                n = n.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        // С помощью операции splay делаем узел из стека новым корнем

        // Если в текущем узле имеется правое поддерево,
        // начинаем следующую итерацию с правого узла.
        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();

            Node<T> node = stack.pop();
            current = node;
            inOrderIterator(node.right);
            root = splay(root, node.value);
            return node.value;
        }

        // Функция удаляет из множества элемент,
        // возвращённый крайним вызовом функции next().
        @Override
        public void remove() {
            if (current == null) throw new IllegalStateException();
            SplayBST.this.remove(current.value);
            current.value = null;
        }
    }


    public FileWriter writer = new FileWriter("src/test/java/output.txt");

    // Визуализация
    public void printTree(Node<T> n) throws IOException {
        int maxLevel = maxLevel(n);
        printTree(Collections.singletonList(n), 1, maxLevel, writer);
    }

    private static <T extends Comparable<?>> void printTree(List<Node<T>> nodes, int level, int maxLevel, FileWriter writer) throws IOException {
        if (nodes.isEmpty() || isAllElementsNull(nodes)) return;


        int floor = maxLevel - level;
        int lines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        printWhiteSpaces(firstSpaces, writer);
        ArrayList<Node<T>> newNodes = new ArrayList<>();

        for (Node<T> node : nodes) {
            if (node != null) {
                writer.write(node.value.toString());
                newNodes.add(node.left);
                newNodes.add(node.right);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                writer.write(" ");
            }
            printWhiteSpaces(betweenSpaces, writer);
        }
        writer.write('\n');

        for (int i = 1; i <= lines; i++) {
            for (Node<T> node : nodes) {
                printWhiteSpaces(firstSpaces - i, writer);
                if (node == null) {
                    printWhiteSpaces(lines + lines + i + 1, writer);
                    continue;
                }

                if (node.left != null) {
                    writer.write("/");
                } else printWhiteSpaces(1, writer);

                printWhiteSpaces(i + i - 1, writer);

                if (node.right != null) {
                    writer.write("\\");
                } else printWhiteSpaces(1, writer);

                printWhiteSpaces(lines + lines - i, writer);
            }
            writer.write('\n');
        }
        printTree(newNodes, level + 1, maxLevel, writer);
    }

    // Печатаем пробелы
    private static void printWhiteSpaces(int count, FileWriter writer) throws IOException {
        for (int i = 0; i < count; i++) writer.write(" ");
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