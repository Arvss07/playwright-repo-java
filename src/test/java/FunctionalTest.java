import com.microsoft.playwright.*;

public class FunctionalTest {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("https://example.com");

            // Locate the header element and verify its text
            Locator header = page.locator("h1");
            String headerText = header.textContent();
            // return true if the header text is "Example Domain"
            if (headerText.equals("Example Domain")) {
                System.out.println("Header text is correct!");
            } else {
                System.out.println("Header text mismatch!");
            }
            browser.close();
        }
    }
}