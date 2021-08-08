package com.condenest.test;

import com.condenest.utils.PropertiesConstant;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;
import java.net.URL;

import static com.condenest.utils.PropertiesReader.properties;

public class BaseTest {
    private static final Logger logger = LogManager.getLogger(BaseTest.class);

    protected AndroidDriver<MobileElement> driver;

    /**
     * This method is used to  starting the server , appium driver
     */
    @BeforeClass
    public void setUp() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.APP, properties.getProperty(PropertiesConstant.APP_PATH));
            capabilities.setCapability(MobileCapabilityType.UDID, properties.getProperty(PropertiesConstant.DEVICE_ID));
            String url = "http://" + properties.getProperty(PropertiesConstant.HOST) + ":"
                    + properties.getProperty(PropertiesConstant.PORT) + "/wd/hub";
            driver = new  AndroidDriver<>(new URL(url), capabilities);
            logger.info("Driver created");
            driver.launchApp();
            logger.info("App launched");
        } catch (MalformedURLException e) {
            throw new RuntimeException("Unable to create driver with invalid url: " + e);
        }
    }

    /**
     * Get the current driver
     *
     * @return current driver
     */
    public WebDriver getDriver() {
        return this.driver;
    }

    /**
     * This method is used to stop the server and close the driver
     */
    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
