import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TestSplayBST {

        @Test
        public void splayTree () {
                SplayBST<Integer> actual = new SplayBST<Integer>();
                SplayBST<Integer> control = new SplayBST<Integer>();

                actual.add(25);
                actual.add(30);
                actual.add(13);
                actual.add(20);
                actual.add(18);
                actual.add(15);
                actual.add(24);
                actual.add(12);
                actual.add(16);
                actual.add(5);
                control.add(25);
                control.add(30);
                control.add(13);
                control.add(20);
                control.add(18);
                control.add(15);
                control.add(24);
                control.add(12);
                control.add(16);
                control.add(5);
                actual.printTree();
                actual.remove(12);
                System.out.println("*");
                actual.printTree();
                actual.remove(25);
                System.out.println("*");
                actual.printTree();
                actual.add(40);
                System.out.println("*");
                actual.printTree();
                System.out.println(actual == control);
                //Assert.assertEquals(actual, control);
                //splayTree.remove(1);
        }

}
