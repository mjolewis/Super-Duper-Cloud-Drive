package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**********************************************************************************************************************
 *
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class HomePage extends WaitPage {

    private static final String NAV_NOTES_TAB = "nav-notes-tab";
    @FindBy(id = NAV_NOTES_TAB)
    private WebElement notesTab;

    private static final String ADD_NOTE_BTN = "add-note-btn";
    @FindBy(id = ADD_NOTE_BTN)
    private WebElement addNote;

    private static final String NOTE_TITLE = "note-title";
    @FindBy(id = NOTE_TITLE)
    private WebElement noteTitle;

    private static final String NOTE_DESCRIPTION = "note-description";
    @FindBy(id = NOTE_DESCRIPTION)
    private WebElement noteDescription;

    private static final String SAVE_NOTE_BTN = "save-note-btn";
    @FindBy(id = SAVE_NOTE_BTN)
    private WebElement saveNote;

    private static final String EDIT_NOTE_BTN = "//button[starts-with(@id, 'btn-edit-note-')]";
    @FindBy(xpath = EDIT_NOTE_BTN)
    private List<WebElement> editNoteButtons;

    private static final String DELETE_NOTE_BTN = "//a[starts-with(@id,'btn-delete-note-')]";
    @FindBy(xpath = DELETE_NOTE_BTN)
    private List<WebElement> deleteNoteButtons;

    private static final String NAV_CREDENTIAL_TAB = "nav-credentials-tab";
    @FindBy(id = NAV_CREDENTIAL_TAB)
    private WebElement credentialsTab;

    private static final String ADD_CREDENTIAL_BTN = "add-credential-btn";
    @FindBy(id = ADD_CREDENTIAL_BTN)
    private WebElement addCredential;

    private static final String CREDENTIAL_URL = "credential-url";
    @FindBy(id = CREDENTIAL_URL)
    private WebElement credentialUrl;

    private static final String CREDENTIAL_USERNAME = "credential-username";
    @FindBy(id = CREDENTIAL_USERNAME)
    private WebElement credentialUsername;

    private static final String CREDENTIAL_PASSWORD = "credential-password";
    @FindBy(id = CREDENTIAL_PASSWORD)
    private WebElement credentialPassword;

    private static final String SAVE_CREDENTIALS_BTN = "save-credentials-btn";
    @FindBy(id = SAVE_CREDENTIALS_BTN)
    private WebElement saveCredentials;

    private static final String EDIT_CREDENTIALS_BTN = "//button[starts-with(@id, 'btn-edit-credential-')]";
    @FindBy(xpath = EDIT_CREDENTIALS_BTN)
    private List<WebElement> editCredentialsButtons;

    private static final String DELETE_CREDENTIALS_BTN = "//a[starts-with(@id, 'btn-delete-credential-')]";
    @FindBy(xpath = DELETE_CREDENTIALS_BTN)
    private List<WebElement> deleteCredentialsButtons;

    private static final String LOGOUT_BTN = "logout-btn";
    @FindBy(id = LOGOUT_BTN)
    private WebElement logoutButton;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void clickNotesTab(WebDriver driver) {
        waitForElement(driver, NAV_NOTES_TAB).sendKeys(Keys.ENTER);
    }

    public void clickAddNote(WebDriver driver) {
        waitForElement(driver, ADD_NOTE_BTN).sendKeys(Keys.ENTER);;
    }

    public void clickSaveNote(WebDriver driver, String title, String description) {
        waitForElement(driver, NOTE_TITLE).sendKeys(title);
        waitForElement(driver, NOTE_DESCRIPTION).sendKeys(description);
        waitForElement(driver, SAVE_NOTE_BTN).sendKeys(Keys.ENTER);
    }

    public String getMostRecentEditNoteId() {
        return getMostRecentAddedElementId(editNoteButtons);
    }

    public void clickEditNoteButton(WebDriver driver, String buttonId) {
        waitForElement(driver, buttonId).sendKeys(Keys.ENTER);
    }

    public String getMostRecentDeleteNoteId() {
        return getMostRecentAddedElementId(deleteNoteButtons);
    }

    public void clickDeleteNote(String noteId) {
        clickButton(deleteNoteButtons, noteId);
    }

    public boolean isNoteDisplayed(String noteId) {
        return isElementDisplayed(editNoteButtons, noteId);
    }

    public void clickCredentialsTab(WebDriver driver) {
        waitForElement(driver, NAV_CREDENTIAL_TAB).sendKeys(Keys.ENTER);
    }

    public void clickAddCredentials(WebDriver driver) {
        waitForElement(driver, ADD_CREDENTIAL_BTN).sendKeys(Keys.ENTER);
    }

    public void clickSaveCredentials(WebDriver driver, String url, String userName, String password) {
        waitForElement(driver, CREDENTIAL_URL).sendKeys(url);
        waitForElement(driver, CREDENTIAL_USERNAME).sendKeys(userName);
        waitForElement(driver, CREDENTIAL_PASSWORD).sendKeys(password);
        waitForElement(driver, SAVE_CREDENTIALS_BTN).sendKeys(Keys.ENTER);
    }

    public String getMostRecentEditCredentialId() {
        return getMostRecentAddedElementId(editCredentialsButtons);
    }

    public void clickEditCredentialButton(WebDriver driver, String buttonId) {
        waitForElement(driver, buttonId).sendKeys(Keys.ENTER);
    }

    public boolean isCredentialDisplayed(String credentialId) {
        return isElementDisplayed(editCredentialsButtons, credentialId);
    }

    public String getMostRecentDeleteCredentialId() {
        return getMostRecentAddedElementId(deleteCredentialsButtons);
    }

    public void clickDeleteCredential(String credentialId) {
        clickButton(deleteCredentialsButtons, credentialId);
    }

    public String find(WebDriver driver, String text) {
        return waitForPageSearch(driver, text);
    }

    public void logout(WebDriver driver) {
        waitForElement(driver, LOGOUT_BTN).sendKeys(Keys.ENTER);
    }

    private String getMostRecentAddedElementId(List<WebElement> buttons) {
        String mostRecentId = null;
        for (WebElement button : buttons) {
            mostRecentId = button.getAttribute("id");
        }

        return mostRecentId;
    }

    private void clickButton(List<WebElement> buttons, String id) {
        for (WebElement button : buttons) {
            if (button.getAttribute("id").equals(id)) {
                button.sendKeys(Keys.ENTER);
                break;
            }
        }
    }

    private boolean isElementDisplayed(List<WebElement> buttons, String elementId) {
        for (WebElement button : buttons) {
            String id = button.getAttribute("id");
            if (id.equals(elementId)) {
                return true;
            }
        }

        return false;
    }
}
