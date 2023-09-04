package DataStructures.BST;

import java.util.Comparator;

public class BST<T> {

    class Node {
        T key;
        Node left, right;
        Node(T value) {
            key = value;
            left = right = null;
        }
    }

    private final Comparator<T> comparator;

    private Node root;

    public BST(Comparator<T> comparator) {
        root = null;
        this.comparator = comparator;
    }

    public void clear() {
        root = null;
    }

    public void insert(T value) {

        Node newNode = new Node(value);
        Node current = root;
        Node parent = null;

        while (current != null)
        {
            parent = current;

            if (comparator.compare(value,current.key) < 0)
                current = current.left;
            else
                current = current.right;
        }
        if (parent == null)
            root = newNode;
        else
        {
            if (comparator.compare(value,parent.key) < 0)
                parent.left = newNode;
            else
                parent.right = newNode;
        }
    }

    public boolean delete(T value) {

        Node current = root;
        Node parent = null;
        boolean isLeftChild = false;

        while (current != null)
        {

            if (comparator.compare(value,current.key) == 0)
            {
                if (current == root)
                    root = deleteNode(current);
                else
                {
                    if (isLeftChild)
                        parent.left = deleteNode(current);
                    else
                        parent.right = deleteNode(current);
                }
                return true;
            }
            else if (comparator.compare(value,current.key) < 0)
            {
                parent = current;
                current = current.left;
                isLeftChild = true;
            }
            else
            {
                parent = current;
                current = current.right;
                isLeftChild = false;
            }
        }
        return false;
    }

    private Node deleteNode(Node node) {

        // Przypadek 1: Usuwamy liść
        if (node.left == null && node.right == null)
            return null;

        // Przypadek 2: Usuwamy węzeł z jednym prawym dzieckiem
        else if (node.left == null)
            return node.right;

        // Przypadek 3: Usuwamy węzeł z jednym lewym dzieckiem
        else if (node.right == null)
            return node.left;

        // Przypadek 4: Usuwamy węzeł z dwoma dziećmi
        else
        {
            // Znajdujemy najmniejszą wartość w prawym poddrzewie
            Node successor = findMin(node.right);

            // Zamieniamy wartość usuwanego węzła z wartością następnika
            node.key = successor.key;

            // Rekurencyjnie usuwamy następnika z prawego poddrzewa
            node.right = deleteNode(successor);

            return node;
        }
    }

    private Node findMin(Node node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    public T findSuccessor(T value) {

        Node node = findRecursive(root, value);

        if (node == null)
            return null;

        if (node.right != null)
            return findMin(node.right).key;
        else
        {
            Node successor = null;
            Node current = root;

            while (current != null)
            {
                if (comparator.compare(node.key,current.key) < 0)
                {
                    successor = current;
                    current = current.left;
                }
                else if (comparator.compare(node.key,current.key) > 0)
                    current = current.right;
                else
                    break;
            }

            if (successor == null)
                return null;

            return successor.key;
        }
    }

    public T find(T key)  {
        Node node = findRecursive(root, key);
        return node == null ? null : node.key;
    }

    private Node findRecursive(Node node, T key)  {
        if (node == null || node.key == key)
            return node;
        if (comparator.compare(node.key,key) > 0)
            return findRecursive(node.left, key);
        return findRecursive(node.right, key);
    }

    public T findMin() {
        return findMinRecursive(root);
    }

    private T findMinRecursive(Node node)  {
        if (node == null)
            return null;
        if (node.left == null)
            return node.key;
        return findMinRecursive(node.left);
    }

    public T findMax() {
        return findMaxRecursive(root);
    }

    private T findMaxRecursive(Node node)  {
        if (node == null)
            return null;
        if (node.right == null)
            return node.key;
        return findMinRecursive(node.right);
    }

    public <R> void postOrderWalk(IExecutor<T,R> exec) {
        postOrderWalk(root,exec);
    }

    private <R> void postOrderWalk(Node node, IExecutor<T,R> exec) {
        if (node != null) {
            postOrderWalk(node.left, exec);
            postOrderWalk(node.right, exec);
            exec.execute(node.key);
        }
    }
}