package scm.address.logic.commands;

import scm.address.logic.commands.exceptions.CommandException;
import scm.address.model.Model;

import static java.util.Objects.requireNonNull;

public class ThemeCommand extends Command{

    public static final String COMMAND_WORD = "theme";
    public static final String MESSAGE_SUCCESS = "Theme changed";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the theme of the application.\n"
            + "Parameters: "
            + "THEME\n"
            + "Example: " + COMMAND_WORD + " light";

    private final String themeName;

    public ThemeCommand(String themeName) {
        assert themeName != null;
        this.themeName = themeName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.setTheme(themeName);
        return null;
    }
}
