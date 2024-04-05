package scm.address.model.theme;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


public class LightThemeTest {

    @Test
    void instantiateLightTheme() {
        LightTheme lightTheme = new LightTheme();
        assertEquals("light", lightTheme.getThemeName());
        assertEquals(lightTheme.getThemeName(), ThemeCollection.getLightTheme().getThemeName());
        assertEquals("/view/LightTheme.css", lightTheme.getThemeCssPath());
        assertEquals("/view/Extensions.css", lightTheme.getThemeExtensionsCssPath());
    }
}
