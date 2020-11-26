import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

class TestSplayBST {

        private AbstractSet<Integer> tree1 = new SplayBST<>();
        private SplayBST<Integer> tree2 = new SplayBST<>();

        TestSplayBST() throws IOException {
        }

        @Test
        void testAddAddWithSplitContainsFind() throws IOException {
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
        void testRemoveRemoveWitSplit() throws IOException {
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
        void testSplitMergeFindMaxFindMinVisualization() throws IOException {

                System.out.println("Результаты будут зафиксированны в файле output.txt");
                System.out.println("Для того чтобы ознакомиться именно с ними, запустите данный тест отдельно.");
                tree2 = new SplayBST<>();
                SplayBST.Pair res;

                assertTrue(tree2.add(11));
                assertTrue(tree2.add(1));
                assertTrue(tree2.add(15));
                assertTrue(tree2.add(5));
                assertTrue(tree2.add(2));
                assertTrue(tree2.add(6));
                assertTrue(tree2.add(10));
                assertTrue(tree2.add(13));
                assertTrue(tree2.add(25));
                assertTrue(tree2.add(4));
                assertTrue(tree2.add(21));
                assertTrue(tree2.add(7));

                tree2.writer.write("fMax = " + tree2.findMax(tree2.root).value + '\n');
                tree2.writer.write("fMin = " + tree2.findMin(tree2.root).value + '\n');
                tree2.writer.write("control:" + '\n');
                tree2.printTree(tree2.root);


                res = tree2.split(15);
                tree2.writer.write("left side after split(15) =" + '\n');
                tree2.printTree(res.left);
                tree2.writer.write("right side after split(15) =" + '\n');
                tree2.printTree(res.right);

                tree2.writer.write("tree after merge =" + '\n');
                tree2.merge(res.left, res.right);
                tree2.printTree(tree2.root);


                tree2.clear();

                assertTrue(tree2.add(11));
                assertTrue(tree2.add(13));
                assertTrue(tree2.add(25));
                assertTrue(tree2.add(4));
                assertTrue(tree2.add(10));
                assertTrue(tree2.add(1));
                assertTrue(tree2.add(15));
                assertTrue(tree2.add(5));
                assertTrue(tree2.add(2));
                assertTrue(tree2.add(6));
                assertTrue(tree2.add(21));

                tree2.writer.write("fMax = " + tree2.findMax(tree2.root).value + '\n');
                tree2.writer.write("fMin = " + tree2.findMin(tree2.root).value + '\n');
                tree2.writer.write("control:" + '\n');
                tree2.printTree(tree2.root);


        res = tree2.split(6);
                tree2.writer.write("left side after split(6) =" + '\n');
                tree2.printTree(res.left);
                tree2.writer.write("right side after split(6) =" + '\n');
                tree2.printTree(res.right);

                tree2.writer.write("tree after merge =" + '\n');
                tree2.merge(res.left, res.right);
                tree2.printTree(tree2.root);

                tree2.writer.close();
        }

        @Test
        void testIterator() throws IOException {

                System.out.println("Результаты будут зафиксированны в файле output.txt");
                System.out.println("Для того чтобы ознакомиться именно с ними, запустите данный тест отдельно.");
                tree1 = new SplayBST<>();
                tree2 = new SplayBST<>();

                assertTrue(tree1.add(5));
                assertTrue(tree1.add(2));
                assertTrue(tree1.add(10));
                assertTrue(tree1.add(1));
                assertTrue(tree1.add(3));
                assertTrue(tree1.add(15));
                tree2.writer.write("tree1:" + '\n');
                tree2.writer.write(String.valueOf(tree1) + '\n');
                tree2.writer.write('\n');

                assertTrue(tree2.add(1));
                assertTrue(tree2.add(15));
                assertTrue(tree2.add(3));
                assertTrue(tree2.add(5));
                assertTrue(tree2.add(10));
                assertTrue(tree2.add(2));
                tree2.writer.write("tree2:" + '\n');
                tree2.printTree(tree2.root);

                assertEquals(tree1.size(), tree2.size());

                Iterator<Integer> iterator1 = tree1.iterator();
                List<Integer> check1 = new ArrayList<>();
                Integer c1;
                Iterator<Integer> iterator2 = tree2.iterator();
                List<Integer> check2 = new ArrayList<>();
                Integer c2;

                tree2.writer.write('\n');
                tree2.writer.write("Итерация по tree2:" + '\n');
                while (iterator1.hasNext() && iterator2.hasNext()) {
                        c1 = iterator1.next();
                        c2 = iterator2.next();
                        tree2.writer.write("iterator1.next() = " + c1 + '\n');
                        tree2.writer.write("iterator2.next() = " + c2 + '\n');
                        check1.add(c1);
                        check2.add(c2);

                        tree2.printTree(tree2.root);
                }
                assertEquals(tree1, tree2);

                tree2.writer.write("check1 = " + check1 + '\n');
                tree2.writer.write("check2 = " + check2 + '\n');
                assertEquals(check1, check2);

                iterator2 = tree2.iterator();

                tree2.writer.write('\n');
                tree2.writer.write("Последовательное удаление элементов tree2:" + '\n');
                while (iterator2.hasNext()) {
                        tree2.writer.write("iterator2.next() = " + iterator2.next() + '\n');
                        iterator2.remove();
                        tree2.printTree(tree2.root);
                        tree2.writer.write('\n');
                        assertNotEquals(tree1, tree2);
                }

                tree2.writer.close();
        }
}
