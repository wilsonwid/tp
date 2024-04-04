package scm.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import scm.address.logic.commands.ThemeCommand;
import scm.address.logic.parser.exceptions.ParseException;
import scm.address.model.theme.ThemeCollection;


public class ThemeCommandParserTest {
    private static final String DARK_THEME_NAME = ThemeCollection.getDarkTheme().getThemeName();
    private static final String LIGHT_THEME_NAME = ThemeCollection.getLightTheme().getThemeName();
    private static final String INVALID_THEME_NAME = "invalid";
    private ThemeCommandParser themeCommandParser = new ThemeCommandParser();

    @Test
    void parse_validTheme_success() throws ParseException {
        // Test that the dark theme is returned successfully
        ThemeCommand darkTheme = themeCommandParser.parse(DARK_THEME_NAME);
        assertEquals(DARK_THEME_NAME, darkTheme.getTheme().getThemeName());

        // Test that the light theme is returned successfully
        ThemeCommand lightTheme = themeCommandParser.parse(LIGHT_THEME_NAME);
        assertEquals(LIGHT_THEME_NAME, lightTheme.getTheme().getThemeName());
    }

    @Test
    void parse_validThemeWithUpperCase_success() throws ParseException {
        // Test that the dark theme is returned successfully
        ThemeCommand darkTheme = themeCommandParser.parse(DARK_THEME_NAME.toUpperCase());
        assertEquals(DARK_THEME_NAME, darkTheme.getTheme().getThemeName());

        // Test that the light theme is returned successfully
        ThemeCommand lightTheme = themeCommandParser.parse(LIGHT_THEME_NAME.toUpperCase());
        assertEquals(LIGHT_THEME_NAME, lightTheme.getTheme().getThemeName());
    }

    @Test
    void parse_invalidTheme_throwsParseException() {
        // Test that an invalid theme name throws a ParseException
        assertThrows(ParseException.class, () -> themeCommandParser.parse(INVALID_THEME_NAME));
    }

    @Test
    void parse_nullTheme_throwsNullPointerException() {
        // Test that a null theme name throws a NullPointerException
        assertThrows(NullPointerException.class, () -> themeCommandParser.parse(null));
    }
}
