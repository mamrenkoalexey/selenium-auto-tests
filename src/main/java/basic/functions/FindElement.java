package basic.functions;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FindElement {
    private static final int DRIVER_EXPLICIT_WAIT = 180;
    private static final int LOADING_WAIT = 10;
    protected WebDriver driver;

    public FindElement(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement find(String path) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_EXPLICIT_WAIT));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(path)));
        Sleep.sleep(Sleep.defaultSleepTime);

        return driver.findElement(By.xpath(path));
    }


    public void waitLoading(String executingReportName, String path) throws Exception {
        By xpath = By.xpath(path);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(LOADING_WAIT));

        try {
            String initialPercent = driver.findElement(xpath).getText();
            String currentPercent = driver.findElement(xpath).getText();

            double percentGrowMaxSeconds = 300;
            double percentGrowSeconds = 0;

            while (driver.findElement(xpath).isDisplayed()) {
                while ((percentGrowSeconds < percentGrowMaxSeconds) && (initialPercent.equals(currentPercent))) {

                    String message = ("Waiting already %3.1fs to change the percentage: %s")
                            .formatted(percentGrowSeconds, currentPercent);

                    Logger.printf(executingReportName, message);

                    Exception err = errorModule();
                    if (err != null) {
                        throw err;
                    }

                    wait.withTimeout(Duration.ofMillis(500));
                    percentGrowSeconds += 0.5;
                    if (!driver.findElement(xpath).isDisplayed()) {
                        break;
                    }
                    currentPercent = driver.findElement(xpath).getText();
                }

                Exception err = errorModule();
                if (err != null) {
                    throw err;
                }

                if (!driver.findElement(xpath).isDisplayed()) {
                    break;
                }

                initialPercent = driver.findElement(xpath).getText();
                currentPercent = driver.findElement(xpath).getText();

                Logger.printf(executingReportName, currentPercent);

                percentGrowSeconds = 0;

                wait.withTimeout(Duration.ofMillis(500));
            }

            String message = "Loading completed";
            Logger.printf(executingReportName, message);
        } catch (UnreachableBrowserException | StaleElementReferenceException | NoSuchElementException ignored) {
        }
    }


    public Exception errorModule() {
        final By errorXpath = By.xpath("//div[@class='error_boundary_root']//h2[text()='Something went wrong with this module.']");

        try {
            driver.findElement(errorXpath);
            System.out.println("\n\nERROR: Something went wrong with this module.");
            
            return new Exception("ERROR: Something went wrong with this module.");
        } catch (NoSuchElementException exception) {
            return null;
        }
    }
}

