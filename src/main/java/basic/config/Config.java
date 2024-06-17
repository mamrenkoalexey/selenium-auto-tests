package basic.config;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    private static final Dotenv dotenv = Dotenv.load();

    public static final String URL = dotenv.get("URL");
    public static final String EMAIL = dotenv.get("EMAIL");
    public static final String PASSWORD = dotenv.get("PASSWORD");
    public static final String BROWSER = dotenv.get("BROWSER");
    public static final String BROWSER_VIEW = dotenv.get("BROWSER_VIEW");
    public static final Boolean BROWSER_DOWNLOAD_TESTS = false;
    public static final Integer BROWSER_DOWNLOAD_TIMEOUT = 60_000;
    public static final String SLACK_TOKEN = dotenv.get("SLACK_TOKEN");

    public static final Integer MAX_RETRIES = 5;

    public static String DefaultLocationName() {
        return dotenv.get("DEFAULT_LOCATION_NAME");
    }
}
