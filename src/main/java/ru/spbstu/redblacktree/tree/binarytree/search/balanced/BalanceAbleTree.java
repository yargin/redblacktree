package ru.spbstu.redblacktree.tree.binarytree.search.balanced;

import ru.spbstu.redblacktree.tree.Node;
import ru.spbstu.redblacktree.tree.binarytree.search.BinarySearchTree;

import static java.util.Objects.isNull;

public abstract class BalanceAbleTree<E> extends BinarySearchTree<E> {
    /**
     * sets new relationship between parent and child. This method is used by
     * {@link #rotate(Node)} for node and its grandparent,
     * node and its parent, node's child and node's parent relinking.
     *
     * @param parent        parent that becomes new child
     * @param child         child that becomes new parent
     * @param makeLeftChild whether new child must be left or right
     */
    protected void relink(Node<E> parent, Node<E> child, boolean makeLeftChild) {
        if (isNull(parent(parent))) {
            setRoot(child);
        } else {
            Node<E> grandParent = parent(parent);
            if (parent == left(grandParent)) {
                setLeft(grandParent, child);
            } else {
                setRight(grandParent, child);
            }
        }
        if (makeLeftChild) {
            setRight(parent, left(child));
            setLeft(child, parent);
        } else {
            setLeft(parent, right(child));
            setRight(child, parent);
        }
    }

    /**
     * rotates n with it's parent. Changes linking side
     *
     * @param node node to rotate above its parent
     */
    protected void rotate(Node<E> node) {
        Node<E> parent = getNoNullParent(node);
        boolean makeLeftChild = node == right(parent);
        relink(parent, node, makeLeftChild);
    }

    /**
     * performs one rotation of <i>n</i>'s parent node or two rotations of <i>n</i> by the means of
     * {@link #rotate(Node)} to reduce the height of subtree
     * rooted at <i>n1</i>
     *
     * <pre>
     *     n1         n2           n1           n1          n
     *    /          /  \         /            /           / \
     *   n2    ==>  n   n1  or  n2     ==>    n     ==>  n2   n1
     *  /                         \          /
     * n                           n        n2
     * </pre>
     * <p>
     * Similarly for subtree with right side children.
     *
     * @param node grand child of subtree root node
     * @return new subtree root
     */
    protected Node<E> reduceSubtreeHeight(Node<E> node) {
        Node<E> parent = getNoNullParent(node);
        Node<E> grandParent = getNoNullParent(parent);
        if (node == left(parent) && parent == left(grandParent) ||
                node == right(parent) && parent == right(grandParent)) {
            rotate(parent);
        } else {
            rotate(node);
            rotate(node);
        }
        return null;
    }

    /**
     * returns <i>node</i>'s parent, throws exception if it is null
     *
     * @param node given node
     * @return <i>node</i>'s parent
     * @throws IllegalArgumentException if <i>node</i>'s parent is null
     */
    protected Node<E> getNoNullParent(Node<E> node) {
        Node<E> parent = parent(node);
        if (isNull(parent)) {
            throw new IllegalArgumentException("node's parent can't be null");
        }
        return parent;
    }
}

