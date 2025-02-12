import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

    public class TestResultListener implements ITestListener {
        private static final String FILE_PATH = "Test-Output/test-output.log";

        private void writeToFile(String message) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
                writer.write(message);
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onTestSuccess(ITestResult result) {
            Object[] params = result.getParameters();
            writeToFile("PASS: " + params[0] + ", " + params[1]);
        }

        @Override
        public void onTestFailure(ITestResult result) {
            Object[] params = result.getParameters();
            writeToFile("FAIL: " + params[0] + ", " + params[1] + " - " + result.getThrowable().getMessage());
        }

        @Override
        public void onTestSkipped(ITestResult result) {
            Object[] params = result.getParameters();
            writeToFile("SKIPPED: " + params[0] + ", " + params[1]);
        }

        @Override
        public void onFinish(ITestContext context) {
            writeToFile("Test Execution Completed.");
        }
    }


