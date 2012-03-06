package collabrite.relationships;

public class RelationshipException extends Exception {

    private static final long serialVersionUID = 1L;

    public RelationshipException() {
    }

    public RelationshipException(String message, Throwable cause) {
        super(message, cause);
    }

    public RelationshipException(String message) {
        super(message);
    }

    public RelationshipException(Throwable cause) {
        super(cause);
    }
}