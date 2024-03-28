package scm.address.model.schedule;

/**
 * Represents a Schedule's title in the address book.
 */
public class Title {
    final String title;
    public Title(String title) {
        this.title = title;
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

    @Override
    public int hashCode() {
        return title.hashCode();
    }
}
