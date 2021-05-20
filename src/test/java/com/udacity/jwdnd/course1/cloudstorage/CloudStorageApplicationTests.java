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
		driver = new ChromeDriver();
	}

	@BeforeEach
	public void beforeEach() {
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
	@DisplayName("Test 1.2 - Test registration, login, and logout.")
	public void testSignUpLoginAndLogout() {

		driver.get(baseURL + "/signup");
		signupPage.signup("John", "Doe", "John", "test");

		driver.get(baseURL + "/login");
		loginPage.login("John", "test");

		driver.get(baseURL + "/home");
		homePage.logout();

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
		loginPage.login("John", "test");

		homePage.clickNotesTab(driver);
		homePage.clickAddNote(driver);
		homePage.clickSaveNote(driver, title, description);

		boolean result = resultPage.isSuccessMessageDisplayed(driver);
		resultPage.clickNavLink(driver);
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

	}

	@AfterAll
	public static void afterAll() {
		if (driver != null) {
			driver.quit();
		}
		driver = null;
	}

}
