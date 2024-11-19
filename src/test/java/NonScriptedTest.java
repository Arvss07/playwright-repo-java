import com.microsoft.playwright.*;

public class NonScriptedTest {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();

            // Step 1: Navigate to the website
            page.navigate("https://the-internet.herokuapp.com");
            page.waitForTimeout(1000);
            System.out.println("Initial Page Title: " + page.title());

            // Exploratory Test: Click random links and validate outcomes
            page.click("text=Form Authentication");
            page.waitForTimeout(1000);
            System.out.println("Page after click: " + page.url());

            // Interact with elements randomly
            if (page.isVisible("#username")) {
                page.fill("#username", "invalidUser");
                page.waitForTimeout(1000);
                page.fill("#password", "1234");
                page.waitForTimeout(1000);
                page.click("button[type='submit']");
                page.waitForTimeout(1000);
                System.out.println("Error Message: " + page.textContent(".flash"));
            }

            // Ad-hoc Test: Test a new feature dynamically
            page.navigate("https://the-internet.herokuapp.com/drag_and_drop");
            page.waitForTimeout(1000);
            String initialContent = page.textContent("#column-a");
            page.dragAndDrop("#column-a", "#column-b");
            page.waitForTimeout(1000);
            String updatedContent = page.textContent("#column-a");

            if (!initialContent.equals(updatedContent)) {
                System.out.println("Ad-hoc Test Passed: Drag-and-drop functionality works.");
            } else {
                System.out.println("Ad-hoc Test Failed: Drag-and-drop functionality did not work.");
            }

            // Additional Test Case: Check for broken images
            page.navigate("https://the-internet.herokuapp.com/broken_images");
            page.waitForTimeout(1000);
            if (page.isVisible("img[src='asdf.jpg']")) {
                System.out.println("Broken Image Test Failed: Image is visible.");
            } else {
                System.out.println("Broken Image Test Passed: Image is not visible.");
            }

            // Additional Test Case: Check for dropdown functionality
            page.navigate("https://the-internet.herokuapp.com/dropdown");
            page.waitForTimeout(1000);
            page.selectOption("#dropdown", "1");
            page.waitForTimeout(1000);
            if (page.isVisible("option[value='1'][selected]")) {
                System.out.println("Dropdown Test Passed: Option 1 selected.");
            } else {
                System.out.println("Dropdown Test Failed: Option 1 not selected.");
            }

            browser.close();
        }
    }
}