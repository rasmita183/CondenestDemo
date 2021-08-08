package com.condenest.pages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class LoginHamburgerPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(LoginHamburgerPage.class);
    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc='Navigate up']")
    private WebElement hamBurgerButton;
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='All News']")
    private WebElement allNewsText;
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Entertainment']")
    private WebElement entertainmentText;
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Product Reviews']")
    private WebElement productReviewText;
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Business']")
    private WebElement businessText;
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Gear']")
    private WebElement gearText;
    @AndroidFindBy(id="com.wiredapp:id/name")
    private List<WebElement> menuList;

    public LoginHamburgerPage(AndroidDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    public List<String> getMenuList() {
        clickAnElement(hamBurgerButton);
        List<String> menus = new ArrayList<>();
        for(WebElement element : menuList) {
            menus.add(element.getText());
        }
        return menus;
    }
}
