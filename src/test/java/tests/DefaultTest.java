package tests;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import reports.DefaultReport;

public class DefaultTest {
    DefaultReport report = new DefaultReport();

    @BeforeTest
    public void start() {
        report.setup();
    }

    @Test
    @Parameters({"groupName", "reportName", "datePreset"})
    public void test(String groupName, String reportName, String datePreset) throws Exception{
        report.test(groupName, reportName, datePreset);
    }

    @AfterTest(alwaysRun = true)
    public void finish() {
        report.finish();
    }
}
