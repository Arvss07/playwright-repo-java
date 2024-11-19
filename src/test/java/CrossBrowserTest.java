import com.microsoft.playwright.*;

public class CrossBrowserTest {
    public static void main(String[] args) {
        // This will run the test on Chromium, Firefox, and WebKit
        String[] browsers = {"chromium", "firefox", "webkit"};

        // example.com is a simple website intended for testing
        final String EXAMPLE_URL = "https://example.com";

        /* Launch each browser and navigate to example.com.
        *  example.com should be loaded in each browser
        */
        for (String browserType : browsers) {
            try (Playwright playwright = Playwright.create()) {
                Browser browser = switch (browserType) {
                    case "chromium" -> playwright.chromium().launch();
                    case "firefox" -> playwright.firefox().launch();
                    case "webkit" -> playwright.webkit().launch();
                    default -> throw new IllegalArgumentException("Unsupported browser");
                };

                BrowserContext context = browser.newContext();
                Page page = context.newPage();
                page.navigate(EXAMPLE_URL);
                System.out.println(browserType + ": " + page.title());
                System.out.println(browserType + ": " + page.url());
                System.out.println(browserType + ": " + page.locator("p").first().textContent());
                browser.close();
            }
        }
    }
}
