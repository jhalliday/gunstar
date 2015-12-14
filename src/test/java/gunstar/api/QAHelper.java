package gunstar.api;

import static org.junit.Assert.assertTrue;

public class QAHelper {

    public static final String TEST_VENUE = "TESTEX";
    public static final String TEST_STOCK = "FOOBAR";
    public static final String TEST_ACCOUNT = "EXB123456";

    public static void assertOk(BaseResponse response) {
        assertTrue(response.error, response.ok);
    }
}
