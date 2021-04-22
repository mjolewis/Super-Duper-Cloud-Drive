package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**********************************************************************************************************************
 * A Selenium Page Object representing the Signup flow.
 *
 * "As a user, I can create a new account in order to login to the application."
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class SignupPage {
    private WebDriver driver;

    @FindBy(id = "inputFirstName")
    private WebElement firstname;

    @FindBy(id = "inputLastName")
    private WebElement lastname;

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "submitButton")
    private WebElement submitButton;

    /**
     * Constructor starts the process of allowing Selenium to automatically process the element selectors.
     * @param driver A browser driver instance.
     */
    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void signup(String firstname, String lastname, String username, String password) {
        this.firstname.clear();
        this.lastname.clear();
        this.username.clear();
        this.password.clear();

        this.firstname.sendKeys(firstname);
        this.lastname.sendKeys(lastname);
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        this.submitButton.click();
    }
}
