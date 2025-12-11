package data_structure;

import java.util.LinkedList;

public class MyQueueLinkedList<T> {
    private LinkedList<T> list = new LinkedList<>();

    public void add(T data) {
        list.addLast(data);
    }

    public boolean offer(T data) {
        list.addLast(data);
        return true;
    }

    public T remove() {
        if (isEmpty()) throw new RuntimeException("Queue is empty");
        return list.removeFirst();
    }

    public T poll() {
        if (isEmpty()) return null;
        return list.removeFirst();
    }

    public T element() {
        if (isEmpty()) throw new RuntimeException("Queue is empty");
        return list.getFirst();
    }

    public T peek() {
        if (isEmpty()) return null;
        return list.getFirst();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }

    public void clear() {
        list.clear();
    }

    public boolean contains(T data) {
        return list.contains(data);
    }
    public T[] toArray(T[] arr) {
        return list.toArray(arr);
    }
    @Override
    public String toString() {
        return list.toString();
    }
}
