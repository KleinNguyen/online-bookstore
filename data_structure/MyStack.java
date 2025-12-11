package data_structure;

public class MyStack<T> {
    private int capacity = 10;
    @SuppressWarnings("unchecked")
    private T stack[] = (T[]) new Object[capacity];
    private int top = 0;

    public void push(T data){
        if(top >= capacity){
            grow();
        }
        stack[top] = data;
        top++;
    }

    public T pop(){
    if(isEmpty()){
        throw new RuntimeException("Stack is empty");
    }
    top--;
    T data = stack[top];
    stack[top] = null;
    if(capacity > 10 && top <= capacity / 3){
        shrink();
    }

    return data;
    }

    public T peek(){
    if(isEmpty()) {
        throw new RuntimeException("Stack is empty");
    }
    return stack[top - 1];
    }

    public boolean isEmpty(){
        return top == 0;
    }

    @SuppressWarnings("unchecked")
    public void grow(){
        int newCapacity = (int)(capacity * 2);
        T[] newStack = (T[]) new Object[newCapacity];
        for(int i = 0; i < top; i++){
            newStack[i] = stack[i];
        }
        capacity = newCapacity;
        stack = newStack;
    }

    @SuppressWarnings("unchecked")
    public void shrink(){
        int newCapacity = Math.max(10, capacity / 2);
        if(newCapacity < top) return; 
        T[] newStack = (T[]) new Object[newCapacity];
        for(int i = 0; i < top; i++){
            newStack[i] = stack[i];
        }
        capacity = newCapacity;
        stack = newStack;
    }

    public String toString(){
        if (top == 0) return "[]";

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < top; i++) {
            sb.append(stack[i]);
            if (i < top - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
