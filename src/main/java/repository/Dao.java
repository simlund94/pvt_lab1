package repository;

import java.util.List;

/**
 * An interface for DAO classes to streamline the most common DAO-operations.
 *
 * @param <T> The type of object which the DAO class should handle.
 */
public interface Dao<T> {

    /**
     * Saves the object of the parameters type to the database table, and returns
     * the same object.
     *
     * @param toSave The object to save
     * @return The saved object
     */
    T save(T toSave);

    /**
     * Updates the information of the object in the database table to match the passed object.
     *
     * @param toUpdate The object to update
     * @return The updated object
     */
    T update(T toUpdate);

    /**
     * Deletes the object of the parameters type, matching the object id, in the table.
     *
     * @param toDelete The object to delete
     * @return The deleted object
     */
    T delete(T toDelete);

    /**
     * Retrieves an object from the table, matching the passed id corresponding to its primary key.
     *
     * @param id The primary key in the database
     * @return The object matching the id
     */
    T get(int id);

    /**
     * Retrieves all objects of the parameters type from the table.
     *
     * @return A list of all objects in the table.
     */
    List<T> getAll();
}
