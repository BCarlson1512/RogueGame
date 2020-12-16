package rogue;

public class NoSuchItemException extends Exception {
    /**
     * Constructor for NoSuchItem, sends no item to the superclass.
     */
    public NoSuchItemException() {
        super("No such item");
    }
}
