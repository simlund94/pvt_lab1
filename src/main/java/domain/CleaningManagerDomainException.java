package domain;

/**
 * An exception to signal that a runtime error has occurred at the domain level of this application.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-03-05
 */
public class CleaningManagerDomainException extends RuntimeException {
    public CleaningManagerDomainException(String message) {
        super(message);
    }
}
