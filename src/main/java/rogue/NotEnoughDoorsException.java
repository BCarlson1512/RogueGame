package rogue;

public class NotEnoughDoorsException extends Exception {
    /**
     * Base constructor sends "Not enough doors to the exception superclass".
     */
    public NotEnoughDoorsException() {
        super("Not enough doors");
    }
}
