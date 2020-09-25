import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class GraphTest {
    public static void main(String[] args) {

    }

    @Test
    public void heightTest() {
         /*
         |           ( 5 )
         |        /         \
         |    ( 2 )         ( 8 )
         |    /   \         /   \
         |( 1 )   ( 4 ) ( 7 )   ( 9 )
         */
        Graph.BST<Integer> tree = new Graph.BST<>(5, 2, 8, 1, 4, 7, 9);
        Assertions.assertEquals(3, tree.getHeight());

        tree = new Graph.BST<>(5, 2, 8);
        Assertions.assertEquals(2, tree.getHeight());

        tree = new Graph.BST<>(5);
        Assertions.assertEquals(1, tree.getHeight());

        tree = new Graph.BST<>(1, 2);
        Assertions.assertEquals(2, tree.getHeight());

        tree = new Graph.BST<>(1, 2, 3, 4, 5);
        Assertions.assertEquals(5, tree.getHeight());
    }

    @Test
    public void addTest() {
        Graph.BST<Integer> tree = new Graph.BST<>();
        int first = 1;
        Assertions.assertEquals(first, tree.add(first).data);
        Assertions.assertEquals(first, tree.values.size());
        int second = 2;
        Assertions.assertEquals(second, tree.add(second).data);
        Assertions.assertEquals(second, tree.values.size());
        int third = 3;
        Assertions.assertEquals(third, tree.add(third).data);
        Assertions.assertEquals(third, tree.values.size());
    }

    @Test
    public void removeTest() {
        Set<Integer> expectedValues = new HashSet<>();
        for (int i = 1; i <= 6; i++) {
            expectedValues.add(i);
        }
        Graph.BST<Integer> tree = new Graph.BST<Integer>(List.copyOf(expectedValues));
        assertTreeValues(expectedValues, tree.values);

        Assertions.assertTrue(tree.remove(6));
        expectedValues.remove(6);
        assertTreeValues(expectedValues, tree.values);

        Assertions.assertTrue(tree.remove(3));
        expectedValues.remove(3);
        assertTreeValues(expectedValues, tree.values);

        Assertions.assertTrue(tree.remove(1));
        expectedValues.remove(1);
        assertTreeValues(expectedValues, tree.values);
    }

    private void assertTreeValues(Collection<Integer> expectedValues, Set<Integer> treeVals) {
        Assertions.assertEquals(expectedValues.size(), treeVals.size());
        expectedValues.forEach(val -> {
            Assertions.assertTrue(treeVals.contains(val));
        });
    }

    @Test
    public void getTest() {
        Set<Integer> expectedValues = new HashSet<>();
        for (int i = 1; i <= 6; i++) {
            expectedValues.add(i);
        }
        Graph.BST<Integer> tree = new Graph.BST<Integer>(List.copyOf(expectedValues));
        assertTreeValues(expectedValues, tree.values);

        Assertions.assertEquals(4, tree.get(4).data);
        Assertions.assertEquals(6, tree.get(6).data);
        Assertions.assertEquals(1, tree.get(1).data);
        Assertions.assertEquals(null, tree.get(10));
    }

    @Test
    public void printSingleTest() {
        Graph.BST<String> tree = new Graph.BST<>();
        Assertions.assertEquals("( ! )", tree.printSingle(""));
        Assertions.assertEquals("( 1 )", tree.printSingle("1"));
        Assertions.assertEquals("( 11)", tree.printSingle("11"));
        Assertions.assertEquals("(111)", tree.printSingle("111"));
        Assertions.assertEquals("(11.)", tree.printSingle("11111"));
    }

    @Test
    public void printAtomTest() {
        /*
         |           ( 5 )
         |        /         \
         |    ( 2 )         ( 8 )
         |    /   \         /   \
         |( 1 )         ( 7 )
         */
        Graph.BST<Integer> tree = new Graph.BST<>();
        tree.add(5);
        tree.add(2);
        tree.add(8);

        Graph.Node<Integer> node = tree.get(5);
        System.out.println(tree.printAtom(tree.root));
        System.out.println("\n\n");
        System.out.println(tree.printAtom(tree.root.children.get(0)));
        System.out.println("\n\n");
        System.out.println(tree.printAtom(tree.root.children.get(1)));
    }
}
