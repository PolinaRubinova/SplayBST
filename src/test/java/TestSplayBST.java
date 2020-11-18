import org.junit.jupiter.api.Test;

class TestSplayBST {

        @Test
        void splayTree() {
                SplayBST<Integer> actual = new SplayBST<>();
                SplayBST<Integer> control = new SplayBST<>();

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

                System.out.println("actual:");
                actual.printTree();
                System.out.println("control:");
                control.printTree();
                System.out.println(actual == control);

                System.out.println("actual.contains(10) = " + actual.contains(10));
                System.out.println("actual:");
                actual.printTree();
                System.out.println("control:");
                control.printTree();
                System.out.println("actual == control = " + (actual == control));

                actual.remove(5);
                actual.printTree();
                System.out.println("actual == control = " + (actual == control));

                actual.remove(10);
                System.out.println("actual:");
                actual.printTree();
                System.out.println("actual == control = " + (actual == control));

                actual.add(11);
                actual.add(12);
                System.out.println("actual:");
                actual.printTree();
                System.out.println("actual == control = " + (actual == control));

                System.out.println(actual.contains(2));
                System.out.println("actual:");
                actual.printTree();
                System.out.println("control:");
                control.printTree();
                System.out.println("control.size() = " + control.size());
                System.out.println("actual == control = " + (actual == control));

                System.out.println(actual.contains(12));
                System.out.println("actual:");
                actual.printTree();
                System.out.println("actual.size() = " + actual.size());
        }
}
