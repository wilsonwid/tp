package scm.address.model.theme;

/**
 * Contains the collection of themes available in the application.
 */
public class ThemeCollection {
    public static final String MESSAGE_INVALID_THEME_NAME = "Invalid theme name provided.";
    private static final Theme darkTheme = new DarkTheme();
    private static final Theme lightTheme = new LightTheme();

    public static Theme getDarkTheme() {
        return darkTheme;
    }

    public static Theme getLightTheme() {
        return lightTheme;
    }

    public static Theme getTheme(String themeName) throws IllegalArgumentException {
        String temp = themeName.toLowerCase();

        if (temp.equals(darkTheme.getThemeName())) {
            return darkTheme;
        } else if (temp.equals(lightTheme.getThemeName())) {
            return lightTheme;
        } else {
            throw new IllegalArgumentException(MESSAGE_INVALID_THEME_NAME);
        }
    }
}
