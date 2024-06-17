package tests;


import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class CustomWithTwoTypeDropdownsTest extends CustomTest {

    @Test
    @Parameters({"groupName", "reportName", "datePreset", "typeDropdownName", "typeName", "secondTypeDropdownName", "secondTypeName"})
    public void test(String groupName, String reportName, String datePreset, String typeDropdownName, String typeName, String secondTypeDropdownName, String secondTypeName) throws Exception{
        super.report.testWithTwoTypeDropdowns(groupName, reportName, datePreset, typeDropdownName, typeName, secondTypeDropdownName, secondTypeName);
    }
}


