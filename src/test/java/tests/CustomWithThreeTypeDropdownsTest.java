package tests;


import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class CustomWithThreeTypeDropdownsTest extends CustomTest {

    @Test
    @Parameters({"groupName", "reportName", "datePreset", "typeDropdownName", "typeName", "secondTypeDropdownName", "secondTypeName", "thirdTypeDropdownName", "thirdTypeName"})
    public void test(String groupName, String reportName, String datePreset, String typeDropdownName, String typeName, String secondTypeDropdownName, String secondTypeName, String thirdTypeDropdownName, String thirdTypeName) throws Exception {
        super.report.testWithThreeTypeDropdowns(groupName, reportName, datePreset, typeDropdownName, typeName, secondTypeDropdownName, secondTypeName, thirdTypeDropdownName, thirdTypeName);
    }
}


