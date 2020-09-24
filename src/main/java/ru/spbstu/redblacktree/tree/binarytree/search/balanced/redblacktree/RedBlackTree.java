package ru.spbstu.redblacktree.tree.binarytree.search.balanced.redblacktree;

import ru.spbstu.redblacktree.tree.Node;
import ru.spbstu.redblacktree.tree.binarytree.LinkedBinaryTree;
import ru.spbstu.redblacktree.tree.binarytree.search.balanced.BalanceAbleTree;

import java.util.Collection;

import static java.util.Objects.isNull;

public class RedBlackTree<E> extends BalanceAbleTree<E> {
    private boolean isBlack(Node<E> n) {
        if (isNull(n)) {
            return true;
        }
        return rbValidate(n).isBlack;
    }

    private boolean isRed(Node<E> n) {
        if (isNull(n)) {
            return false;
        }
        return !rbValidate(n).isBlack;
    }

    private void makeBlack(Node<E> n) {
        RBNode<E> rbNode = rbValidate(n);
        rbNode.isBlack = true;
    }

    private void makeRed(Node<E> n) {
        RBNode<E> rbNode = rbValidate(n);
        rbNode.isBlack = false;
    }

    private Node<E> uncle(Node<E> n) {
        return sibling(parent(n));
    }

    protected void afterElementAdded(Node<E> n) {
        Node<E> parent = parent(n);
        if (isNull(parent)) {
            makeBlack(n);
            return;
        } else if (isBlack(parent)) {
            return;
        }

        Node<E> grandParent = parent(parent);
        Node<E> uncle = uncle(n);

        if (n == left(parent) && parent == left(grandParent) || n == right(parent) && parent == right(grandParent)) {
            makeBlack(parent);
        } else {
            makeBlack(n);
        }

        makeRed(grandParent);

        if (isNull(uncle) || isBlack(uncle)) {
            reduceSubtreeHeight(n);
        } else {
            makeBlack(uncle);
            afterElementAdded(grandParent);
        }
    }

    @Override
    public Node<E> add(E val) {
        Node<E> node = super.add(val);
        Node<E> rbNode = makeNodeRB(node);
        afterElementAdded(rbNode);
        return rbNode;
    }

    @Override
    public E remove(E val) {
        Node<E> node = treeSearch(root(), val);
        if (isNull(node)) {
            return null;
        } else {
            return remove(node);
        }
    }

    @Override
    public E remove(Node<E> n) {
        Node<E> nodeToRemove = n;
        if (childrenNumber(n) == 2) {
            nodeToRemove = findLeftmost(right(n));
        }
        beforeElementRemoved(nodeToRemove);
        return super.remove(n);
    }

    /**
     * handles three cases(forth is impossible) for node deletion:
     *
     * <pre>
     *  1)  R - just delete
     *
     *      B
     *  2)   \  -  delete & change to black
     *        R
     *
     *  3)  B - other six cases handled in <i>removeBlackNode()</i>
     *
     *      R          B          R
     *  4)   \    &     \     &    \     - impossible according to red-black tree rules
     *        B          B          R
     * </pre>
     *
     * @param n node to delete
     */
    protected void beforeElementRemoved(Node<E> n) {
        if (isBlack(n)) {
            Collection<Node<E>> children = children(n);
            if (children.isEmpty()) {
                removeBlackNode(n);
            } else {
                Node<E> redChild = children.iterator().next();
                makeBlack(redChild);
            }
        }
    }

    /**
     * provides six cases tree transformation for black node deletion
     *
     * @param n node that will be deleted
     */
    private void removeBlackNode(Node<E> n) {
        //case 1 *
        if (isNull(parent(n))) {
            makeBlack(n);
            return;
        }
        RBNode<E> parent = rbValidate(parent(n));
        RBNode<E> sibling = rbValidate(sibling(n));
        Node<E> nearNephew = n == left(parent) ? left(sibling) : right(sibling);
        Node<E> fartherNephew = n == left(parent) ? right(sibling) : left(sibling);

        //case 2
        if (isRed(sibling)) {
            makeBlack(sibling);
            makeRed(parent);
            rotate(sibling);
            removeBlackNode(n);
        }

        //case 6 *
        if (isRed(fartherNephew)) {
            sibling.isBlack = parent.isBlack;
            makeBlack(fartherNephew);
            makeBlack(parent);
            rotate(sibling);
            return;
        }

        //case 5
        if (isRed(nearNephew) && isBlack(nearNephew)) {
            makeRed(sibling);
            makeBlack(nearNephew);
            rotate(nearNephew);
            removeBlackNode(n);
        }

        if (isBlack(nearNephew)) {
            if (isBlack(parent)) {
                //case 3
                makeRed(sibling);
                removeBlackNode(parent);
            } else {
                //case 4 *
                makeBlack(parent);
                makeRed(sibling);
            }
        }
    }

    /**
     * validates the node is an instance of supported {@link RBNode} type and casts to it
     *
     * @param n given node
     * @return casted {@link RBNode} node
     */
    private RBNode<E> rbValidate(Node<E> n) {
        if (n instanceof RBNode) {
            return (RBNode<E>) n;
        } else if (isNull(n)) {
            throw new IllegalArgumentException("node can't be null");
        }
        throw new IllegalArgumentException("wrong node");
    }

    /**
     * creates new red-black node based on <i>n</i>
     *
     * @param n given node
     * @return new red-black node
     */
    private RBNode<E> makeNodeRB(Node<E> n) {
        RBNode<E> rbNode = new RBNode<>(n.getElement());
        setRight(rbNode, right(n));
        setLeft(rbNode, left(n));
        Node<E> parent = parent(n);

        if (root() == n) {
            setRoot(rbNode);
        } else if (n == left(parent)) {
            setLeft(parent, rbNode);
        } else {
            setRight(parent, rbNode);
        }

        return rbNode;
    }

    protected static class RBNode<E> extends LinkedBinaryTree.NodeImpl<E> {
        private boolean isBlack;

        public RBNode(E element) {
            super(element);
        }

        @Override
        public String toString() {
            return (isBlack ? "" + getElement() : "(" + getElement() + ')');
        }
    }
}
