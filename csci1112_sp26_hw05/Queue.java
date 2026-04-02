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
        this.count = 0;
    }

    /// TODO:
    public void enqueue(Transaction t) {
        QueueElement element = new QueueElement(t);
        if (this.front == null) {
            this.front = element;
            this.back = element;
        }
        else {
            back.next = element;
            back = element;
        }
        count += 1;
    }

    /// TODO:
    public Transaction dequeue() {
        if (front.next == null) {
            return null;
        }
        Transaction removed = this.front.value;
        front = front.next;
        count -= 1;
        return removed;
    }

    /// TODO:
    public boolean isEmpty() {
        if (this.front == null) {
            return true;
        }
        return false;
    }

    /// TODO:
    public int size() {
        return count;
    }

    /// TODO:
    @Override
    public String toString() {
        String s = "";
        QueueElement current = this.front;
        while (current != null) {
            s += current.value.toString() + ";";
            current = current.next;
        }
        return s;
    }

}
