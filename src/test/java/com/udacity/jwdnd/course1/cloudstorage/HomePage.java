package com.udacity.jwdnd.course1.cloudstorage;

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

    public String getMostRecentNoteId() {
        String mostRecentNoteId = null;
        for (WebElement button : editNoteButtons) {
            mostRecentNoteId = button.getAttribute("id");
        }

        return mostRecentNoteId;
    }

    public void clickEditNoteButton(WebDriver driver, String buttonId) {
        waitForElement(driver, buttonId).click();
    }

    public String find(WebDriver driver, String text) {
        return waitForPageSearch(driver, text);
    }

    public void logout() {
        logout.click();
    }
}
