package ru.spbstu.redblacktree.tree.binarytree;

import ru.spbstu.redblacktree.tree.Node;
import ru.spbstu.redblacktree.tree.Tree;

/**
 * an interface for a binary tree, in which each node has at most two children
 *
 * @param <E> element
 */
public interface BinaryTree<E> extends Tree<E> {
    /**
     * @param p node
     * @return the node of <i>node</i>'s left child (of null if no child exists)
     */
    Node<E> left(Node<E> p);

    /**
     * @param p node
     * @return the node of <i>node</i>'s right child (of null if no child exists)
     */
    Node<E> right(Node<E> p);

    /**
     * @param p node
     * @return the node of <i>node</i>'s sibling (of null if no sibling exists)
     */
    Node<E> sibling(Node<E> p);

    /**
     * creates a new left child of {@link Node} <i>n</i> storing element <i>e</i>
     *
     * @param n node
     * @param e element
     * @return created node
     * @throws IllegalArgumentException if <i>node</i> already has a left child
     */
    Node<E> addLeft(Node<E> n, E e);

    /**
     * creates a new right child of {@link Node} <i>n</i> storing element <i>e</i>
     *
     * @param n node
     * @param e element
     * @return created node
     * @throws IllegalArgumentException if <i>n</i> already has a right child
     */
    Node<E> addRight(Node<E> n, E e);
}
