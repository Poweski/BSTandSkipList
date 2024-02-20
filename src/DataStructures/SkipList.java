package DataStructures;

import java.util.Comparator;
import java.util.Random;

public class SkipList<T> {

    private static class Node<T> {
        private final T value;
        private final Node<T>[] next;

        @SuppressWarnings("unchecked")
        private Node(T value, int level) {
            this.value = value;
            this.next = new Node[level];
        }
    }

    private Node<T> head;
    private final double p;
    private final Random random = new Random();
    private final Comparator<T> comparator;

    public SkipList(double p, Comparator<T> comparator) {
        this.p = p;
        this.head = new Node<>(null, 1);
        this.comparator = comparator;
    }

    public void clear() {
        this.head = new Node<>(null,1);
    }

    public T search(T value) {
        return searchRecursive(head, value, head.next.length - 1);
    }

    private T searchRecursive(Node<T> current, T value, int level) {
        if (level < 0) {
            current = current.next[0];

            if (current != null && comparator.compare(current.value, value) == 0) {
                return current.value;
            }
            else {
                return null;
            }
        }

        while (current.next[level] != null && comparator.compare(current.next[level].value, value) < 0) {
            current = current.next[level];
        }

        return searchRecursive(current, value,level - 1);
    }

    public T remove(T value) {
        Node<T> result = removeRecursive(head, value, head.next.length - 1);
        int counter = 0;

        for (int i = head.next.length - 1; i >= 0; i--) {
            if (head.next[i] == null) {
                counter++;
            }
        }

        fixHeadRemove(counter);

        return result != null ? result.value : null;
    }

    private Node<T> removeRecursive(Node<T> current, T value, int level) {

        if (level < 0) {
            current = current.next[0];
            if (current != null && comparator.compare(current.value, value) == 0) {
                return current;
            }
            else {
                return null;
            }
        }

        while (current.next[level] != null && comparator.compare(current.next[level].value, value) < 0) {
            current = current.next[level];
        }

        Node<T> result = removeRecursive(current, value, level - 1);

        if (result != null) {
            if (result.next.length-1 < level) {
                current.next[level] = null;
            }
            else {
                current.next[level] = result.next[level];
            }
        }

        return result;
    }

    public T insert(T value) {
        Node<T> newNode = new Node<>(value, generateRandomLevel());
        insertRecursive(head, newNode, head.next.length - 1);

        if (newNode.next.length - head.next.length > 0) {
            fixHeadInsert(newNode);
        }

        return value;
    }

    private Node<T> insertRecursive(Node<T> current, Node<T> newNode, int level) {
        if (level < 0) {
            if (current.next[0] != null && comparator.compare(current.next[0].value, newNode.value) == 0) {
                throw new IllegalArgumentException("A node with the given value already exists!");
            }
            else {
                return current;
            }
        }

        while (current.next[level] != null && comparator.compare(current.next[level].value, newNode.value) < 0) {
            current = current.next[level];
        }

        insertRecursive(current, newNode, level - 1);

        if (newNode.next.length - 1 >= level) {
            newNode.next[level] = current.next[level];
            current.next[level] = newNode;
        }

        return current;
    }

    private void fixHeadRemove(int counter) {
        Node<T> newHead = new Node<>(null,head.next.length-counter);
        if (head.next.length - counter >= 0) {
            System.arraycopy(head.next, 0, newHead.next, 0, head.next.length - counter);
        }
        head = newHead;
    }

    private void fixHeadInsert(Node<T> newNode) {
        Node<T> newHead = new Node<>(null,newNode.next.length);
        System.arraycopy(head.next, 0, newHead.next, 0, head.next.length);
        for (int i = newNode.next.length - 1; i >= 0; i--) {
            if (newHead.next[i] == null) {
                newHead.next[i] = newNode;
            }
        }
        head = newHead;
    }

    private int generateRandomLevel() {
        int newLevel = 1;
        while (random.nextDouble() < p) {
            newLevel++;
        }
        return newLevel;
    }
}