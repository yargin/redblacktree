package ru.spbstu.redblacktree.tree.binarytree.search;

import ru.spbstu.redblacktree.tree.Node;
import ru.spbstu.redblacktree.tree.binarytree.LinkedBinaryTree;

import java.util.Comparator;

import static java.util.Objects.isNull;

public class BinarySearchTree<E> extends LinkedBinaryTree<E> {
    public static final String NULL_NODE_MSG = "cannot add null to search tree";
    private Comparator<? super E> comparator;

    public BinarySearchTree() {
    }

    public BinarySearchTree(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    /**
     * removes <i>node</i> from tree. Allows remove n with two children
     *
     * @param n to remove
     * @return removed n's element
     */
    @Override
    public E remove(Node<E> n) {
        if (childrenNumber(n) < 2) {
            return super.remove(n);
        }
        E element = n.getElement();
        //find leftmost in right subtree
        Node<E> leftmost = findLeftmost(right(n));
        super.remove(leftmost);

        setLeft(leftmost, left(n));
        setRight(leftmost, right(n));
        if (isNull(parent(n))) {
            setRoot(leftmost);
        } else if (n == left(parent(n))) {
            setLeft(parent(n), leftmost);
        } else {
            setRight(parent(n), leftmost);
        }

        return element;
    }

    /**
     * removes node from the tree accordingly it's value
     *
     * @param val value to remove
     * @return removed value
     */
    public E remove(E val) {
        Node<E> nodeToDelete = treeSearch(root(), val);
        return remove(nodeToDelete);
    }

    /**
     * searches leftmost node in <i>node</i>'s subtree
     *
     * @param n root of subtree to search
     * @return leftmost node
     */
    protected Node<E> findLeftmost(Node<E> n) {
        if (isNull(left(n))) {
            return n;
        }
        return findLeftmost(left(n));
    }

    /**
     * comparing two values
     *
     * @param val1 first value to compare
     * @param val2 second value to compare
     * @return 1 if left is greater
     * 0 if values are equal
     * -1 if right is greater
     */
    protected int compare(E val1, E val2) {
        if (isNull(val1) || isNull(val2)) {
            throw new IllegalStateException("cannot compare nulls");
        }
        if (isNull(comparator)) {
            if (val1 instanceof Comparable) {
                return ((Comparable<E>) val1).compareTo(val2);
            }
        } else {
            return comparator.compare(val1, val2);
        }
        throw new IllegalStateException("cannot compare");
    }

    /**
     * returns the node in n's subtree by val
     *
     * @param n   subtree's root
     * @param val value specified in node
     * @return node containing searched value
     */
    public Node<E> treeSearch(Node<E> n, E val) {
        if (isNull(n)) {
            return null;
        }
        if (compare(n.getElement(), val) == 0) {
            return n;
        }
        if (compare(n.getElement(), val) < 0) {
            if (!isNull(right(n))) {
                return treeSearch(right(n), val);
            }
        } else {
            return treeSearch(left(n), val);
        }
        return null;
    }

    public E search(E val) {
        if (treeSearch(root(), val) != null) {
            return treeSearch(root(), val).getElement();
        }
        return null;
    }

    /**
     * adds new node to tree accordingly it's value. New nodes added only to leaves
     *
     * @param val value to add
     * @return added leaf
     */
    public Node<E> add(E val) {
        checkVal(val);
        if (isNull(root())) {
            return addRoot(val);
        }
        return add(root(), val);
    }

    @Override
    public Node<E> addRoot(E e) {
        checkVal(e);
        return super.addRoot(e);
    }

    @Override
    public Node<E> addLeft(Node<E> n, E e) {
        checkVal(e);
        return super.addLeft(n, e);
    }

    @Override
    public Node<E> addRight(Node<E> n, E e) {
        checkVal(e);
        return super.addRight(n, e);
    }

    @Override
    public E set(Node<E> n, E e) {
        checkVal(e);
        return super.set(n, e);
    }

    @Override
    protected Node<E> setLeft(Node<E> n, Node<E> newChild) {
        checkVal(newChild);
        return super.setLeft(n, newChild);
    }

    @Override
    protected Node<E> setRight(Node<E> n, Node<E> newChild) {
        checkVal(newChild);
        return super.setRight(n, newChild);
    }

    @Override
    protected Node<E> setRoot(Node<E> n) {
        checkVal(n);
        return super.setRoot(n);
    }

    private void checkVal(E val) {
        if (isNull(val)) {
            throw new IllegalArgumentException(NULL_NODE_MSG);
        }
    }

    private void checkVal(Node<E> node) {
        if (!isNull(node) && isNull(node.getElement())) {
            throw new IllegalArgumentException(NULL_NODE_MSG);
        }
    }

    @Override
    public Node<E> add(Node<E> n, E val) {
        checkVal(val);
        int compare = compare(n.getElement(), val);
        if (compare > 0) {
            if (isNull(left(n))) {
                return addLeft(n, val);
            }
            return add(left(n), val);
        } else if (compare < 0) {
            if (isNull(right(n))) {
                return addRight(n, val);
            }
            return add(right(n), val);
        }
        return n;
    }
}
