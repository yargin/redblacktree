package ru.spbstu.redblacktree.tree;

import ru.spbstu.redblacktree.tree.binarytree.AbstractBinaryTree;
import ru.spbstu.redblacktree.tree.binarytree.LinkedBinaryTree;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static ru.spbstu.redblacktree.tree.utils.Assert.assertEquals;
import static ru.spbstu.redblacktree.tree.utils.Assert.fail;
import static ru.spbstu.redblacktree.utils.CollectionUtils.transformIntoNew;

/**
 * provides tests for {@link AbstractBinaryTree}
 */
public class AbstractBinaryTreeTest {
    private static final String TEST_CLASS_NAME = "AbstractBinaryTreeTest";
    private static final String EXCEPTION_STR = " (exception)";
    private static final String FAILED = "failed";
    private static String testName;

    public static void main(String[] args) {
        testAdd();
        testAddLeft();
        testAddRight();
        testAddRoot();
        testSet();
        testRemove();
        testSize();
        testChildrenNumber();
        testChildren();
        testPreOrder();
        testInOrder();
        testPostOrder();
        testBreadthFirst();
    }

    private static void testAdd() {
        testName = TEST_CLASS_NAME + ".testAdd()";
        AbstractBinaryTree<Integer> tree = new LinkedBinaryTree<>();
        Node<Integer> one = tree.addRoot(1);
        Node<Integer> two = tree.add(one, 2);
        Node<Integer> three = tree.add(one, 3);
        assertEquals(testName, asList(1, 2, 3), transformIntoNew(tree.nodes(), Node::getElement));
        tree.add(two, 4);
        assertEquals(testName, 5, tree.add(two, 5).getElement());
        tree.add(three, 6);
        tree.add(three, 7);
        assertEquals(testName, asList(1, 2, 3, 4, 5, 6, 7), transformIntoNew(tree.nodes(), Node::getElement));
        try {
            tree.add(three, 8);
            fail(FAILED);
        } catch (IllegalArgumentException e) {
            assertEquals(testName + EXCEPTION_STR, "node already has two siblings", e.getMessage());
        }
        try {
            tree.add(null, 8);
            fail(FAILED);
        } catch (IllegalArgumentException e) {
            assertEquals(testName + EXCEPTION_STR, "node can't be null", e.getMessage());
        }
    }

    private static void testAddLeft() {
        testName = TEST_CLASS_NAME + ".testAddLeft()";
        AbstractBinaryTree<Integer> tree = new LinkedBinaryTree<>();
        Node<Integer> one = tree.addRoot(1);
        assertEquals(testName, 2, tree.addLeft(one, 2).getElement());
        assertEquals(testName, asList(1, 2), transformIntoNew(tree.nodes(), Node::getElement));
        Node<Integer> three = tree.addLeft(one, 3);
        assertEquals(testName, asList(1, 3, 2), transformIntoNew(tree.nodes(), Node::getElement));
        tree.addRight(three, 4);
        tree.addLeft(one, 5);
        assertEquals(testName, asList(1, 5, 3, 2, 4), transformIntoNew(tree.nodes(), Node::getElement));
        try {
            tree.addLeft(null, 8);
            fail(FAILED);
        } catch (IllegalArgumentException e) {
            assertEquals(testName + EXCEPTION_STR, "node can't be null", e.getMessage());
        }
    }

    private static void testAddRight() {
        testName = TEST_CLASS_NAME + ".testAddRight()";
        AbstractBinaryTree<Integer> tree = new LinkedBinaryTree<>();
        Node<Integer> one = tree.addRoot(1);
        assertEquals(testName, 2, tree.addRight(one, 2).getElement());
        assertEquals(testName, asList(1, 2), transformIntoNew(tree.nodes(), Node::getElement));
        tree.addRight(one, 3);
        assertEquals(testName, asList(1, 3, 2), transformIntoNew(tree.nodes(), Node::getElement));
        try {
            tree.addRight(null, 8);
            fail(FAILED);
        } catch (IllegalArgumentException e) {
            assertEquals(testName + EXCEPTION_STR, "node can't be null", e.getMessage());
        }
    }

    private static void testAddRoot() {
        testName = TEST_CLASS_NAME + ".testAddRoot()";
        AbstractBinaryTree<Integer> tree = new LinkedBinaryTree<>();
        tree.addRoot(1);
        assertEquals(testName, singletonList(1), transformIntoNew(tree.nodes(), Node::getElement));
        try {
            tree.addRoot(8);
            fail(FAILED);
        } catch (IllegalStateException e) {
            assertEquals(testName + EXCEPTION_STR, "root already exists", e.getMessage());
        }
    }

    private static void testSet() {
        testName = TEST_CLASS_NAME + ".testSet()";
        AbstractBinaryTree<Integer> tree = new LinkedBinaryTree<>();
        tree.addRoot(1);
        Node<Integer> two = tree.add(tree.root(), 2);
        tree.add(tree.root(), 3);
        tree.set(tree.root(), 11);
        assertEquals(testName, asList(11, 2, 3), transformIntoNew(tree.nodes(), Node::getElement));
        tree.set(two, 22);
        assertEquals(testName, asList(11, 22, 3), transformIntoNew(tree.nodes(), Node::getElement));
        try {
            tree.set(null, 666);
            fail(FAILED);
        } catch (IllegalArgumentException e) {
            assertEquals(testName + EXCEPTION_STR, "node can't be null", e.getMessage());
        }
    }

    private static void testRemove() {
        testName = TEST_CLASS_NAME + ".testRemove()";
        AbstractBinaryTree<Integer> tree = new LinkedBinaryTree<>();
        Node<Integer> root = tree.addRoot(1);
        tree.remove(root);
        assertEquals(testName, emptyList(), tree.nodes());
        root = tree.addRoot(111);
        tree.add(tree.root(), 1);
        tree.remove(root);
        assertEquals(testName, singletonList(1), transformIntoNew(tree.nodes(), Node::getElement));
        Node<Integer> two = tree.add(tree.root(), 2);
        Node<Integer> three = tree.add(tree.root(), 3);

        Node<Integer> four = tree.add(two, 4);
        Node<Integer> five = tree.add(two, 5);
        Node<Integer> six = tree.add(three, 6);
        Node<Integer> seven = tree.add(three, 7);

        Node<Integer> eight = tree.add(four, 8);
        tree.add(four, 9);
        tree.add(five, 10);
        tree.add(five, 11);
        tree.add(six, 12);
        Node<Integer> thirteen = tree.add(six, 13);
        tree.add(seven, 14);
        tree.add(seven, 15);

        try {
            tree.remove(four);
            fail(FAILED);
        } catch (IllegalArgumentException e) {
            assertEquals(testName + EXCEPTION_STR, "cannot delete node with two children", e.getMessage());
        }
        assertEquals(testName, 8, tree.remove(eight));
        assertEquals(testName, asList(1, 2, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14, 15),
                transformIntoNew(tree.nodes(), Node::getElement));
        tree.remove(four);
        assertEquals(testName, asList(1, 2, 3, 9, 5, 6, 7, 10, 11, 12, 13, 14, 15), transformIntoNew(tree.nodes(),
                Node::getElement));
        tree.remove(thirteen);
        assertEquals(testName, asList(1, 2, 3, 9, 5, 6, 7, 10, 11, 12, 14, 15), transformIntoNew(tree.nodes(), Node::
                getElement));
        tree.remove(six);
        assertEquals(testName, asList(1, 2, 3, 9, 5, 12, 7, 10, 11, 14, 15), transformIntoNew(tree.nodes(), Node::
                getElement));
    }

    private static void testSize() {
        testName = TEST_CLASS_NAME + ".testSize()";
        AbstractBinaryTree<Integer> tree = new LinkedBinaryTree<>();
        assertEquals(testName, 0, tree.size());
        tree.addRoot(1);
        Node<Integer> two = tree.add(tree.root(), 2);
        tree.add(tree.root(), 3);
        assertEquals(testName, 3, tree.size());
        tree.add(two, 4);
        assertEquals(testName, 4, tree.size());
        tree.remove(two);
        assertEquals(testName, 3, tree.size());
    }

    private static void testChildrenNumber() {
        testName = TEST_CLASS_NAME + ".testChildrenNumber()";
        AbstractBinaryTree<Integer> tree = new LinkedBinaryTree<>();
        tree.addRoot(1);
        Node<Integer> two = tree.add(tree.root(), 2);
        Node<Integer> three = tree.add(tree.root(), 3);
        Node<Integer> four = tree.add(two, 4);
        tree.add(two, 5);
        tree.add(three, 6);
        tree.add(three, 7);
        Node<Integer> eight = tree.add(four, 8);
        assertEquals(testName, 0, tree.childrenNumber(eight));
        assertEquals(testName, 1, tree.childrenNumber(four));
        assertEquals(testName, 2, tree.childrenNumber(two));
    }

    private static void testChildren() {
        testName = TEST_CLASS_NAME + ".testChildren()";
        AbstractBinaryTree<Integer> tree = new LinkedBinaryTree<>();
        tree.addRoot(1);
        Node<Integer> two = tree.add(tree.root(), 2);
        Node<Integer> three = tree.add(tree.root(), 3);
        Node<Integer> four = tree.add(two, 4);
        Node<Integer> five = tree.add(two, 5);
        tree.add(three, 6);
        tree.add(three, 7);
        Node<Integer> eight = tree.add(four, 8);
        assertEquals(testName, new ArrayList<>(), tree.children(eight));
        assertEquals(testName, singletonList(eight), tree.children(four));
        assertEquals(testName, asList(four, five), tree.children(two));
    }

    private static void testPreOrder() {
        testName = TEST_CLASS_NAME + ".testPreOrder()";
        AbstractTree<Integer> tree = init();
        assertEquals(testName, asList(1, 2, 4, 8, 9, 5, 10, 11, 3, 6, 12, 13, 7, 14, 15), transformIntoNew(tree.
                preOrder(), Node::getElement));
    }

    private static void testInOrder() {
        testName = TEST_CLASS_NAME + ".testInOrder()";
        AbstractBinaryTree<Integer> tree = init();
        assertEquals(testName, asList(8, 4, 9, 2, 10, 5, 11, 1, 12, 6, 13, 3, 14, 7, 15), transformIntoNew(tree.
                inOrder(), Node::getElement));
    }

    private static void testPostOrder() {
        testName = TEST_CLASS_NAME + ".testPostOrder()";
        AbstractTree<Integer> tree = init();
        assertEquals(testName, asList(8, 9, 4, 10, 11, 5, 2, 12, 13, 6, 14, 15, 7, 3, 1), transformIntoNew(tree.
                postOrder(), Node::getElement));
    }

    private static void testBreadthFirst() {
        testName = TEST_CLASS_NAME + ".testBreadthFirst()";
        AbstractTree<Integer> tree = init();
        assertEquals(testName, asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15), transformIntoNew(tree.
                breadthFirst(), Node::getElement));
        tree = new LinkedBinaryTree<>();
        tree.addRoot(1);
        tree.add(tree.root(), 2);
        Node<Integer> three = tree.add(tree.root(), 3);
        tree.add(three, 4);
        tree.add(three, 5);
        assertEquals(testName, asList(1, 2, 3, 4, 5), transformIntoNew(tree.breadthFirst(), Node::getElement));
    }

    /**
     * creates new {@link LinkedBinaryTree} with some elements for tests
     *
     * @return {@link AbstractBinaryTree} with some elements
     */
    private static AbstractBinaryTree<Integer> init() {
        AbstractBinaryTree<Integer> tree = new LinkedBinaryTree<>();
        tree.addRoot(1);

        Node<Integer> two = tree.add(tree.root(), 2);
        Node<Integer> three = tree.add(tree.root(), 3);

        Node<Integer> four = tree.add(two, 4);
        Node<Integer> five = tree.add(two, 5);
        Node<Integer> six = tree.add(three, 6);
        Node<Integer> seven = tree.add(three, 7);

        tree.add(four, 8);
        tree.add(four, 9);
        tree.add(five, 10);
        tree.add(five, 11);
        tree.add(six, 12);
        tree.add(six, 13);
        tree.add(seven, 14);
        tree.add(seven, 15);

        return tree;
    }
}
