package tests;


import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import reports.CustomReport;


public class CustomTest extends CustomReport {
    CustomReport report = new CustomReport();

    @BeforeTest
    public void start() {
        report.setup();
    }


    @AfterTest(alwaysRun = true)
    public void finish() {
        report.finish();
    }
}


