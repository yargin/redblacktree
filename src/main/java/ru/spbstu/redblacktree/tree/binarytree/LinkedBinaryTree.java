package ru.spbstu.redblacktree.tree.binarytree;

import ru.spbstu.redblacktree.tree.Node;

import java.util.NoSuchElementException;
import java.util.Objects;

import static java.util.Objects.isNull;

/**
 * concrete implementation of a binary tree using a node-based, linked structure
 *
 * @param <E> element
 */
public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {
    private int length;
    private Node<E> root;

    /**
     * validates the node is an instance of supported {@link NodeImpl} type and casts to it
     *
     * @param n node
     * @return casted {@link NodeImpl} node
     */
    protected NodeImpl<E> validate(Node<E> n) {
        if (n instanceof NodeImpl) {
            return (NodeImpl<E>) n;
        } else if (isNull(n)) {
            throw new IllegalArgumentException("node can't be null");
        }
        throw new IllegalArgumentException("wrong node");
    }

    @Override
    public Node<E> addRoot(E e) {
        if (size() == 0 && isNull(root)) {
            root = new NodeImpl<>(e);
            length = 1;
            return root;
        }
        throw new IllegalStateException("root already exists");
    }

    @Override
    public Node<E> add(Node<E> n, E e) {
        if (!isNull(left(n)) && !isNull(right(n))) {
            throw new IllegalArgumentException("node already has two siblings");
        }
        if (isNull(left(n))) {
            return addLeft(n, e);
        } else {
            return addRight(n, e);
        }
    }

    @Override
    public Node<E> addLeft(Node<E> n, E e) {
        Node<E> newNode = new NodeImpl<>(e);

        if (!isNull(left(n))) {
            Node<E> oldLeft = left(n);
            setLeft(newNode, oldLeft);
        }
        setLeft(n, newNode);
        length++;
        return newNode;
    }

    @Override
    public Node<E> addRight(Node<E> n, E e) {
        Node<E> newNode = new NodeImpl<>(e);

        if (!isNull(right(n))) {
            Node<E> oldRight = right(n);
            setRight(newNode, oldRight);
        }
        setRight(n, newNode);
        length++;
        return newNode;
    }

    /**
     * replaces the element at {@link Node} <i>n</i> with <i>e</i>
     *
     * @param n node
     * @param e element
     * @return replace element
     */
    @Override
    public E set(Node<E> n, E e) {
        NodeImpl<E> node = validate(n);
        E dataToReturn = node.data;
        node.data = e;
        return dataToReturn;
    }

    /**
     * replaces the element at {@link Node} <i>n</i> with <i>e</i>
     *
     * @param n node
     * @return replace element
     */
    @Override
    public E remove(Node<E> n) {
        if (childrenNumber(n) == 2) {
            throw new IllegalArgumentException("cannot delete node with two children");
        }
        Node<E> newChild = null;
        try {
            newChild = children(n).iterator().next();
        } catch (NoSuchElementException ignore) {
        }
        Node<E> parent = parent(n);
        if (isNull(parent)) {
            root = newChild;
        } else if (left(parent) == n) {
            setLeft(parent, newChild);
        } else {
            setRight(parent, newChild);
        }
        length--;
        return n.getElement();
    }

    @Override
    public Node<E> left(Node<E> p) {
        return validate(p).leftChild;
    }

    @Override
    public Node<E> right(Node<E> p) {
        return validate(p).rightChild;
    }

    @Override
    public Node<E> root() {
        return root;
    }

    @Override
    public Node<E> parent(Node<E> n) {
        return validate(n).parent;
    }

    @Override
    public int size() {
        return length;
    }

    /**
     * sets <i>node</i>'s new left child, null is acceptable
     *
     * @param n        given node
     * @param newChild new left child
     * @return old left child
     */
    @Override
    protected Node<E> setLeft(Node<E> n, Node<E> newChild) {
        NodeImpl<E> node = validate(n);
        NodeImpl<E> leftChild = null;
        if (!isNull(newChild)) {
            leftChild = validate(newChild);
            leftChild.parent = n;
        }
        Node<E> oldLeft = node.leftChild;
        node.leftChild = leftChild;
        return oldLeft;
    }

    /**
     * sets <i>node</i>'s new right child, null is acceptable
     *
     * @param n        given node
     * @param newChild new right child
     * @return old right child
     */
    @Override
    protected Node<E> setRight(Node<E> n, Node<E> newChild) {
        NodeImpl<E> node = validate(n);
        NodeImpl<E> rightChild = null;
        if (!isNull(newChild)) {
            rightChild = validate(newChild);
            rightChild.parent = n;
        }
        Node<E> oldLeft = node.rightChild;
        node.rightChild = rightChild;
        return oldLeft;
    }

    /**
     * sets new subtree's root
     *
     * @param n new root
     * @return old root
     */
    @Override
    protected Node<E> setRoot(Node<E> n) {
        Node<E> oldRoot = root;
        NodeImpl<E> newRoot = validate(n);
        newRoot.parent = null;
        root = newRoot;
        return oldRoot;
    }

    protected static class NodeImpl<E> implements Node<E> {
        private Node<E> parent;
        private Node<E> leftChild;
        private Node<E> rightChild;
        private E data;

        public NodeImpl(E data) {
            this.data = data;
        }

        @Override
        public E getElement() {
            return data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (isNull(o)) {
                return false;
            }
            if (!(o instanceof Node)) {
                return false;
            }
            Node<E> node = (Node<E>) o;
            return Objects.equals(data, node.getElement());
        }

        @Override
        public int hashCode() {
            return Objects.hash(data);
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }
}
