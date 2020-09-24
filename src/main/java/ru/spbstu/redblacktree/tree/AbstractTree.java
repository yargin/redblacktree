package ru.spbstu.redblacktree.tree;

import java.util.*;

import static java.util.Objects.isNull;

/**
 * an abstract base class providing some functionality of the Tree interface
 *
 * @param <E> element
 */
public abstract class AbstractTree<E> implements Tree<E> {
    @Override
    public boolean isRoot(Node<E> n) {
        return n == root();
    }

    @Override
    public Iterator<E> iterator() {
        return new ElementIterator();
    }

    /**
     * @return an iterable collection of nodes of the tree in preOrder
     */
    public Collection<Node<E>> preOrder() {
        List<Node<E>> nodes = new ArrayList<>();
        if (isNull(root())) {
            return nodes;
        }
        return preOrder(root(), nodes);
    }

    private Collection<Node<E>> preOrder(Node<E> node, Collection<Node<E>> nodes) {
        if (isNull(node)) {
            return nodes;
        }
        nodes.add(node);
        for (Node<E> n : children(node)) {
            preOrder(n, nodes);
        }
        return nodes;
    }

    /**
     * @return an iterable collection of nodes of the tree in postOrder
     */
    public Collection<Node<E>> postOrder() {
        List<Node<E>> nodes = new ArrayList<>();
        if (isNull(root())) {
            return nodes;
        }
        return postOrder(root(), nodes);
    }

    private Collection<Node<E>> postOrder(Node<E> node, Collection<Node<E>> nodes) {
        if (isNull(node)) {
            return nodes;
        }
        for (Node<E> n : children(node)) {
            postOrder(n, nodes);
        }
        nodes.add(node);
        return nodes;
    }

    /**
     * @return an iterable collection of nodes of the tree in breadth-first order
     */
    public Collection<Node<E>> breadthFirst() {
        Queue<Node<E>> queue = new ArrayDeque<>();
        Collection<Node<E>> nodes = new ArrayList<>();
        if (isNull(root())) {
            return nodes;
        }
        queue.offer(root());
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            nodes.add(node);
            queue.addAll(children(node));
        }
        return nodes;
    }

    /**
     * adapts the iteration produced by {@link Tree#nodes()}
     */
    private class ElementIterator implements Iterator<E> {
        private final Iterator<Node<E>> iterator = nodes().iterator();

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public E next() {
            return iterator.next().getElement();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
