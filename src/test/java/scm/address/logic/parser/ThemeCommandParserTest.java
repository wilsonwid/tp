package scm.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import scm.address.logic.commands.ThemeCommand;
import scm.address.logic.parser.exceptions.ParseException;
import scm.address.model.theme.Theme;


public class ThemeCommandParserTest {

    private ThemeCommandParser themeCommandParser = new ThemeCommandParser();

    @Test
    void parse_validTheme_success() throws ParseException {
        // Test that the dark theme is returned successfully
        ThemeCommand darkTheme = themeCommandParser.parse("dark");
        assertEquals("dark", darkTheme.getTheme().getThemeName());

        // Test that the light theme is returned successfully
        ThemeCommand lightTheme = themeCommandParser.parse("light");
        assertEquals("light", lightTheme.getTheme().getThemeName());
    }
}
