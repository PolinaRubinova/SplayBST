import org.junit.jupiter.api.Test;

public class TestSplayBST {

        @Test
        public void splayTree() {
                SplayBST<Integer> actual = new SplayBST<Integer>();
                SplayBST<Integer> control = new SplayBST<Integer>();

                actual.add(15);
                actual.add(3);
                actual.add(2);
                actual.add(5);
                actual.add(10);
                actual.add(1);

                control.add(1);
                control.add(15);
                control.add(3);
                control.add(5);
                control.add(10);
                control.add(2);

                actual.printTree();
                control.printTree();
                System.out.println(actual == control);

                actual.remove(10);
                actual.printTree();
                System.out.println(actual == control);

                actual.remove(2);
                actual.printTree();
                System.out.println(actual == control);

                actual.add(11);
                actual.add(12);
                actual.printTree();
                System.out.println(actual == control);

                System.out.println(actual.contains(2));
                actual.printTree();
                control.printTree();
        }
}
