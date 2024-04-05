package scm.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static scm.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import scm.address.commons.core.GuiSettings;
import scm.address.logic.commands.exceptions.CommandException;
import scm.address.model.Model;
import scm.address.model.ModelManager;
import scm.address.model.ScheduleList;
import scm.address.model.UserPrefs;
import scm.address.model.theme.Theme;
import scm.address.model.theme.ThemeCollection;

public class ThemeCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ScheduleList());

    @Test
    void execute_darkTheme_success() throws CommandException {
        // Set the initial theme to dark
        model.setGuiSettings(new GuiSettings(100, 0, 0, 0, ThemeCollection.getLightTheme().getThemeName()));

        // Test that the theme is changed successfully
        Theme theme = ThemeCollection.getDarkTheme();
        ThemeCommand themeCommand = new ThemeCommand(theme);
        System.out.println(model.getGuiSettings().getWindowCoordinates());
        CommandResult commandResult = themeCommand.execute(model);
        assertEquals(String.format(ThemeCommand.MESSAGE_SUCCESS, theme.getThemeName()),
                commandResult.getFeedbackToUser());
    }

    @Test
    void execute_lightTheme_success() throws CommandException {
        // Set the initial theme to dark
        model.setGuiSettings(new GuiSettings(100, 0, 0, 0, ThemeCollection.getDarkTheme().getThemeName()));

        // Test that the theme is changed successfully
        Theme theme = ThemeCollection.getLightTheme();
        ThemeCommand themeCommand = new ThemeCommand(theme);
        CommandResult commandResult = themeCommand.execute(model);
        assertEquals(String.format(ThemeCommand.MESSAGE_SUCCESS, theme.getThemeName()),
                commandResult.getFeedbackToUser());
    }

    @Test
    void instantiate_nullTheme_failure() {
        assertThrows(AssertionError.class, () -> new ThemeCommand(null));
    }
}
