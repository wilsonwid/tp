package scm.address.logic.commands;

import static java.util.Objects.requireNonNull;

import scm.address.commons.core.GuiSettings;
import scm.address.logic.commands.exceptions.CommandException;
import scm.address.model.Model;
import scm.address.model.theme.Theme;


/**
 * Command to change the theme of the application.
 */
public class ThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";
    public static final String MESSAGE_SUCCESS = "Theme changed";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the theme of the application.\n"
            + "Parameters: "
            + "THEME\n"
            + "Example: " + COMMAND_WORD + " light\n"
            + "Supported themes: light, dark";
    public static final String MESSAGE_INVALID_THEME = "Invalid theme value";
    private final Theme theme;

    /**
     * Creates a ThemeCommand to change the theme of the application.
     *
     * @param theme The theme to change to.
     */
    public ThemeCommand(Theme theme) {
        assert theme != null;
        this.theme = theme;
    }

    public Theme getTheme() {
        return theme;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        GuiSettings guiSettings = model.getGuiSettings();

        model.setGuiSettings(new GuiSettings(guiSettings.getWindowWidth(), guiSettings.getWindowHeight(),
                guiSettings.getWindowCoordinates().x, guiSettings.getWindowCoordinates().y, theme.getThemeName()));

        return new CommandResult(String.format(MESSAGE_SUCCESS), false, false, true);
    }
}
