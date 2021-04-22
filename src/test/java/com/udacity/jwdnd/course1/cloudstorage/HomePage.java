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
public class HomePage {

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

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "addNewNoteButton")
    private WebElement addNewNote;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "dismissNoteModal")
    private WebElement dismissNoteModal;

    @FindBy(id = "closeNoteModal")
    private WebElement closeNoteModal;

    @FindBy(id = "saveNote")
    private WebElement saveNote;

    @FindBy(id = "editNote")
    private WebElement editNote;

    @FindBy(id = "deleteNote")
    private WebElement deleteNote;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
