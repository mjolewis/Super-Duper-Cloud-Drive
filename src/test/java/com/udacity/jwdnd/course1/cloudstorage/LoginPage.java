package com.udacity.jwdnd.course1.cloudstorage;

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
public class LoginPage {
    private WebDriver driver;

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "submitButton")
    private WebElement submitButton;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password) {
        this.username.clear();
        this.password.clear();

        this.username.sendKeys(username);
        this.password.sendKeys(password);
        this.submitButton.click();
    }
}
