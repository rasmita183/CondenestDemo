package com.condenest.test;

import com.condenest.pages.HomePage;
import com.condenest.pages.LoginHamburgerPage;
import com.condenest.reporting.ExtentTestManager;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class HomeTest extends BaseTest {
    LoginHamburgerPage loginHamburgerPage;
    HomePage homePage;

    /**
     * Validate Home page functionality
     */
    @Test(priority = 0, description = "Validate  HomePage")
    public void validateHomePage() {
        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.validateIsHomePage(), "HomePage is not displayed");
        Assert.assertTrue(homePage.validateIsTextNewsIsDisplayed(), "Search Text is not displayed");
        ExtentTestManager.reportInfo("Home Page is validated");
    }

    /**
     * Validate hamburger menu elements
     */
    @Test(dependsOnMethods = {"validateHomePage"}, description = "Validate MenuItems In Hamburger")
    public void validateMenuItems() {
        loginHamburgerPage = new LoginHamburgerPage(driver);
        List<String> expectedMenuList = Arrays.asList("All News", "Entertainment", "Product Reviews", "Business", "Gear", "Videos", "Downloads", "Sync Now", "Switch Theme", "Settings", "Explore");
        List<String> actualMenuList = loginHamburgerPage.getMenuList();
        ExtentTestManager.reportInfo("Expected Menus: " + expectedMenuList);
        ExtentTestManager.reportInfo("Actual Menus: " + actualMenuList);
        Assert.assertEquals(actualMenuList, expectedMenuList, "Menu list no matching");
    }
}
