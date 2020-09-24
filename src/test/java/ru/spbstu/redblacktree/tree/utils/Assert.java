package ru.spbstu.redblacktree.tree.utils;

import java.util.Objects;

import static java.lang.System.out;

public class Assert {
    private Assert() {
    }

    public static void assertEquals(String testName, Object expected, Object actual) {
        if (Objects.deepEquals(expected, actual)) {
            out.println(testName + " passed");
        } else {
            out.println(testName + " failed: expected " + expected + ", actual " + actual);
        }
    }

    /**
     * print exception assertion fail
     *
     * @param msg message to print
     */
    public static String fail(String msg) {
        throw new AssertionError(msg);
    }
}
