package scm.address.model.schedule;

/**
 * Represents a Schedule's title in the address book.
 */
public class Title {
    /** Informs the user of the constriants for valid titles. **/
    public static final String MESSAGE_CONSTRAINTS = "Titles should be alphanumeric and cannot be empty. "
            + "They can contain a space only after the first character.";

    /** The regular expression to be used for determining whether a Title is valid. **/
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String title;

    /**
     * Constructs a new Title instance.
     *
     * @param title The title to be added.
     */
    public Title(String title) {
        this.title = title;
    }

    /**
     * Returns whether the given {@code title} is a valid title.
     *
     * @param title The title to be checked.
     * @return A boolean value.
     */
    public static boolean isValidTitle(String title) {
        return title.matches(VALIDATION_REGEX);
    }

    /**
     * Returns whether this instance is equal to another.
     * Two instances of Titles are equal only if the title equals the other title.
     *
     * @param other The other Object to be compared against.
     * @return A boolean value.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other instanceof Title) {
            Title otherTitle = (Title) other;
            return title.equals(otherTitle.title);
        }

        return false;
    }

    /**
     * Returns the String representation of this object, which is its title.
     *
     * @return A String.
     */
    @Override
    public String toString() {
        return title;
    }
}
