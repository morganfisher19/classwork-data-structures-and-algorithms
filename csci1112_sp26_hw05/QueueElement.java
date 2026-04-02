/*--------------------------------------------------------------------------
GWU CSCI 1112 Spring 2026
author: <your name>, Charles Peeke 


TODO - Summarize this class

--------------------------------------------------------------------------*/

public class QueueElement {
    // TODO:
    public Transaction value;
    public QueueElement next;

    /// TODO:
    public QueueElement(Transaction value) {
        this.value = value;
        this.next = null;
    }

    /// TODO:
    @Override
    public String toString() {
        return "QueueElement [value=" + value + ", next=" + next + "]";
    }
}
