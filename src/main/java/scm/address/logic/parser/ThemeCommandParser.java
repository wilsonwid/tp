package scm.address.logic.parser;

import scm.address.logic.commands.ThemeCommand;
import scm.address.logic.parser.exceptions.ParseException;

import static scm.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new ThemeCommand object.
 */
public class ThemeCommandParser implements Parser<ThemeCommand> {
    @Override
    public ThemeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);
        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE));
        }

        String themeValue = argMultimap.getPreamble().trim();

        String theme = "";
        try {
            theme = parseTheme(themeValue);
        } catch (ParseException e) {
            throw new ParseException((ThemeCommand.MESSAGE_INVALID_THEME + "\n" + ThemeCommand.MESSAGE_USAGE));
        }
        return new ThemeCommand(theme);
    }

    /**
     * Parses the theme value and returns the theme.
     *
     * @param theme The theme value.
     * @return The theme.
     * @throws ParseException If the theme value is invalid.
     */
    private String parseTheme(String theme) throws ParseException {
        if (theme.equals("light") || theme.equals("dark")) {
            return theme;
        } else {
            throw new ParseException("Invalid theme value");
        }
    }
}
