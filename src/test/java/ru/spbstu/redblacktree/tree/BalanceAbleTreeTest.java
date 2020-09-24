package ru.spbstu.redblacktree.tree;

import ru.spbstu.redblacktree.tree.binarytree.search.balanced.BalanceAbleTree;
import ru.spbstu.redblacktree.tree.utils.Assert;

import static java.util.Arrays.asList;
import static ru.spbstu.redblacktree.tree.utils.Assert.assertEquals;
import static ru.spbstu.redblacktree.utils.CollectionUtils.transformIntoNew;

/**
 * provides tests for {@link BalanceAbleTree}
 */
public class BalanceAbleTreeTest {
    private static final String TEST_CLASS_NAME = "BalanceAbleTreeTest";
    private static final String EXCEPTION_STR = " (exception)";
    private static final String FAILED = "failed";
    private static String testName;

    public static void main(String[] args) {
        testReduceSubstringsHeight();
        testRotate();
    }

    private static void testReduceSubstringsHeight() {
        testName = TEST_CLASS_NAME + ".testReduceSubstringsHeight()";
        BalanceAbleTreeImpl<String> strings = new BalanceAbleTreeImpl<>();
        Node<String> root = strings.add("5");
        Node<String> pivot = strings.addLeft(root, "3");
        strings.addRight(root, "A");
        strings.addLeft(pivot, "B");
        Node<String> child = strings.addRight(pivot, "4");
        strings.addLeft(child, "C");
        strings.addRight(child, "D");
        strings.reduceSubtreeHeight(child);
        assertEquals(testName, asList("4", "3", "5", "B", "C", "D", "A"), transformIntoNew(strings.breadthFirst(), Node::
                getElement));

        strings = new BalanceAbleTreeImpl<>();
        root = strings.add("1");
        Node<String> subroot = strings.add(root, "3");
        pivot = strings.addRight(subroot, "5");
        strings.addLeft(subroot, "A");
        strings.addRight(pivot, "B");
        child = strings.addLeft(pivot, "4");
        strings.addRight(child, "C");
        strings.addLeft(child, "D");
        strings.reduceSubtreeHeight(child);
        assertEquals(testName, asList("1", "4", "3", "5", "A", "D", "C", "B"), transformIntoNew(strings.breadthFirst(),
                Node::getElement));

        strings = new BalanceAbleTreeImpl<>();
        root = strings.add("3");
        pivot = strings.addRight(root, "5");
        strings.addLeft(root, "A");
        strings.addRight(pivot, "B");
        child = strings.addLeft(pivot, "4");
        strings.addRight(child, "C");
        strings.addLeft(child, "D");
        strings.reduceSubtreeHeight(child);
        assertEquals(testName, asList("4", "3", "5", "A", "D", "C", "B"), transformIntoNew(strings.breadthFirst(),
                Node::getElement));

        strings = new BalanceAbleTreeImpl<>();
        root = strings.add("3");
        pivot = strings.addRight(root, "5");
        strings.addLeft(root, "A");
        strings.addRight(pivot, "B");
        child = strings.addLeft(pivot, "4");
        strings.addRight(child, "C");
        strings.addLeft(child, "D");
        try {
            strings.reduceSubtreeHeight(pivot);
            Assert.fail(FAILED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(testName + EXCEPTION_STR, "node's parent can't be null", e.getMessage());
        }
        try {
            strings.reduceSubtreeHeight(root);
            Assert.fail(FAILED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(testName + EXCEPTION_STR, "node's parent can't be null", e.getMessage());
        }
    }

    private static void testRotate() {
        testName = TEST_CLASS_NAME + ".testRotate()";
        BalanceAbleTreeImpl<String> strings = new BalanceAbleTreeImpl<>();
        Node<String> root = strings.add("5");
        Node<String> pivot = strings.addLeft(root, "3");
        strings.addRight(root, "A");
        strings.addRight(pivot, "B");
        Node<String> child = strings.addLeft(pivot, "2");
        strings.addRight(child, "C");
        strings.addLeft(child, "D");

        strings.rotate(pivot);
        assertEquals(testName, asList("3", "2", "5", "D", "C", "B", "A"), transformIntoNew(strings.breadthFirst(), Node::
                getElement));

        strings = new BalanceAbleTreeImpl<>();
        root = strings.add("3");
        pivot = strings.addRight(root, "5");
        strings.addLeft(root, "A");
        strings.addLeft(pivot, "B");
        child = strings.addRight(pivot, "7");
        strings.addLeft(child, "C");
        strings.addRight(child, "D");

        strings.rotate(pivot);
        assertEquals(testName, asList("5", "3", "7", "A", "B", "C", "D"), transformIntoNew(strings.breadthFirst(), Node::
                getElement));

        strings = new BalanceAbleTreeImpl<>();
        root = strings.add("3");
        pivot = strings.addRight(root, "5");
        strings.addLeft(root, "A");
        strings.addLeft(pivot, "B");
        child = strings.addRight(pivot, "7");
        strings.addLeft(child, "C");
        strings.addRight(child, "D");
        try {
            strings.reduceSubtreeHeight(root);
            Assert.fail(FAILED);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(testName + EXCEPTION_STR, "node's parent can't be null", e.getMessage());
        }
    }

    /**
     * testing implementation of {@link BalanceAbleTree}
     *
     * @param <E>
     */
    private static class BalanceAbleTreeImpl<E> extends BalanceAbleTree<E> {
        @Override
        protected Node<E> reduceSubtreeHeight(Node<E> node) {
            return super.reduceSubtreeHeight(node);
        }

        @Override
        protected void rotate(Node<E> node) {
            super.rotate(node);
        }
    }
}
