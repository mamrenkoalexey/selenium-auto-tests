package basic.functions;

import org.openqa.selenium.WebDriver;

import static basic.config.Config.URL;

public class DriverFunctions {

    public static void openURL(WebDriver driver) {
        driver.get(URL);
    }
}
