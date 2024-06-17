package drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;
import java.util.HashMap;

import static basic.config.Config.BROWSER;
import static basic.config.Config.BROWSER_VIEW;

public class DefaultDriver {
    public static final int DRIVER_IMPLICIT_WAIT = 3000;
    protected static WebDriver driver = null;


    public static WebDriver getDriver(String downloadPath) {
        switch (BROWSER) {
            case "Safari" -> driver = new SafariDriver();

            case "Chrome" -> {
                ChromeOptions options = new ChromeOptions();

                if (BROWSER_VIEW.equals("headless")) {
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");
                    options.addArguments("--headless");
                }

                HashMap<String, Object> chromePref = new HashMap<>();

                chromePref.put("download.default_directory", downloadPath);
                options.setExperimentalOption("prefs", chromePref);

                driver = new ChromeDriver(options);
            }
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(DRIVER_IMPLICIT_WAIT));

        return driver;
    }
}
