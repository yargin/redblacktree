package ru.spbstu.redblacktree.ui;

import ru.spbstu.redblacktree.tree.Tree;
import ru.spbstu.redblacktree.tree.binarytree.search.balanced.BalanceAbleTree;
import ru.spbstu.redblacktree.tree.binarytree.search.balanced.redblacktree.RedBlackTree;
import ru.spbstu.redblacktree.utils.Transformer;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.*;

public class Application {
    public static final Type TYPE = Type.INTEGER;
    private static final Scanner scanner = new Scanner(in);

    public static void main(String[] args) throws InterruptedException {
        out.println("enter command('add " + TYPE.getTypeName() + "' to add, 'del " + TYPE.getTypeName() + "' to quit, " +
                "q to quit): ");
        run(TYPE.getParser(), new RedBlackTree<>());
    }

    /**
     * runs interactive console application providing adding and deleting elements into {@link RedBlackTree}
     *
     * @param transformer transforms {@link String} value into proper
     * @param tree        {@link Tree} that stores elements
     * @param <E>         element's type
     */
    private static <E> void run(Transformer<String, E> transformer, BalanceAbleTree<E> tree) {
        String entered = scanner.nextLine().trim();
        while (!"q".equals(entered)) {
            try {
                E value = getValue(entered, transformer);
                Action action = specifyAction(entered);
                if (action.equals(Action.ADD)) {
                    tree.add(value);
                } else {
                    tree.remove(value);
                }
                printTree(tree);
//                printRBTree(tree);
            } catch (IllegalArgumentException e) {
                out.println("wrong command, please try again");
            }
            out.println("enter command: ");
            entered = scanner.nextLine().trim();
        }
    }

    /**
     * specifies {@link Action} to execute by entered command
     *
     * @param entered command to execute
     * @return {@link Action} to execute
     */
    private static Action specifyAction(String entered) {
        String addRegex = "add" + ".+";
        Matcher addMatcher = Pattern.compile(addRegex, Pattern.CASE_INSENSITIVE).matcher(entered);
        if (addMatcher.matches()) {
            return Action.ADD;
        }
        String delRegex = "del" + ".+";
        Matcher delMatcher = Pattern.compile(delRegex, Pattern.CASE_INSENSITIVE).matcher(entered);
        if (delMatcher.matches()) {
            return Action.DEL;
        }
        throw new IllegalArgumentException();
    }

    /**
     * gets {@link Number} from given string by specified {@link Transformer}.
     * Gets number from the end of string(needs to be trimmed)
     *
     * @param string      having number
     * @param transformer method to get value from given string
     * @param <E>         value's type
     * @return value transformed from given string
     */
    private static <E> E getValue(String string, Transformer<String, E> transformer) {
        int numberIndex = string.lastIndexOf(' ');
        return transformer.transform(string.substring(numberIndex + 1));
    }

    private static <E> void printTree(BalanceAbleTree<E> tree) {
        out.println(tree);
    }

    private static <E> void printRBTree(BalanceAbleTree<E> tree) {
        String stringTree = tree.toString();
        int startPointer = 0;
        int endPointer;
        while (true) {
            try {
                endPointer = stringTree.indexOf('(', startPointer);
                if (endPointer == -1) {
                    out.print(stringTree.substring(startPointer));
                    Thread.sleep(100);
                    break;
                } else {
                    out.print(stringTree.substring(startPointer, endPointer - 1));
                    Thread.sleep(100);
                    startPointer = endPointer + 1;
                    endPointer = stringTree.indexOf(')', startPointer);
                    err.print(' ' + stringTree.substring(startPointer, endPointer) + ' ');
                    Thread.sleep(100);
                    startPointer = endPointer + 1;
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
