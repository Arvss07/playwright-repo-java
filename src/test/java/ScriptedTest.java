import com.microsoft.playwright.*;

public class ScriptedTest {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();

            // Navigate to login page
            page.navigate("https://the-internet.herokuapp.com/login");
            page.waitForTimeout(1000);

            // Functional Test: Valid Login
            page.fill("#username", "tomsmith");
            page.waitForTimeout(1000);
            page.fill("#password", "SuperSecretPassword!");
            page.waitForTimeout(1000);
            page.click("button[type='submit']");
            page.waitForTimeout(1000);
            String successMessage = page.textContent(".flash");
            if (successMessage.contains("You logged into a secure area!")) {
                System.out.println("Login Test Passed!");
            } else {
                System.out.println("Login Test Failed!");
            }

            // Logout Test
            page.click("a[href='/logout']");
            page.waitForTimeout(1000);
            if (page.url().contains("login")) {
                System.out.println("Logout Test Passed!");
            } else {
                System.out.println("Logout Test Failed!");
            }

            // Error Handling Test: Invalid Login
            page.fill("#username", "invalidUser");
            page.waitForTimeout(1000);
            page.fill("#password", "invalidPassword");
            page.waitForTimeout(1000);
            page.click("button[type='submit']");
            page.waitForTimeout(1000);
            String errorMessage = page.textContent(".flash");
            if (errorMessage.contains("Your username is invalid!")) {
                System.out.println("Error Handling Test Passed!");
            } else {
                System.out.println("Error Handling Test Failed!");
            }

            // Page Navigation Test: Verify Redirect
            page.navigate("https://the-internet.herokuapp.com/secure");
            page.waitForTimeout(1000);
            String redirectMessage = page.textContent(".flash");
            if (redirectMessage.contains("You must login to view the secure area!")) {
                System.out.println("Page Navigation Test Passed!");
            } else {
                System.out.println("Page Navigation Test Failed!");
            }

            browser.close();
        }
    }
}