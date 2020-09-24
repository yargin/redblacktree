package ru.spbstu.redblacktree.tree;

import ru.spbstu.redblacktree.tree.binarytree.search.BinarySearchTree;
import ru.spbstu.redblacktree.tree.utils.Assert;

import java.util.Collection;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static ru.spbstu.redblacktree.tree.utils.Assert.assertEquals;
import static ru.spbstu.redblacktree.utils.CollectionUtils.transformIntoNew;

/**
 * provides tests for {@link BinarySearchTree}
 */
public class BinarySearchTreeTest {
    private static final String TEST_CLASS_NAME = "BinarySearchTreeTest";
    private static final String FAILED = "failed";
    private static String testName;

    public static void main(String[] args) {
        testAdd();
        testRemove();
        testSearch();
    }

    private static void testAdd() {
        testName = TEST_CLASS_NAME + ".testAdd()";
        BinarySearchTree<Integer> integers = new BinarySearchTree<>();
        integers.add(6);
        assertEquals(testName, singletonList(6), transformIntoNew(integers.breadthFirst(), Node::getElement));
        integers.add(5);
        Assert.assertEquals(testName, 9, integers.add(9).getElement());
        Assert.assertEquals(testName, 9, integers.add(9).getElement());
        integers.add(3);
        integers.add(4);
        integers.add(8);
        integers.add(10);
        assertEquals(testName, asList(6, 5, 9, 3, 8, 10, 4), transformIntoNew(integers.breadthFirst(), Node::getElement));
        BinarySearchTree<String> personTree = treeInit();
        Collection<String> persons = transformIntoNew(personTree.breadthFirst(), Node::getElement);
        Assert.assertEquals(testName, asList("Max", "Daniel", "Peter", "Andrew", "Gregory", "Nick", "Tom", "Egor", "Quentin",
                "Yuri"), persons);
    }

    private static void testRemove() {
        testName = TEST_CLASS_NAME + ".testRemove()";
        BinarySearchTree<Integer> integers = new BinarySearchTree<>();
        integers.add(6);
        Node<Integer> four = integers.add(4);
        integers.add(5);
        integers.add(2);
        Node<Integer> nine = integers.add(9);
        integers.add(3);
        integers.add(8);
        integers.add(7);
        integers.add(11);
        integers.add(10);
        Assert.assertEquals(testName, 4, integers.remove(four));
        assertEquals(testName, asList(6, 5, 9, 2, 8, 11, 3, 7, 10), transformIntoNew(integers.breadthFirst(), Node::
                getElement));
        Assert.assertEquals(testName, 9, integers.remove(nine));
        assertEquals(testName, asList(6, 5, 10, 2, 8, 11, 3, 7), transformIntoNew(integers.breadthFirst(), Node::
                getElement));
    }

    private static void testSearch() {
        testName = TEST_CLASS_NAME + ".testSearch()";
        BinarySearchTree<Integer> integers = new BinarySearchTree<>();
        integers.add(6);
        integers.add(5);
        integers.add(9);
        integers.add(3);
        Node<Integer> four = integers.add(4);
        Node<Integer> eight = integers.add(8);
        integers.add(10);
        Assert.assertEquals(testName, four, integers.treeSearch(integers.root(), 4));
        Assert.assertEquals(testName, eight, integers.treeSearch(integers.root(), 8));
        try {
            integers.treeSearch(integers.root(), null);
            Assert.fail(FAILED);
        } catch (IllegalStateException e) {
            Assert.assertEquals("AbstractBinaryTree.testSearch() (exception)", "cannot compare nulls", e.getMessage());
        }
        Assert.assertEquals(testName, null, integers.treeSearch(null, 1));
        Assert.assertEquals(testName, null, integers.treeSearch(integers.root(), 33));
        Assert.assertEquals(testName, null, integers.treeSearch(integers.root(), 1));

        BinarySearchTree<String> personTree = treeInit();
        Assert.assertEquals(testName, "Max", personTree.search("Max"));
        Assert.assertEquals(testName, null, personTree.search("Jack"));
    }

    /**
     * creates new {@link BinarySearchTree} with some elements for tests
     *
     * @return {@link BinarySearchTree} with some elements
     */
    private static BinarySearchTree<String> treeInit() {
        BinarySearchTree<String> names = new BinarySearchTree<>();
        names.add("Max");
        names.add("Daniel");
        names.add("Andrew");
        names.add("Gregory");
        names.add("Egor");
        names.add("Peter");
        names.add("Nick");
        names.add("Tom");
        names.add("Quentin");
        names.add("Yuri");
        return names;
    }
}
