package scm.address.model.theme;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Represents the Dark Theme of the application.
 */
class DarkTheme implements Theme {
    private static final String DARK_THEME_NAME = "dark";
    private static final String DARK_THEME_CSS_PATH = "/view/DarkTheme.css";
    private static final String DARK_THEME_EXTENSIONS_CSS_PATH = "/view/Extensions.css";
    @JsonProperty
    private String theme = "dark";

    @Override
    public String getThemeName() {
        return DARK_THEME_NAME;
    }

    @Override
    public String getThemeCssPath() {
        return DARK_THEME_CSS_PATH;
    }

    @Override
    public String getThemeExtensionsCssPath() {
        return DARK_THEME_EXTENSIONS_CSS_PATH;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof DarkTheme;
    }

}
