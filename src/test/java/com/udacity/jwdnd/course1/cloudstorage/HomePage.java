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

    private static final String DELETE_NOTE_BTN = "//a[starts-with(@id,'btn-delete-note-')]";
    @FindBy(xpath = DELETE_NOTE_BTN)
    private List<WebElement> deleteNoteButtons;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "logoutButton")
    private WebElement logout;

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

    public String getMostRecentNoteId() {
        return getMostRecentAddedElementId(editNoteButtons);
    }

    public String getMostRecentDeleteNoteId() {
        return getMostRecentAddedElementId(deleteNoteButtons);
    }

    public void clickEditNoteButton(WebDriver driver, String buttonId) {
        waitForElement(driver, buttonId).click();
    }

    public void clickDeleteNote(String buttonId) {
        clickButton(deleteNoteButtons, buttonId);
    }

    public boolean isNoteDisplayed(String buttonId) {
        for (WebElement button : editNoteButtons) {
            String id = button.getAttribute("id");
            if (id.equals(buttonId)) {
                return true;
            }
        }

        return false;
    }

    public String find(WebDriver driver, String text) {
        return waitForPageSearch(driver, text);
    }

    public void logout() {
        logout.sendKeys(Keys.ENTER);
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
}
