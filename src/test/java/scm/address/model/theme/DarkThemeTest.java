package scm.address.model.theme;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DarkThemeTest {

    @Test
    void instantiateDarkTheme() {
        DarkTheme darkTheme = new DarkTheme();
        assertEquals("dark", darkTheme.getThemeName());
        assertEquals(darkTheme.getThemeName(), ThemeCollection.getDarkTheme().getThemeName());
        assertEquals("/view/DarkTheme.css", darkTheme.getThemeCssPath());
        assertEquals("/view/Extensions.css", darkTheme.getThemeExtensionsCssPath());
    }
}
