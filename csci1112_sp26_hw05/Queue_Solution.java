/*--------------------------------------------------------------------------
GWU CSCI 1112 Spring 2026
author: <your name>, Charles Peeke 


TODO - Summarize this class

--------------------------------------------------------------------------*/

public class Queue {
    // TODO:
    private QueueElement front;
    private QueueElement back;
    private int count;

    /// TODO:
    public Queue() {
        this.front = null;
        this.back = null;
    }

    /// TODO:
    public void enqueue(Transaction t) {
        // Create new QueueElement with transaction t
        QueueElement newElement = new QueueElement(t);
        // Add new element to back of queue
        if (this.front == null) {
            this.front = newElement;
            this.back = newElement;
        }
        else {
            this.back.next = newElement;
            this.back = newElement;
        }
        // Increment count
        this.count++;
    }

    /// TODO:
    public Transaction dequeue() {
        if (this.front == null) {
            return null;
        }

        // Remove transaction from front of queue
        Transaction value = this.front.value;
        this.front = this.front.next;
        // Decrement count
        this.count--;
        return value;
    }

    /// TODO:
    public boolean isEmpty() {
        // Return true if the queue is empty
        return (this.front == null);
        // return (this.count == 0);
    }

    /// TODO:
    public int size() {
        // TODO
        return this.count;
    }

    /// TODO:
    @Override
    public String toString() {
        // TODO
        String s = "";
        QueueElement current = this.front;
        while (current != null) {
            s += current.value.toString() + ";";
            current = current.next;
        }
        return s;
    }

}
