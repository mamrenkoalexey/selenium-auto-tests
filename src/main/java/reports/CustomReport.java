package reports;

import basic.config.Config;
import basic.functions.DriverFunctions;
import basic.functions.FindElement;
import basic.functions.Logger;
import basic.functions.Sleep;
import drivers.DefaultDriver;
import login.Login;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.time.Instant;
import java.util.UUID;

import static basic.config.Config.*;

public class CustomReport {
    protected String downloadPath;
    protected WebDriver driver = null;
    protected FindElement findElement;
    protected String executingReportName;
    protected String groupName;
    protected String reportName;

    public void setup() {
        this.downloadPath = this.createDownloadPath();
        this.driver = DefaultDriver.getDriver(this.downloadPath);
        this.findElement = new FindElement(driver);
    }

    public void finish() {
        if (driver != null) {
            driver.close();
        }

        if (BROWSER_DOWNLOAD_TESTS) this.deleteDownloadPath();
    }

    private void deleteDownloadPath() {
        File directory = new File(downloadPath);
        File[] filesList = directory.listFiles();

        for (File file : filesList) {
            file.delete();
        }

        directory.delete();
    }

    private String createDownloadPath() {
        UUID uuid = UUID.randomUUID();
        String downloadPath = System.getProperty("java.io.tmpdir") + uuid;
        File f = new File(downloadPath);

        // check if the directory can be created
        // using the abstract path name
        f.mkdir();

        return downloadPath;
    }

    public void test(String groupName, String reportName, String datePreset) throws Exception {
        int retriesCount = 1;

        while (true) {
            try {
                System.out.println("\n-----------------------------------------------------");
                this.groupName = groupName;
                this.reportName = reportName;
                this.executingReportName = "[%s] %s".formatted(groupName, reportName);

                DriverFunctions.openURL(driver);

                Login.authorization(executingReportName, findElement);

                Logger.printf(executingReportName, "Performing...");
                selectReportDropdown();
                locationPreset();
                datePresent(datePreset);
                break;
            } catch (Exception e) {
                Logger.printf(executingReportName, "Error: %s".formatted(e.getMessage()));
                Logger.printf(executingReportName, "Retry: %s".formatted(retriesCount));
                if (++retriesCount == Config.MAX_RETRIES) throw e;

                Sleep.sleep(Sleep.defaultSleepTime);
            }
        }
    }


    public void selectReportDropdown() {
        String reportsDropdown = "//span[text()='REPORTS']//parent::li//button";
        findElement.find(reportsDropdown).click();

        String search = "//input[@placeholder='Search...']";
        findElement.find(search).click();
        findElement.find(search).sendKeys(reportName);

        String reportGroupXPATH = "//div[text()='%s']".formatted(groupName);

        Logger.printf(executingReportName, "Select -> %s".formatted(groupName));
        findElement.find(reportGroupXPATH).click();

        String report = ("//div[text()='%s']//parent::li//li[text()='%s']").formatted(groupName, reportName);

        Logger.printf(executingReportName, "Select -> %s".formatted(reportName));
        findElement.find(report).click();
    }

    public void datePresent(String defaultDatePreset) throws Exception {
        if (!defaultDatePreset.isEmpty()) {
            String loading = "//p[text()='LOADING...']";
            findElement.waitLoading(executingReportName, loading);

            String datePreset = "//input[@data-testid='%s']".formatted(defaultDatePreset);

            Logger.printf(executingReportName, "Select -> %s".formatted(defaultDatePreset));

            findElement.find(datePreset).click();
        }
    }


    public void typeDropdown(String typeDropdownName, String typeName) {
        if (!typeName.isEmpty()) {
            String typeDropdown = "//span[text()='%s']//parent::div//parent::li//button".formatted(typeDropdownName);
            findElement.find(typeDropdown).click();

            String searchXPATH = typeDropdown + "//parent::div//input[@placeholder='Search...']";
            findElement.find(searchXPATH).click();
            findElement.find(searchXPATH).sendKeys(typeName);

            Logger.printf(executingReportName, "Select -> Type: %s".formatted(typeName));
            String type = "//div[text()='%s']".formatted(typeName);
            findElement.find(type).click();
        }
    }

    public void locationPreset() {
        String locationDropdown = "//div[@id='location_idCollapsibleContent']//button";
        findElement.find(locationDropdown).click();

        String searchXPATH = "//div[@id='location_idCollapsibleContent']//input[@placeholder='Search...']";
        findElement.find(searchXPATH).click();
        findElement.find(searchXPATH).sendKeys(Config.DefaultLocationName());

        Actions actions = new Actions(driver);

        String messageLocation = "Select -> %s".formatted(Config.DefaultLocationName());
        Logger.printf(executingReportName, messageLocation);

        actions.moveToElement(findElement.find("//div[text()='%s']//input".formatted(Config.DefaultLocationName()))).build().perform();
        findElement.find("//div[text()='%s']//button[text()='Only']".formatted(Config.DefaultLocationName())).click();
    }


    public void view() throws Exception {
        Sleep.sleep(Sleep.defaultSleepTime);
        String viewButton = "//button[text()='View']";

        //TODO if invisible button View;

        Logger.printf(executingReportName, "Select -> %s".formatted("View-button"));
        findElement.find(viewButton).click();

        String loadingXPATH = "//div[@class='loading']//p";
        findElement.waitLoading(executingReportName, loadingXPATH);

        Logger.printf(executingReportName, "Select -> %s".formatted("Filters-button"));

        String filtersButton = "//button[text()='Filters']";
        findElement.find(filtersButton).click();

        if (BROWSER_DOWNLOAD_TESTS) {
            if (BROWSER.equals("Chrome")) {
                Logger.printf(executingReportName, "Select -> %s".formatted("Download-button"));
                String downloadButton = "//button[text()='Download']";
                findElement.find(downloadButton).click();

                waitUntilFileToDownload();
            }
        }

        Logger.printf(executingReportName, "Report closed successfully.");
        System.out.println("\n-----------------------------------------------------\n");
    }

    public void waitUntilFileToDownload() {
        // Array to Store List of Files in Directory
        File[] listOfFiles;

        // Store File Name
        String fileName;

        //  Consider file is not downloaded
        boolean fileDownloaded = false;

        // capture time before looking for files in directory
        // last modified time of previous files will always less than start time
        // this is basically to ignore previous downloaded files
        long startTime = Instant.now().toEpochMilli();

        // Time to wait for download to finish
        long waitTime = startTime + BROWSER_DOWNLOAD_TIMEOUT;

        // while current time is less than wait time
        while (Instant.now().toEpochMilli() < waitTime) {
            // get all the files of the folder
            listOfFiles = new File(downloadPath).listFiles();

            // iterate through each file
            for (File file : listOfFiles) {
                // get the name of the current file
                fileName = file.getName().toLowerCase();

                // condition 1 - Last Modified Time > Start Time
                // condition 2 - till the time file is completely downloaded extension will be crdownload
                // Condition 3 - Current File name contains expected Text
                // Condition 4 - Current File name contains expected extension
                if (file.lastModified() > startTime && !fileName.contains("crdownload") && fileName.contains("report") && fileName.contains("csv")) {
                    // File Found
                    fileDownloaded = true;
                    Logger.printf(executingReportName, "Download -> Completed in %s ms".formatted(Instant.now().toEpochMilli() - startTime));
                    break;
                } else {
                    Sleep.sleep(500);
                }
            }

            // File Found Break While Loop
            if (fileDownloaded) return;
        }

        throw new RuntimeException("File not found in " + downloadPath);
    }

    public void testWithTypeDropdown(String groupName, String reportName, String datePreset, String typeDropdownName, String typeName) throws Exception {
        test(groupName, reportName, datePreset);
        typeDropdown(typeDropdownName, typeName);
        view();
    }

    public void testWithTwoTypeDropdowns(String groupName, String reportName, String datePreset, String typeDropdownName, String typeName, String secondTypeDropdownName, String secondTypeName) throws Exception {
        test(groupName, reportName, datePreset);
        typeDropdown(typeDropdownName, typeName);
        typeDropdown(secondTypeDropdownName, secondTypeName);
        view();
    }

    public void testWithThreeTypeDropdowns(String groupName, String reportName, String datePreset, String typeDropdownName, String typeName, String secondTypeDropdownName, String secondTypeName, String thirdTypeDropdownName, String thirdTypeName) throws Exception {
        test(groupName, reportName, datePreset);
        typeDropdown(typeDropdownName, typeName);
        typeDropdown(secondTypeDropdownName, secondTypeName);
        typeDropdown(thirdTypeDropdownName, thirdTypeName);
        view();
    }
}

