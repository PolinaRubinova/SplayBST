import org.junit.jupiter.api.Test;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

class TestSplayBST {
        AbstractSet<Integer> tree1 = new SplayBST<>();
        SplayBST<Integer> tree2 = new SplayBST<>();

        @Test
        void testAddAddWithSplitContainsFind() {
                tree1 = new SplayBST<>();
                tree2 = new SplayBST<>();

                assertTrue(tree1.add(5));
                assertTrue(tree1.add(15));
                assertTrue(tree1.add(1));
                assertTrue(tree1.add(3));
                assertTrue(tree1.add(10));
                assertTrue(tree1.add(2));
                assertEquals(tree1.size(), 6);
                assertFalse(tree1.isEmpty());

                assertTrue(tree1.contains(5));
                assertTrue(tree1.contains(15));
                assertTrue(tree1.contains(10));
                assertTrue(tree1.contains(2));

                assertFalse(tree1.contains(6));
                assertFalse(tree1.contains(7));
                assertFalse(tree1.contains(0));
                assertFalse(tree1.contains(16));

                assertTrue(tree2.addWithSplit(1));
                assertTrue(tree2.addWithSplit(15));
                assertTrue(tree2.addWithSplit(3));
                assertTrue(tree2.addWithSplit(5));
                assertTrue(tree2.addWithSplit(10));
                assertTrue(tree2.addWithSplit(2));
                assertEquals(tree2.size(), 6);
                assertFalse(tree2.isEmpty());

                assertTrue(tree2.contains(5));
                assertTrue(tree2.contains(10));
                assertTrue(tree2.contains(2));
                assertTrue(tree2.contains(3));

                assertFalse(tree2.contains(6));
                assertFalse(tree2.contains(7));
                assertFalse(tree2.contains(0));
                assertFalse(tree2.contains(16));

                assertEquals(tree1, tree2);
                assertTrue(tree2.contains(15));
                assertEquals(tree2.getRootValue(), 15);
                assertTrue(tree2.contains(2));
                assertEquals(tree2.getRootValue(), 2);
        }


        @Test
        void testRemoveRemoveWitSplit() {
                tree1 = new SplayBST<>();
                tree2 = new SplayBST<>();

                assertTrue(tree1.add(5));
                assertTrue(tree1.add(15));
                assertTrue(tree1.add(1));
                assertTrue(tree1.add(3));
                assertTrue(tree1.add(10));
                assertTrue(tree1.add(2));

                assertTrue(tree2.addWithSplit(1));
                assertTrue(tree2.addWithSplit(15));
                assertTrue(tree2.addWithSplit(3));
                assertTrue(tree2.addWithSplit(5));
                assertTrue(tree2.addWithSplit(10));
                assertTrue(tree2.addWithSplit(2));

                assertEquals(tree1, tree2);
                assertFalse(tree1.remove(0));
                assertTrue(tree1.contains(5));
                assertTrue(tree1.remove(5));
                assertFalse(tree1.remove(5));
                assertFalse(tree1.contains(5));

                assertNotEquals(tree1, tree2);
                assertTrue(tree1.remove(1));
                assertFalse(tree1.remove(1));
                assertTrue(tree1.remove(15));
                assertFalse(tree1.remove(15));
                assertEquals(tree1.size(), 3);
                assertFalse(tree1.isEmpty());

                assertTrue(tree1.remove(2));
                assertTrue(tree1.remove(3));
                assertTrue(tree1.remove(10));
                assertTrue(tree1.isEmpty());

                assertFalse(tree2.removeWithSplit(0));
                assertTrue(tree2.contains(5));
                assertTrue(tree2.removeWithSplit(5));
                assertFalse(tree2.removeWithSplit(5));
                assertFalse(tree2.contains(5));

                assertNotEquals(tree1, tree2);
                assertTrue(tree2.removeWithSplit(1));
                assertFalse(tree2.removeWithSplit(1));
                assertTrue(tree2.removeWithSplit(15));
                assertFalse(tree2.removeWithSplit(15));
                assertEquals(tree2.size(), 3);
                assertFalse(tree2.isEmpty());

                assertTrue(tree2.remove(2));
                assertTrue(tree2.remove(3));
                assertTrue(tree2.remove(10));
                assertTrue(tree2.isEmpty());
                assertEquals(tree1, tree2);
        }

        @Test
        void testSplitMergeFindMaxFindMinVisualization() {
                tree2 = new SplayBST<>();
                SplayBST.Pair res = null;

                tree2.add(10);
                tree2.add(1);
                tree2.add(15);
                tree2.add(5);
                tree2.add(2);
                tree2.add(6);
                tree2.add(11);
                tree2.add(13);
                tree2.add(25);
                tree2.add(4);
                tree2.add(21);
                tree2.add(7);

                System.out.println("fMax=" + tree2.findMax(tree2.root));
                System.out.println("fMin=" + tree2.findMin(tree2.root));
                System.out.println("control=");
                tree2.printTree(tree2.root);


                res = tree2.split(15);
                System.out.println("left side after split(15) =");
                tree2.printTree(res.left);
                System.out.println("right side after split(15) =");
                tree2.printTree(res.right);

                System.out.println("tree after merge =");
                tree2.merge(res.left, res.right);
                tree2.printTree(tree2.root);


                tree2 = new SplayBST<>();

                tree2.add(11);
                tree2.add(13);
                tree2.add(25);
                tree2.add(4);
                tree2.add(10);
                tree2.add(1);
                tree2.add(15);
                tree2.add(5);
                tree2.add(2);
                tree2.add(6);

                System.out.println("fMax=" + tree2.findMax(tree2.root));
                System.out.println("fMin=" + tree2.findMin(tree2.root));
                System.out.println("control=");
                tree2.printTree(tree2.root);


                res = tree2.split(4);
                System.out.println("left side after split(4) =");
                tree2.printTree(res.left);
                System.out.println("right side after split(4) =");
                tree2.printTree(res.right);

                System.out.println("tree after merge =");
                tree2.merge(res.left, res.right);
                tree2.printTree(tree2.root);
        }

        @Test
        void testIterator() {
                tree1 = new SplayBST<>();
                tree2 = new SplayBST<>();

                assertTrue(tree1.add(5));
                assertTrue(tree1.add(2));
                assertTrue(tree1.add(10));
                assertTrue(tree1.add(1));
                assertTrue(tree1.add(3));
                assertTrue(tree1.add(15));

                assertTrue(tree2.add(1));
                assertTrue(tree2.add(15));
                assertTrue(tree2.add(3));
                assertTrue(tree2.add(5));
                assertTrue(tree2.add(10));
                assertTrue(tree2.add(2));
                tree2.printTree(tree2.root);

                Iterator<Integer> iterator1 = tree1.iterator();
                List<Integer> check1 = new ArrayList<>();
                Iterator<Integer> iterator2 = tree2.iterator();
                List<Integer> check2 = new ArrayList<>();

                while (iterator1.hasNext()) {
                        check1.add(iterator1.next());
                        check2.add(iterator2.next());
                }
                assertEquals(tree1, tree2);
                assertArrayEquals(check1.toArray(), check2.toArray());

                iterator2 = tree2.iterator();

                while (iterator2.hasNext()) {
                        iterator2.next();
                        iterator2.remove();
                        tree2.printTree(tree2.root);
                        assertNotEquals(tree1, tree2);
                }

        }
}
