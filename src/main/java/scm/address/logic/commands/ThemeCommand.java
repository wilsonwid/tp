package scm.address.logic.commands;

import scm.address.logic.commands.exceptions.CommandException;
import scm.address.model.Model;

public class ThemeCommand extends Command{

    public static final String COMMAND_WORD = "theme";
    public static final String MESSAGE_SUCCESS = "Theme changed";

    public ThemeCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
