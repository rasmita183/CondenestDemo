package com.condenest.pages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

public abstract class BasePage {

    private static final Logger logger = LogManager.getLogger(BasePage.class);
    protected final AndroidDriver<MobileElement> driver;

    public BasePage(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
    }

    /**
     * clicks an element
     *
     * @param element to click
     */
    public void clickAnElement(WebElement element) {
        element.click();
        logger.info("clicked element: " + element);
    }

    /**
     * @param element element to check visibility
     * @return true is element is displayed
     */
    public boolean isElementDisplayed(WebElement element) {
        return element.isDisplayed();
    }

}
