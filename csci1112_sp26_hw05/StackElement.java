/*--------------------------------------------------------------------------
GWU CSCI 1112 Spring 2026
author: <your name>, Charles Peeke 


TODO - Summarize this class

--------------------------------------------------------------------------*/

public class StackElement {
    // TODO:
    public String value;
    public StackElement next;

    /// TODO:
    public StackElement(String value) {
        this.value = value;
        this.next = null;
    }

    /// TODO:
    @Override
    public String toString() {
        return "StackElement [value=" + value + ", next=" + next + "]";
    }
}
