package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**********************************************************************************************************************
 * A Selenium Page Object representing the Login flow.
 *
 * "As a user, I can login to my account in order to access or uploading files."
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class LoginPage extends WaitPage {

    private static final String USERNAME = "inputUsername";
    @FindBy(id = USERNAME)
    private WebElement username;

    private static final String PASSWORD = "inputPassword";
    @FindBy(id = PASSWORD)
    private WebElement password;

    private static final String SUBMIT_BUTTON = "submitButton";
    @FindBy(id = SUBMIT_BUTTON)
    private WebElement submitButton;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void login(WebDriver driver, String username, String password) {
        waitForElement(driver, USERNAME).sendKeys(username);
        waitForElement(driver, PASSWORD).sendKeys(password);
        waitForElement(driver, SUBMIT_BUTTON).sendKeys(Keys.ENTER);
    }
}
