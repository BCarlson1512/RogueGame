package rogue;

public class ImpossiblePositionException extends Exception {
    /**
     * Impossible room constructor, sends impossible position to the superclass.
     */
    public ImpossiblePositionException() {
        super("Impossible position");
    }
}
