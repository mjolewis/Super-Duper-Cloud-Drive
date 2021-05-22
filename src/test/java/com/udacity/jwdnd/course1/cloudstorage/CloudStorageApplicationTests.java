package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

/**********************************************************************************************************************
 * Test user story: "As a user, I can signup and login to upload, download, and view files".
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests extends WaitPage {

	@LocalServerPort
	private int port;

	private static WebDriver driver;
	private String baseURL;
	private SignupPage signupPage;
	private LoginPage loginPage;
	private HomePage homePage;
	private ResultPage resultPage;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		driver = new ChromeDriver();
		baseURL = "http://localhost:" + port;
		signupPage = new SignupPage(driver);
		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
		resultPage = new ResultPage(driver);
	}

	@Test
	@DisplayName("Test 1.1 - Test unauthorized user can only access the login and signup pages.")
	public void unauthorizedAccess() {
		driver.get(baseURL + "/signup");
		assertEquals("Sign Up", driver.getTitle());

		driver.get(baseURL + "/login");
		assertEquals("Login", driver.getTitle());

		driver.get(baseURL + "/home");
		assertEquals("Login", driver.getTitle());

		driver.get(baseURL + "/notes");
		assertEquals("Login", driver.getTitle());

		driver.get(baseURL + "/files");
		assertEquals("Login", driver.getTitle());

		driver.get(baseURL + "/credentials");
		assertEquals("Login", driver.getTitle());
	}

	@Test
	@DisplayName("Test 1.2 - Test signup, login, and logout.")
	public void testSignUpLoginAndLogout() {

		driver.get(baseURL + "/signup");
		signupPage.signup("John", "Doe", "John", "test");

		driver.get(baseURL + "/login");
		loginPage.login(driver, "John", "test");

		driver.get(baseURL + "/home");
		homePage.logout(driver);

		driver.get(baseURL + "/home");
		String currentUrl = driver.getCurrentUrl();
		Assertions.assertNotEquals(baseURL + "/home", currentUrl);
	}

	@Test
	@DisplayName("Test 2.1 - Create a note and verify it displays")
	public void testCreateNote() {
		String title = "Test Title";
		String description = "Test description.";

		driver.get(baseURL + "/signup");
		signupPage.signup("John", "Doe", "John", "test");

		driver.get(baseURL + "/login");
		loginPage.login(driver, "John", "test");

		homePage.clickNotesTab(driver);
		homePage.clickAddNote(driver);
		homePage.clickSaveNote(driver, title, description);

		boolean result = resultPage.isSuccessMessageDisplayed(driver);
		//resultPage.clickNavLink(driver);
		driver.get(baseURL + "/home");
		homePage.clickNotesTab(driver);
		String actualTitle = homePage.find(driver, title);
		String actualDescription = homePage.find(driver, description);

		assertAll("Save a note",
				() -> assertTrue(result, "Note was not saved."),
				() -> assertEquals(title, actualTitle, "Note title is incorrect."),
				() -> assertEquals(description, actualDescription, "Description is incorrect"));

	}

	@Test
	@DisplayName("Test 2.2 - Edit existing note and verify the changes are displayed")
	public void testEditNote() {
		String title = "Test Title";
		String description = "Test description.";

		String editTitle = "Edit Title";
		String editDescription = "Edit description";

		String newTitle = title + editTitle;
		String newDescription = description + editDescription;

		driver.get(baseURL + "/signup");
		signupPage.signup("John", "Doe", "John", "test");

		driver.get(baseURL + "/login");
		loginPage.login(driver, "John", "test");

		homePage.clickNotesTab(driver);
		homePage.clickAddNote(driver);
		homePage.clickSaveNote(driver, title, description);

		driver.get(baseURL + "/home");
		homePage.clickNotesTab(driver);
		String noteId = homePage.getMostRecentEditNoteId(driver);
		homePage.clickEditNoteButton(driver, noteId);
		homePage.clickSaveNote(driver, editTitle, editDescription);

		boolean successEditMsg = resultPage.isSuccessMessageDisplayed(driver);
		resultPage.clickNavLink(driver);
		driver.get(baseURL + "/home");
		homePage.clickNotesTab(driver);
		String actualTitle = homePage.find(driver, newTitle);
		String actualDescription = homePage.find(driver, newDescription);

		assertAll("Edit a note",
				() -> assertTrue(successEditMsg, "Note was not saved."),
				() -> assertEquals(newTitle, actualTitle, "Edited title is incorrect."),
				() -> assertEquals(newDescription, actualDescription, "Edited description is incorrect"));
	}

	@Test
	@DisplayName("Test 2.3 - Delete existing note and verify it's no longer displayed")
	public void testDeleteNote() {
		String title = "Test Title";
		String description = "Test description.";

		driver.get(baseURL + "/signup");
		signupPage.signup("John", "Doe", "John", "test");

		driver.get(baseURL + "/login");
		loginPage.login(driver, "John", "test");

		driver.get(baseURL + "/home");
		homePage.clickNotesTab(driver);
		homePage.clickAddNote(driver);
		homePage.clickSaveNote(driver, title, description);
		boolean successAddMsg = resultPage.isSuccessMessageDisplayed(driver);

		driver.get(baseURL + "/home");
		homePage.clickNotesTab(driver);
		String noteTitle = homePage.find(driver, title);
		String noteDescription = homePage.find(driver, description);

		String noteId = homePage.getMostRecentDeleteNoteId(driver);
		homePage.clickDeleteNote(noteId);
		boolean successDeleteMsg = homePage.isNoteDisplayed(noteId);

		resultPage.clickNavLink(driver);
		driver.get(baseURL + "/home");
		homePage.clickNotesTab(driver);

		assertAll("Delete a note",
				() -> assertTrue(successAddMsg, "Success message was not displayed"),
				() -> assertFalse(successDeleteMsg, "Note was not deleted."),
				() -> assertEquals(title, noteTitle, "Note title was never added"),
				() -> assertEquals(description, noteDescription, "Note description was never added"));
	}

	@Test
	@DisplayName("Test 3.1 - Create credentials and verify they are displayed.")
	public void testCreatingCredentials() {
		String url = "www.github.com";
		String username = "John";
		String password = "test";

		driver.get(baseURL + "/signup");
		signupPage.signup("John", "Doe", "John", "test");

		driver.get(baseURL + "/login");
		loginPage.login(driver, "John", "test");

		driver.get(baseURL + "/home");
		homePage.clickCredentialsTab(driver);
		homePage.clickAddCredentials(driver);
		homePage.clickSaveCredentials(driver, url, username, password);
		boolean successAddMsg = resultPage.isSuccessMessageDisplayed(driver);
		resultPage.clickNavLink(driver);

		driver.get(baseURL + "/home");
		homePage.clickCredentialsTab(driver);
		String actualUrl = homePage.find(driver, url);
		String actualUsername = homePage.find(driver, username);
		String encryptedPassword = homePage.waitForPageSearch(driver, password);

		assertAll("Create credentials",
				() -> assertTrue(successAddMsg, "The credentials were not added."),
				() -> assertEquals(url, actualUrl, "The url is incorrect."),
				() -> assertEquals(username, actualUsername, "The username is incorrect"),
				() -> assertNull(encryptedPassword, "The password was not encrypted"));
	}

	@Test
	@DisplayName("Test 3.2 - View and edit existing credentials")
	public void testEditCredentials() {
		String url = "www.github.com";
		String username = "John";
		String password = "test";

		String editUrl = "/mjolewis/Super-Duper-Cloud-Drive";
		String editUsername = "James";
		String editPassword = "1234";

		String newUrl = url + editUrl;
		String newUsername = username + editUsername;
		String newPassword = password + editPassword;

		driver.get(baseURL + "/signup");
		signupPage.signup("John", "Doe", "John", "test");

		driver.get(baseURL + "/login");
		loginPage.login(driver, "John", "test");

		driver.get(baseURL + "/home");
		homePage.clickCredentialsTab(driver);
		homePage.clickAddCredentials(driver);
		homePage.clickSaveCredentials(driver, url, username, password);
		boolean successAddMsg = resultPage.isSuccessMessageDisplayed(driver);

		driver.get(baseURL + "/home");
		homePage.clickCredentialsTab(driver);

		String credentialId = homePage.getMostRecentEditCredentialId(driver);
		homePage.clickEditCredentialButton(driver, credentialId);
		homePage.clickSaveCredentials(driver, editUrl, editUsername, editPassword);

		boolean successEditMsg = resultPage.isSuccessMessageDisplayed(driver);
		resultPage.clickNavLink(driver);
		driver.get(baseURL + "/home");
		homePage.clickCredentialsTab(driver);
		String actualUrl = homePage.find(driver, newUrl);
		String actualUsername = homePage.find(driver, newUsername);
		homePage.clickEditCredentialButton(driver, credentialId);
		String actualPassword = homePage.getPlainTextPassword(driver);

		assertAll("Edit a credential",
				() -> assertTrue(successAddMsg, "Credential was not saved."),
				() -> assertTrue(successEditMsg, "Credential was not edited."),
				() -> assertEquals(newUrl, actualUrl, "Edited url is incorrect."),
				() -> assertEquals(newUsername, actualUsername, "Edited username is incorrect."),
				() -> assertEquals(newPassword, actualPassword, "Edited password is incorrect or encrypted."));
	}

	@Test
	@DisplayName("Test 3.3 - Delete existing credentials and verify they no longer exist")
	public void testDeleteCredentials() {
		String url = "www.github.com";
		String username = "John";
		String password = "test";

		driver.get(baseURL + "/signup");
		signupPage.signup("John", "Doe", "John", "test");

		driver.get(baseURL + "/login");
		loginPage.login(driver, "John", "test");

		driver.get(baseURL + "/home");
		homePage.clickCredentialsTab(driver);
		homePage.clickAddCredentials(driver);
		homePage.clickSaveCredentials(driver, url, username, password);
		boolean successAddMessage = resultPage.isSuccessMessageDisplayed(driver);

		driver.get(baseURL + "/home");
		homePage.clickCredentialsTab(driver);

		String credentialId = homePage.getMostRecentDeleteCredentialId(driver);
		homePage.clickDeleteCredential(credentialId);
		boolean successDeleteMessage = homePage.isCredentialDisplayed(driver, credentialId);

		assertAll("Delete a credential",
				() -> assertTrue(successAddMessage, "Credential was not added"),
				() -> assertFalse(successDeleteMessage, "Credential was not deleted"));
	}

	@AfterEach
	public void afterEach() {
		if (driver != null) {
			driver.quit();
		}
		driver = null;
	}

}
