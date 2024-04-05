package scm.address.logic.parser;

import static scm.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import scm.address.logic.commands.ThemeCommand;
import scm.address.logic.parser.exceptions.ParseException;
import scm.address.model.theme.Theme;
import scm.address.model.theme.ThemeCollection;


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

        Theme theme;
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
    private Theme parseTheme(String theme) throws ParseException {
        String temp = theme.trim().toLowerCase();

        switch (temp) {
        case "light":
            return ThemeCollection.getLightTheme();
        case "dark":
            return ThemeCollection.getDarkTheme();
        default:
            //Message does not matter as exception is caught and rethrown
            throw new ParseException(ThemeCommand.MESSAGE_INVALID_THEME);
        }
    }
}
