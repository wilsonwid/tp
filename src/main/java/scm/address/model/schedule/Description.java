package scm.address.model.schedule;

/**
 * Represents a Schedule's description in the address book.
 */
public class Description {
    /** Informs the user of the constraints for valid descriptions. **/
    public static final String MESSAGE_CONSTRAINTS = "Descriptions should be alphanumeric and cannot be empty. "
            + "They can contain a space only after the first character";

    /** The regular expression to be used for determinig whether a Description is valid. **/
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String description;

    /**
     * Constructs a new Description instance.
     *
     * @param description The description to be added.
     */
    public Description(String description) {
        this.description = description;
    }

    /**
     * Returns whether the given {@code description} is a valid description.
     *
     * @param description The description to be checked.
     * @return A boolean value.
     */
    public static boolean isValidDescription(String description) {
        return description.matches(VALIDATION_REGEX);
    }

    /**
     * Returns whether this instance is equal to another.
     * Two instances of Descriptions are equal only if their descriptions are equal.
     *
     * @param other The other Object to be compared against.
     * @return A boolean value.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other instanceof Description) {
            Description otherDescription = (Description) other;
            return description.equals(otherDescription.description);
        }

        return false;
    }

    /**
     * Returns the String representation of this object, which is its description.
     *
     * @return A String.
     */
    @Override
    public String toString() {
        return description;
    }
}
