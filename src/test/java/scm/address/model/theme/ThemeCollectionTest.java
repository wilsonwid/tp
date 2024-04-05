package scm.address.model.theme;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ThemeCollectionTest {



    @Test
    void getDarkTheme_validTheme_success() {
        // Test that the dark theme is returned successfully
        Theme darkTheme = ThemeCollection.getDarkTheme();
        assertEquals("dark", darkTheme.getThemeName());
    }

    @Test
    void getLightTheme_validTheme_success() {
        // Test that the light theme is returned successfully
        Theme lightTheme = ThemeCollection.getLightTheme();
        assertEquals("light", lightTheme.getThemeName());
    }

    @Test
    void getTheme_validTheme_success() {
        // Test that the dark theme is returned successfully
        Theme darkTheme = ThemeCollection.getTheme("dark");
        assertEquals("dark", darkTheme.getThemeName());

        // Test that the light theme is returned successfully
        Theme lightTheme = ThemeCollection.getTheme("light");
        assertEquals("light", lightTheme.getThemeName());
    }

    @Test
    void getTheme_invalidTheme_throwsIllegalArgumentException() {
        // Test that an invalid theme name throws an IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
                -> ThemeCollection.getTheme("invalid"));
        assertEquals(ThemeCollection.MESSAGE_INVALID_THEME_NAME, exception.getMessage());
    }
}
