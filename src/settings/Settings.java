package settings;

public class Settings {
    private static String host;
    private static int port = -1;

    private static String username;
    private static String password;

    private static Theme theme = Theme.WHITE;
    private static boolean safeMode;

    public static void setHost(String host) {
        Settings.host = host;
    }

    public static String getHost() {
        return host;
    }

    public static void setPort(int port) {
        Settings.port = port;
    }

    public static int getPort() {
        return port;
    }

    public static void setUsername(String username) {
        Settings.username = username;
    }

    public static String getUsername() {
        return username;
    }

    public static void setPassword(String password) {
        Settings.password = password;
    }

    public static String getPassword() {
        return password;
    }

    public static void setTheme(Theme theme) {
        Settings.theme = theme;
    }

    public static Theme getTheme() {
        return theme;
    }

    public static void setSafeMode(boolean safeMode) {
        Settings.safeMode = safeMode;
    }

    public static boolean isSafeMode() {
        return safeMode;
    }
}
