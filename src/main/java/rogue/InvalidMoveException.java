package rogue;

public class InvalidMoveException extends Exception {
    /**
     * Default.
     */
    public InvalidMoveException() {
        super("Invalid move");
    }
    /**
     * Takes a message and sends it into the exception superclass.
     * @param message error message
     */
    public InvalidMoveException(String message) {
        super(message);
    }

}
