package scm.address.logic.commands;

import scm.address.logic.commands.exceptions.CommandException;
import scm.address.model.Model;

import static java.util.Objects.requireNonNull;

/**
 * Command to change the theme of the application.
 */
public class ThemeCommand extends Command{

    public static final String COMMAND_WORD = "theme";
    public static final String MESSAGE_SUCCESS = "Theme changed";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the theme of the application.\n"
            + "Parameters: "
            + "THEME\n"
            + "Example: " + COMMAND_WORD + " light\n"
            + "Supported themes: light, dark";
    public static final String MESSAGE_INVALID_THEME = "Invalid theme value";
    private final String themeName;

    /**
     * Creates a ThemeCommand to change the theme of the application.
     *
     * @param themeName The name of the theme to change to.
     */
    public ThemeCommand(String themeName) {
        assert themeName != null;
        this.themeName = themeName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.setTheme(themeName);
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
