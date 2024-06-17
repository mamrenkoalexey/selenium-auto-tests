package reports;

public class DefaultReport extends CustomReport {
    @Override
    public void setup() {
        super.setup();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void test(String groupName, String reportName, String datePreset)throws Exception {
        super.test(groupName, reportName, datePreset);
        super.view();
    }
}
