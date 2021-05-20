package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**********************************************************************************************************************
 *
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class HomePage extends WaitPage {

    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    @FindBy(id = "nav-files-tab")
    private WebElement navTab;

    @FindBy(id = "fileUpload")
    private WebElement chooseFile;

    @FindBy(id = "fileUploadButton")
    private WebElement fileUploadButton;

    @FindBy(id = "viewFileButton")
    private WebElement viewFileButton;

    @FindBy(id = "deleteFileButton")
    private WebElement deleteFileButton;

    private static final String NAV_NOTES_TAB = "nav-notes-tab";
    @FindBy(id = NAV_NOTES_TAB)
    private WebElement notesTab;

    private static final String ADD_NOTE_BTN = "add-note-btn";
    @FindBy(id = ADD_NOTE_BTN)
    private WebElement addNewNote;

    private final String NOTE_TITLE = "note-title";
    @FindBy(id = NOTE_TITLE)
    private WebElement noteTitle;

    private final String NOTE_DESCRIPTION = "note-description";
    @FindBy(id = NOTE_DESCRIPTION)
    private WebElement noteDescription;

    @FindBy(id = "dismissNoteModal")
    private WebElement dismissNoteModal;

    @FindBy(id = "close-note-btn")
    private WebElement closeNote;

    private static final String SAVE_NOTE_BTN = "save-note-btn";
    @FindBy(id = SAVE_NOTE_BTN)
    private WebElement saveNote;

    @FindBy(id = "editNote")
    private WebElement editNote;

    @FindBy(id = "deleteNote")
    private WebElement deleteNote;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "logoutButton")
    private WebElement logout;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void clickNotesTab(WebDriver driver) {
        waitForElement(driver, NAV_NOTES_TAB).click();
    }

    public void clickAddNote(WebDriver driver) {
        waitForElement(driver, ADD_NOTE_BTN).click();;
    }

    public void clickSaveNote(WebDriver driver, String title, String description) {
        waitForElement(driver, NOTE_TITLE).sendKeys(title);
        waitForElement(driver, NOTE_DESCRIPTION).sendKeys(description);
        waitForElement(driver, SAVE_NOTE_BTN).click();
    }

    public String find(WebDriver driver, String text) {
        return waitForPageSearch(driver, text);
    }

    public void logout() {
        logout.click();
    }
}
