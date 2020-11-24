import org.junit.jupiter.api.Test;
import java.util.AbstractSet;
import static org.junit.Assert.*;

class TestSplayBST {
        AbstractSet<Integer> actual = new SplayBST<>();
        SplayBST<Integer> control = new SplayBST<>();

        @Test
        void testAddAddWithSplitContainsFind() {
                actual = new SplayBST<>();
                control = new SplayBST<>();

                assertTrue(actual.add(5));
                assertTrue(actual.add(15));
                assertTrue(actual.add(1));
                assertTrue(actual.add(3));
                assertTrue(actual.add(10));
                assertTrue(actual.add(2));
                assertEquals(actual.size(), 6);
                assertFalse(actual.isEmpty());

                assertTrue(actual.contains(5));
                assertTrue(actual.contains(15));
                assertTrue(actual.contains(10));
                assertTrue(actual.contains(2));

                assertFalse(actual.contains(6));
                assertFalse(actual.contains(7));
                assertFalse(actual.contains(0));
                assertFalse(actual.contains(16));

                assertTrue(control.addWithSplit(1));
                assertTrue(control.addWithSplit(15));
                assertTrue(control.addWithSplit(3));
                assertTrue(control.addWithSplit(5));
                assertTrue(control.addWithSplit(10));
                assertTrue(control.addWithSplit(2));
                assertEquals(control.size(), 6);
                assertFalse(control.isEmpty());

                assertTrue(control.contains(5));
                assertTrue(control.contains(10));
                assertTrue(control.contains(2));
                assertTrue(control.contains(3));

                assertFalse(control.contains(6));
                assertFalse(control.contains(7));
                assertFalse(control.contains(0));
                assertFalse(control.contains(16));

                assertEquals(actual, control);
                assertTrue(control.contains(15));
                assertEquals(control.getRootValue(), 15);
                assertTrue(control.contains(2));
                assertEquals(control.getRootValue(), 2);
        }


        @Test
        void testRemoveRemoveWitSplit() {
                actual = new SplayBST<>();
                control = new SplayBST<>();

                assertTrue(actual.add(5));
                assertTrue(actual.add(15));
                assertTrue(actual.add(1));
                assertTrue(actual.add(3));
                assertTrue(actual.add(10));
                assertTrue(actual.add(2));

                assertTrue(control.addWithSplit(1));
                assertTrue(control.addWithSplit(15));
                assertTrue(control.addWithSplit(3));
                assertTrue(control.addWithSplit(5));
                assertTrue(control.addWithSplit(10));
                assertTrue(control.addWithSplit(2));

                assertEquals(actual, control);
                assertFalse(actual.remove(0));
                assertTrue(actual.contains(5));
                assertTrue(actual.remove(5));
                assertFalse(actual.remove(5));
                assertFalse(actual.contains(5));

                assertNotEquals(actual, control);
                assertTrue(actual.remove(1));
                assertFalse(actual.remove(1));
                assertTrue(actual.remove(15));
                assertFalse(actual.remove(15));
                assertEquals(actual.size(), 3);
                assertFalse(actual.isEmpty());

                assertTrue(actual.remove(2));
                assertTrue(actual.remove(3));
                assertTrue(actual.remove(10));
                assertTrue(actual.isEmpty());

                assertFalse(control.removeWithSplit(0));
                assertTrue(control.contains(5));
                assertTrue(control.removeWithSplit(5));
                assertFalse(control.removeWithSplit(5));
                assertFalse(control.contains(5));

                assertNotEquals(actual, control);
                assertTrue(control.removeWithSplit(1));
                assertFalse(control.removeWithSplit(1));
                assertTrue(control.removeWithSplit(15));
                assertFalse(control.removeWithSplit(15));
                assertEquals(control.size(), 3);
                assertFalse(control.isEmpty());

                assertTrue(control.remove(2));
                assertTrue(control.remove(3));
                assertTrue(control.remove(10));
                assertTrue(control.isEmpty());
                assertEquals(actual, control);
        }

        @Test
        void testSplitMergeFindMaxFindMinVisualization() {
                control = new SplayBST<>();

                control.add(10);
                control.add(1);
                control.add(15);
                control.add(5);
                control.add(2);
                control.add(3);
                control.printTree(control.root);

                control.root = control.findMax(control.root);
                System.out.println("fMax=" + control.root.value);
                System.out.println("control=");
                control.printTree(control.root);

                control.root = control.findMin(control.root);
                System.out.println("fMin=" + control.root.value);
                System.out.println("control=");
                control.printTree(control.root);


                SplayBST.Pair res = control.split(5);
                System.out.println("left side after split(5) =");
                control.printTree(res.left);
                System.out.println("right side after split(5) =");
                control.printTree(res.right);

                System.out.println("tree after merge =");
                control.merge(res.left, res.right);
                control.printTree(control.root);

        }
}
