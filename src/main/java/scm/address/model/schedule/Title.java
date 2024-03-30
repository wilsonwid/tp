package scm.address.model.schedule;

/**
 * Represents a Schedule's title in the address book.
 */
public class Title {
    public static final String MESSAGE_CONSTRAINTS = "Titles should be alphanumeric and cannot be empty.";
    private final String title;
    public Title(String title) {
        this.title = title;
    }

    /**
     * Returns whether the given {@code title} is a valid title.
     *
     * @param title The title to be checked.
     * @return A boolean value.
     */
    public boolean isValidTitle(String title) {

    }

    @Override
    public String toString() {
        return title;
    }

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
}
