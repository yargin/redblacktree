package ru.spbstu.redblacktree.tree;

import ru.spbstu.redblacktree.tree.binarytree.search.BinarySearchTree;
import ru.spbstu.redblacktree.tree.binarytree.search.balanced.redblacktree.RedBlackTree;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static ru.spbstu.redblacktree.tree.utils.Assert.assertEquals;
import static ru.spbstu.redblacktree.utils.CollectionUtils.transformIntoNew;

/**
 * provides tests for {@link RedBlackTree}
 */
public class RedBlackTreeTest {
    private static final String TEST_CLASS_NAME = "RedBlackTreeTest";
    private static String testName;

    public static void main(String[] args) {
        testAdd();
        testRemove();
    }

    private static void testAdd() {
        testName = TEST_CLASS_NAME + ".testAdd()";
        BinarySearchTree<Integer> integers = new RedBlackTree<>();
        integers.add(1);
        assertEquals(testName + " - root", singletonList(1), transformIntoNew(integers.nodes(), Node::getElement));
        integers.add(2);
        assertEquals(testName + " - red", asList(1, 2), transformIntoNew(integers.nodes(), Node::getElement));
        integers.add(3);
        assertEquals(testName + " - reduceSubintegers", asList(2, 1, 3), transformIntoNew(integers.nodes(), Node::
                getElement));
        integers.add(4);
        integers.add(5);
        assertEquals(testName + " - reduceSubintegers", asList(2, 1, 4, 3, 5), transformIntoNew(integers.nodes(), Node::
                getElement));
        integers.add(6);
        assertEquals(testName + " - makeBlack(uncle)", asList(2, 1, 4, 3, 5, 6), transformIntoNew(integers.nodes(),
                Node::getElement));
        integers.add(7);
        integers.add(8);
        assertEquals(testName + " - push double null up", asList(4, 2, 6, 1, 3, 5, 7, 8), transformIntoNew(integers.
                nodes(), Node::getElement));
    }

    private static void testRemove() {
        testName = TEST_CLASS_NAME + ".testRemove()";
        BinarySearchTree<Integer> integers = new RedBlackTree<>();
        assertEquals(testName + " remove red", null, integers.remove(99));
        Node<Integer> one = integers.add(1);
        Node<Integer> two = integers.add(2);
        Node<Integer> three = integers.add(3);
        integers.add(4);
        integers.add(5);
        Node<Integer> six = integers.add(6);
        Node<Integer> seven = integers.add(7);
        Node<Integer> eight = integers.add(8);
        integers.remove(eight);
        assertEquals(testName + " black with one red child", asList(4, 2, 6, 1, 3, 5, 7), transformIntoNew(integers.
                nodes(), Node::getElement));
        integers.remove(integers.root());
        assertEquals(testName + " root", asList(5, 2, 6, 1, 3, 7), transformIntoNew(integers.nodes(), Node::getElement));
        integers.remove(one);
        assertEquals(testName + " case 4", asList(5, 2, 6, 3, 7), transformIntoNew(integers.nodes(), Node::getElement));
        integers.remove(three);
        integers.add(8);
        Node<Integer> nine = integers.add(9);
        integers.remove(nine);
        assertEquals(testName + " case 2", asList(5, 2, 7, 6, 8), transformIntoNew(integers.nodes(), Node::getElement));
        nine = integers.add(9);
        integers.remove(six);
        assertEquals(testName + " case 6", asList(5, 2, 8, 7, 9), transformIntoNew(integers.nodes(), Node::getElement));
        integers.remove(nine);
        assertEquals(testName + " case 5", asList(5, 2, 8, 7), transformIntoNew(integers.nodes(), Node::getElement));
        integers.remove(seven);
        integers.remove(two);
        assertEquals(testName + " case 3", asList(5, 8), transformIntoNew(integers.nodes(), Node::getElement));
    }
}
