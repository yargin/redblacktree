package ru.spbstu.redblacktree.tree;

import java.util.Date;

import static java.lang.System.out;

public class TestSuite {
    public static final String DELIMITER = "--------";

    public static void main(String[] args) {
        out.println(new Date() + ": tests started...");

        out.println(DELIMITER);
        AbstractBinaryTreeTest.main(args);
        out.println(DELIMITER);
        BalanceAbleTreeTest.main(args);
        out.println(DELIMITER);
        BinarySearchTreeTest.main(args);
        out.println(DELIMITER);
        CollectionUtilsTest.main(args);
        out.println(DELIMITER);
        LinkedBinaryTreeTest.main(args);
        out.println(DELIMITER);
        RedBlackTreeTest.main(args);
        out.println(DELIMITER);

        out.println(new Date() + ": tests completed");
    }
}
