package basic.functions;

public class Sleep {
    public static final Integer defaultSleepTime = 1000;

    public static void sleep(Integer time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
