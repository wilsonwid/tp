package scm.address.model.theme;

/**
 * Represents the Light Theme of the application.
 */
class LightTheme implements Theme {
    private static final String LIGHT_THEME_NAME = "light";
    private static final String LIGHT_THEME_CSS_PATH = "/view/LightTheme.css";
    private static final String LIGHT_THEME_EXTENSIONS_CSS_PATH = "/view/Extensions.css";


    @Override
    public String getThemeName() {
        return LIGHT_THEME_NAME;
    }

    @Override
    public String getThemeCssPath() {
        return LIGHT_THEME_CSS_PATH;
    }

    @Override
    public String getThemeExtensionsCssPath() {
        return LIGHT_THEME_EXTENSIONS_CSS_PATH;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof LightTheme;
    }
}
