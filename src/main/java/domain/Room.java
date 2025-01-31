package domain;

/**
 * A class representing a room or a locale, for the purpose of a cleaning management system.
 *
 * <p> This class provides basic fields like id, size in square meters and a short textual description.
 * The fields {@code id} and {@code size} are immutable, while {@code description} can be modified after instantiation.
 */
public class Room {

    /**
     * Id is immutable, as it's the primary key in the database and should not be altered.
     */
    private final int id;

    /**
     * Size is immutable, since a change in room size probably indicates some kind of structural
     * change, and thus it's better to create a new room record if this is this case.
     */
    private final double sizeInSqm;

    /**
     * Descriptor is mutable, since a room can change its purpose.
     */
    private String description;

    /**
     * Constructor used to create a Room object with a given id.
     *
     * @param id          The rooms unique identifier, a non-negative integer
     * @param sizeInSqm   The size of the room in square meters, a non-negative double
     * @param description A short descriptor of the room. Must be non-null, non-empty and max 50 characters long
     * @throws IllegalArgumentException If {@code id} is negative, {@code size} is negative or {@code description}
     *                                  is invalid.
     */
    public Room(int id, double sizeInSqm, String description) {
        if (id < 0) {
            throw new IllegalArgumentException("Id must be a positive integer");
        }
        this.id = id;

        if (sizeInSqm < 0) {
            throw new IllegalArgumentException("Size cannot be a negative number");
        }
        this.sizeInSqm = sizeInSqm;

        setDescription(description);
    }

    /**
     * Constructor used to create a Room object without a specified id (defaults to 0).
     * Intended for creating new room records in the database, where the ID is automatically assigned.
     *
     * @param sizeInSqm   The size of the room in square meters
     * @param description A short description of the room. Must be non-null, non-empty and max 50 characters long
     * @throws IllegalArgumentException If {@code id} is negative, {@code size} is negative or {@code description}
     *                                  is invalid.
     */
    public Room(double sizeInSqm, String description) {
        this(0, sizeInSqm, description);
    }

    /**
     * Sets the description field of the Room. Should be a short descriptor like "Cleaning Cabinet", etc.
     *
     * @param description The description, cannot be null or empty. Max 50 characters long.
     */
    public void setDescription(String description) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty or null");
        }
        if (description.length() > 50) {
            throw new IllegalArgumentException("Description is too long: Max 50 characters");
        }
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public double getSizeInSqm() {
        return sizeInSqm;
    }

    public int getId() {
        return id;
    }

    /**
     * Description of the object and its state, only for demo and debugging.
     */
    @Override
    public String toString() {
        return String.format("ID: %d, Description: \"%s\", Size: %.2f sqm",
                this.id, this.description, this.sizeInSqm);
    }
}
