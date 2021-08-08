package com.condenest.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.condenest.utils.PropertiesConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

import static com.condenest.utils.PropertiesReader.properties;

public class ExtentManager {
    private static final Logger logger = LogManager.getLogger(ExtentManager.class);

    private static ExtentReports extent;
    private static String reportFileName = "Test-Automaton-Report" + ".html";
    private static String reportFilepath = System.getProperty("user.dir") + File.separator + "TestReport";
    private static String reportFileLocation = reportFilepath + File.separator
            + reportFileName;

    /**
     * Gets a instance of extent report
     *
     * @return instance of extent report
     */
    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
        return extent;
    }

    /**
     * Create an instance of Extent report
     *
     * @return extent report instance
     */
    public static ExtentReports createInstance() {
        String fileName = getReportPath(reportFilepath);
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(reportFileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("Appium Sample Test Result");
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Device Platform", properties.getProperty(PropertiesConstant.DEVICE_PLATFORM));
        extent.setSystemInfo("Device Name", properties.getProperty(PropertiesConstant.DEVICE_NAME));
        return extent;
    }

    /**
     * Creates report directory and returns the path
     *
     * @param path - report directory path
     * @return report directory path
     */
    private static String getReportPath(String path) {
        File testDirectory = new File(path);
        if (!testDirectory.exists()) {
            if (testDirectory.mkdir()) {
                logger.info("Directory: " + path + " is created!");
                return reportFileLocation;
            } else {
                logger.info("Failed to create directory: " + path);
                return System.getProperty("user.dir");
            }
        } else {
            logger.info("Directory already exists: " + path);
        }
        return reportFileLocation;
    }
}
