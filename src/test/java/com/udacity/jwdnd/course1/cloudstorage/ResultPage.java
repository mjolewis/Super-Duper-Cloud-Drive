package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**********************************************************************************************************************
 * A Selenium Page Object representing the result of user actions.
 *
 * "As a user, I can add new files, notes, and credentials and be notified whether or not the system successfully
 * stored the item."
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class ResultPage extends WaitPage {

    private static final String SUCCESS_MESSAGE = "success-message";
    @FindBy(id = SUCCESS_MESSAGE)
    private WebElement successMessage;

    private static final String ERROR_MESSAGE = "error-message";
    @FindBy(id = ERROR_MESSAGE)
    private WebElement errorMessage;

    private static final String NAV_LINK = "nav-link";
    @FindBy(id = NAV_LINK)
    private WebElement navLink;

    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isSuccessMessageDisplayed(WebDriver driver) {
        return isElementDisplayed(driver, SUCCESS_MESSAGE);
    }

    public boolean isErrorMessageDisplayed() {
        return errorMessage.isDisplayed();
    }

    public void clickNavLink(WebDriver driver) {
        waitForElement(driver, NAV_LINK);
    }
}
