package tests;


import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class CustomWithTypeDropdownReportTest extends CustomTest {

    @Test
    @Parameters({"groupName", "reportName", "datePreset", "typeDropdownName", "typeName"})
    public void test(String groupName, String reportName, String datePreset, String typeDropdownName, String typeName) throws Exception {
        super.report.testWithTypeDropdown(groupName, reportName, datePreset, typeDropdownName, typeName);
    }
}


