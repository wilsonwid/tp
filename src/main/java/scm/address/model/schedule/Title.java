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
}
