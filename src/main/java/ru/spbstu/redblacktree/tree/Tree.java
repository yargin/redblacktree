package ru.spbstu.redblacktree.tree;

import java.util.Collection;
import java.util.Iterator;

public interface Tree<E> extends Iterable<E> {
    /**
     * @return the node of the root of the tree (or null if empty)
     */
    Node<E> root();

    /**
     * @param n node
     * @return the node of the parent of the tree (or null if empty)
     */
    Node<E> parent(Node<E> n);

    /**
     * @param n node
     * @return an iterable collection containing the children of node <i>node</i> (if any)
     */
    Collection<Node<E>> children(Node<E> n);

    /**
     * @param n node
     * @return the number of children of node <i>n</i>
     * @throws IllegalArgumentException if <i>n</i> instance is not of supported type
     */
    int childrenNumber(Node<E> n);

    /**
     * places element <i>e</i> at the root of an empty tree and returns its new {@link Node}
     *
     * @param e element
     * @return created root
     * @throws IllegalStateException if tree is not empty
     */
    Node<E> addRoot(E e);

    /**
     * creates a new child of {@link Node} <i>p</i> storing element <i>e</i>
     *
     * @param p parent node
     * @param e element
     * @return created node
     * @throws IllegalArgumentException
     */
    Node<E> add(Node<E> p, E e);

    /**
     * replaces the element at {@link Node} <i>n</i> with <i>e</i>
     *
     * @param n node
     * @param e element
     * @return replace element
     */
    E set(Node<E> n, E e);

    /**
     * replaces the element at {@link Node} <i>n</i> with <i>e</i>
     *
     * @param n node
     * @return replace element
     */
    E remove(Node<E> n);

    /**
     * @param n node
     * @return true if node <i>n</i> is the root of the tree
     * @throws IllegalArgumentException if <i>n</i> instance is not of supported type
     */
    boolean isRoot(Node<E> n);

    /**
     * @return the number of nodes (and hence elements) that are contained in the tree
     */
    int size();

    /**
     * @return an iterator for all elements in the tree (so that the tree itself is {@link Iterable})
     */
    @Override
    Iterator<E> iterator();

    /**
     * @return an iterable collection of all nodes of the tree
     */
    Collection<Node<E>> nodes();
}
