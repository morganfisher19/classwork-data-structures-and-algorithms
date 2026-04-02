/*--------------------------------------------------------------------------
GWU CSCI 1112 Spring 2026
author: <your name>, Charles Peeke 


TODO - Summarize this class

--------------------------------------------------------------------------*/

public class Stack {
    // TODO:
    private StackElement top;
    private int count;
    
    /// TODO:
    public Stack() {
        this.top = null;
        this.count = 0;
    }
    
    /// TODO:
    public void push(String s) {
        // Create a new StackElement from the string
        StackElement newElement = new StackElement(s);
        // Add new element to top of stack
        newElement.next = this.top;
        this.top = newElement;
        // Increment count
        this.count++;
    }
    
    /// TODO: 
    public String pop() {
        if (this.top == null) {
            return null;
        }
        // Remove element from top of stack
        String value = this.top.value;
        this.top = this.top.next;
        // Decrement count
        this.count--;
        return value;
    }
    
    /// TODO: 
    public boolean isEmpty() {
        // Return true if the stack is empty
        return (this.top == null);
        // return this.count == 0;
    }

    /// TODO:
    public int size() {
        // Return the number of elements in the stack
        return this.count;
    }

    // TODO: 
    @Override
    public String toString() {
        String s = "";
        StackElement current = this.top;
        while (current != null) {
            s += current + " ";
            current = current.next;
        }
        return s;
    }
}


