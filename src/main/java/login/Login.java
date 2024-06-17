package login;

import basic.config.Config;
import basic.functions.FindElement;
import basic.functions.Logger;
import basic.functions.Sleep;

import static basic.config.Config.EMAIL;
import static basic.config.Config.PASSWORD;

public class Login {

    public static void authorization(String executingReportName, FindElement findElement) {
        Integer retriesCount = 0;

        while (true) {
            try {
                Logger.printf(executingReportName, "Authorization");

                String emailXpath = "//input[@id='user_email']";
                findElement.find(emailXpath).sendKeys(EMAIL);

                String passwordXpath = "//input[@id='user_password']";
                findElement.find(passwordXpath).sendKeys(PASSWORD);

                String buttonXpath = "//button[text()='LOG-IN']";
                findElement.find(buttonXpath).submit();

                break;
            } catch (Exception e) {
                Logger.printf(executingReportName, "Error: %s".formatted(e.getMessage()));
                Logger.printf(executingReportName, "Retry: %s".formatted(retriesCount));
                if ((++retriesCount).equals(Config.MAX_RETRIES)) throw e;

                Sleep.sleep(Sleep.defaultSleepTime);
            }
        }
    }
}
