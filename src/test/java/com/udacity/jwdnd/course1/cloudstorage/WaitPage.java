package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**********************************************************************************************************************
 * Utility class that implements a WebDriverWait object for all Page objects.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public abstract class WaitPage {
    private final Logger logger = LoggerFactory.getLogger(WaitPage.class.getName());
    private static final int WAIT_TIME_IN_SECONDS = 5;

    protected WebElement waitForElement(WebDriver driver, String elementId) {
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIME_IN_SECONDS);
        return wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id(elementId))));
    }

    protected String waitForPageSearch(WebDriver driver, String text) {
        WebElement wait;
        try {
            wait = new WebDriverWait(driver, WAIT_TIME_IN_SECONDS)
                    .until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[text()='" + text + "']"))));
        } catch (NoSuchElementException e) {
            return null;
        }

        return wait.getText();
    }

    protected boolean isElementDisplayed(WebDriver driver, String elementId) {
        try {
            return waitForElement(driver, elementId).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
