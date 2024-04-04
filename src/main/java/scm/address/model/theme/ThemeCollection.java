package scm.address.model.theme;

/**
 * Contains the collection of themes available in the application.
 */
public class ThemeCollection {
    private static final Theme darkTheme = new DarkTheme();
    private static final Theme lightTheme = new LightTheme();

    public static Theme getDarkTheme() {
        return darkTheme;
    }

    public static Theme getLightTheme() {
        return lightTheme;
    }
}
