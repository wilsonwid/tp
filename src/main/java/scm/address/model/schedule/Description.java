package scm.address.model.schedule;

/**
 * Represents a Schedule's description in the address book.
 */
public class Description {
    final String description;
    public Description(String description) {
        this.description = description;
    }

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

    @Override
    public String toString() {
        return description;
    }
}
