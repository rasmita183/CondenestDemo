package com.condenest.pages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class HomePage extends BasePage {

    @AndroidFindBy(id = "com.wiredapp:id/menu_search")
    private WebElement searchTextField;
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='All News (10)']")
    private WebElement newsTextBox;

    public HomePage(AndroidDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    /**
     * Validate home page is displaying
     *
     * @return true if home is displayed
     */
    public boolean validateIsHomePage() {
        return searchTextField.isDisplayed();
    }
    /**
     * Validate home page text is displaying
     *
     * @return true if home is displayed
     */
    public boolean validateIsTextNewsIsDisplayed() {
        return newsTextBox.isDisplayed();
    }
}
