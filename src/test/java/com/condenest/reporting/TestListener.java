package com.condenest.reporting;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.condenest.test.BaseTest;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TestListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(TestListener.class);

    /**
     * This method is invoked before any test method gets executed
     */
    public void onStart(ITestContext context) {
        logger.info("*** Test Suite " + context.getName() + " started ***");
    }

    /**
     * This method is invoked after all tests methods gets executed.
     */
    public void onFinish(ITestContext context) {
        logger.info(("*** Test Suite " + context.getName() + " ending ***"));
        ExtentTestManager.endTest();
        ExtentManager.getInstance().flush();
    }

    /**
     * This method is invoked before any tests method is invoked
     *
     * @param result particular test method has been started.
     */

    public void onTestStart(ITestResult result) {
        logger.info(("*** Running test method " + result.getMethod().getMethodName() + "..."));
        ExtentTestManager.startTest(result.getMethod().getDescription());
    }

    /**
     * This method is invoked when any test method gets succeeded
     *
     * @param result indicate that the particular test method has successfully finished its execution
     */

    public void onTestSuccess(ITestResult result) {
        logger.info("*** Executed " + result.getMethod().getMethodName() + " test successfully...");
        addScreenshot(result);
        ExtentTestManager.getTest().log(Status.PASS, "Test Passed");
    }

    /**
     * his method is invoked when any test method gets failed
     *
     * @param result indicate that the particular test method has been failed
     */

    public void onTestFailure(ITestResult result) {
        ExtentTest extentTest = ExtentTestManager.getTest();
        logger.info("*** Test execution " + result.getMethod().getMethodName() + " failed...");
        extentTest.info((result.getMethod().getDescription() + " failed!"));
        addScreenshot(result);
        ExtentTestManager.getTest().log(Status.FAIL, "Test Failed");
    }

    /**
     * This method is invoked when each test method is skipped
     *
     * @param result ndicate that the particular test method has been skipped
     */

    public void onTestSkipped(ITestResult result) {
        logger.info("*** Test " + result.getMethod().getMethodName() + " skipped...");
        addScreenshot(result);
        ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
    }

    /**
     * This method is invoked each time the test method fails but is within the success percentage mentioned
     */

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.info("*** Test failed but within percentage % " + result.getMethod().getMethodName());
    }

    /**
     * Gets encoded base 64 String of the image
     *
     * @param result ITestResult of the test to take screenshot
     * @return encoded base 64 String of the image
     */
    public String getBase64Image(ITestResult result) {
        Object testClass = result.getInstance();
        WebDriver driver = ((BaseTest) testClass).getDriver();
        String targetLocation = "";
        String testClassName = result.getInstanceName();
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String testMethodName = result.getName().trim();
        String screenShotName = testMethodName + timeStamp + ".png";
        String reportsPath = System.getProperty("user.dir") + File.separator + "TestReport" + File.separator + "screenshots";
        String encodedBase64 = "";
        logger.info("Screen shots reports path - " + reportsPath);
        try {
            File file = new File(reportsPath + File.separator + testClassName);
            if (!file.exists()) {
                if (file.mkdirs()) {
                    logger.info("Directory: " + file.getAbsolutePath() + " is created!");
                } else {
                    logger.info("Failed to create directory: " + file.getAbsolutePath());
                }
            }
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            targetLocation = reportsPath + File.separator + testClassName + File.separator + screenShotName;
            File targetFile = new File(targetLocation);
            logger.info("Screen shot file location - " + screenshotFile.getAbsolutePath());
            logger.info("Target File location - " + targetFile.getAbsolutePath());
            FileHandler.copy(screenshotFile, targetFile);

            FileInputStream fileInputStream = new FileInputStream(targetFile);
            byte[] bytes = new byte[(int) targetFile.length()];
            fileInputStream.read(bytes);
            encodedBase64 = new String(Base64.encodeBase64(bytes));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found exception occurred while taking screenshot " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("An exception occured while reading image file" + e.getMessage());
        }
        return encodedBase64;
    }

    /**
     * Add screenshot to the test result
     *
     * @param result ITestResult of the test to add screenshot
     */
    public void addScreenshot(ITestResult result) {
        try {
            ExtentTestManager.getTest().info("Screenshot", MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Image(result)).build());
        } catch (IOException e) {
            throw new RuntimeException("An exception occured while capturing screenshot from base 64" + e.getMessage());
        }
    }

}
