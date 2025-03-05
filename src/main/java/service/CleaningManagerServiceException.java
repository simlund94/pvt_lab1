package service;


/**
 * An exception to signal that a runtime exception has been thrown at the service layer of this application.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created 2025-02-24
 */
public class CleaningManagerServiceException extends RuntimeException {

    public CleaningManagerServiceException(String message) {
        super(message);
    }
}
