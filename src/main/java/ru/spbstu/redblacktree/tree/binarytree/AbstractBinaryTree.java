package ru.spbstu.redblacktree.tree.binarytree;

import ru.spbstu.redblacktree.tree.AbstractTree;
import ru.spbstu.redblacktree.tree.Node;
import ru.spbstu.redblacktree.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.System.lineSeparator;
import static java.util.Objects.isNull;

public abstract class AbstractBinaryTree<E> extends AbstractTree<E> implements BinaryTree<E> {
    /**
     * indent between nodes
     */
    protected static final String DELIMITER = " ";
    /**
     * null node string representation
     */
    protected static final String NULL_NODE = ".";

    @Override
    public Node<E> sibling(Node<E> n) {
        if (isNull(parent(n))) {
            return null;
        }
        if (n == left(parent(n))) {
            return right(parent(n));
        }
        return left(parent(n));
    }

    @Override
    public Collection<Node<E>> children(Node<E> n) {
        Collection<Node<E>> children = new ArrayList<>();
        if (!isNull(left(n))) {
            children.add(left(n));
        }
        if (!isNull(right(n))) {
            children.add(right(n));
        }
        return children;
    }

    @Override
    public int childrenNumber(Node<E> n) {
        Node<E> left = left(n);
        Node<E> right = right(n);
        if (isNull(left) && isNull(right)) {
            return 0;
        } else if (!isNull(left) && !isNull(right)) {
            return 2;
        }
        return 1;
    }

    public Collection<Node<E>> inOrder() {
        List<Node<E>> nodes = new ArrayList<>();
        if (isNull(root())) {
            return nodes;
        }
        return inOrder(root(), nodes);
    }

    private Collection<Node<E>> inOrder(Node<E> node, Collection<Node<E>> nodes) {
        if (isNull(node)) {
            return nodes;
        }
        inOrder(left(node), nodes);
        nodes.add(node);
        inOrder(right(node), nodes);
        return nodes;
    }

    @Override
    public Iterator<E> iterator() {
        return CollectionUtils.transformIntoNew(nodes(), Node::getElement).iterator();
    }

    @Override
    public Collection<Node<E>> nodes() {
        return breadthFirst();
    }

    /**
     * calculates tree's height
     *
     * @return tree's height
     */
    protected int getHeight() {
        return getHeight(root(), 0, 1);
    }

    public int getHeight(Node<E> node, int depth, int treeHeight) {
        if (isNull(node)) {
            return max(depth, treeHeight);
        }
        return getHeight(left(node), depth + 1, getHeight(right(node), depth + 1, treeHeight));
    }

    /**
     * calculates length of longest element.toString() in tree
     *
     * @return length
     */
    protected int maxStrLength() {
        Collection<String> nodes = CollectionUtils.transformIntoNew(breadthFirst(), Object::toString);
        int longest = 0;
        for (String str : nodes) {
            if (str.length() > longest) {
                longest = str.length();
            }
        }
        return longest;
    }

    @Override
    public String toString() {
        if (isNull(root())) {
            return "";
        }
        int height = getHeight();
        int maxStrLength = maxStrLength();
        List<StringBuilder> rows = new ArrayList<>();
        drawTreeAsList(root(), rows, height, maxStrLength, 0);
        StringBuilder result = new StringBuilder();
        for (StringBuilder row : rows) {
            result.append(row).append(lineSeparator());
        }
        return result.toString();
    }

    private void drawTreeAsList(Node<E> node, List<StringBuilder> rows, int treeHeight, int strLength, int depth) {
        if (rows.size() < treeHeight) {
            rows.add(new StringBuilder());
        }

        int shift = 1 << treeHeight - depth - 1;
        StringBuilder stringBuilder = new StringBuilder();

        StringBuilder row;
        //get row or return if it is last empty row
        if (depth == treeHeight) {
            return;
        } else {
            row = rows.get(depth);
        }

        //add left delimiters
        row.append(addDelimiters(stringBuilder, (shift - 1) * strLength));
        //add element
        String strElem = isNull(node) ? NULL_NODE : node.toString();
        stringBuilder = growStrByDelims(strElem, strLength);
        //add right delimiters
        stringBuilder = addDelimiters(stringBuilder, shift * strLength);
        row.append(stringBuilder);

        Node<E> nextNode = isNull(node) ? null : left(node);
        drawTreeAsList(nextNode, rows, treeHeight, strLength, depth + 1);
        nextNode = isNull(node) ? null : right(node);
        drawTreeAsList(nextNode, rows, treeHeight, strLength, depth + 1);
    }

    /**
     * adds given number of delimiters to the end of starting stringBuilder
     *
     * @param sb          starting stringBuilder
     * @param delimNumber given number of delimiters
     * @return stringBuilder grown by delimiters
     */
    protected StringBuilder addDelimiters(StringBuilder sb, int delimNumber) {
        for (int i = 0; i < delimNumber; i++) {
            sb.append(DELIMITER);
        }
        return sb;
    }

    /**
     * adds additional delimiters to align given string to needed length
     *
     * @param str          given string
     * @param lengthNeeded length needed
     * @return grown string
     */
    public StringBuilder growStrByDelims(String str, int lengthNeeded) {
        StringBuilder stringBuilder = new StringBuilder(str);
        for (int i = 0; i < lengthNeeded - str.length(); i++) {
            stringBuilder.append(DELIMITER);
        }
        return stringBuilder;
    }

    /**
     * sets new left subtree for <i>node</i> rooted at <i>newChild</i>
     *
     * @param node     node, that's left child becomes root for new subtree
     * @param newChild new subtree's root
     * @return old subtree's root
     */
    protected abstract Node<E> setLeft(Node<E> node, Node<E> newChild);

    /**
     * sets new right subtree for <i>node</i> rooted at <i>newChild</i>
     *
     * @param node     node, that's right child becomes root for new subtree
     * @param newChild new subtree's root
     * @return old subtree's root
     */
    protected abstract Node<E> setRight(Node<E> node, Node<E> newChild);

    /**
     * sets tree's new root
     *
     * @param node new root
     * @return old root
     */
    protected abstract Node<E> setRoot(Node<E> node);
}
