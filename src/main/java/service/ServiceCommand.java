package service;


/**
 * Interface to define the behaviour of a Command pattern.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created 2025-02-24
 */
@FunctionalInterface
public interface ServiceCommand<T> {
    T execute();
}
