package scm.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_FILENAME = new Prefix("f/");
    public static final Prefix PREFIX_TITLE = new Prefix("title/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_START_DATETIME = new Prefix("start/");
    public static final Prefix PREFIX_END_DATETIME = new Prefix("end/");

    public static final Prefix PREFIX_BEFORE_DATETIME = new Prefix("before/");
    public static final Prefix PREFIX_AFTER_DATETIME = new Prefix("after/");
    public static final Prefix PREFIX_DURING_DATETIME = new Prefix("during/");
}
