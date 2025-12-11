package data_structure;

import java.util.ArrayList;

public class MyQueueArrayList<T> {
    private ArrayList<T> list = new ArrayList<>();

    public void add(T data) { 
        list.add(data);
    }

    public boolean offer(T data) { 
        return list.add(data); 
    }

    public T remove() { 
        if (isEmpty()) throw new RuntimeException("Queue is empty");
        return list.remove(0);
    }

    public T poll() { 
        if (isEmpty()) return null;
        return list.remove(0);
    }

    public T element() { 
        if (isEmpty()) throw new RuntimeException("Queue is empty");
        return list.get(0);
    }

    public T peek() { 
        if (isEmpty()) return null;
        return list.get(0);
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

    public boolean isEmpty() {
        return list.isEmpty();
    }
    public T[] toArray(T[] arr) {
        return list.toArray(arr);
    }
    @Override
    public String toString() {
        return list.toString();
    }
}
