import com.microsoft.playwright.*;

public class CrossBrowserTest {
    public static void main(String[] args) {
        String[] browsers = {"chromium", "firefox", "webkit"};

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
                page.navigate("https://example.com");
                System.out.println(browserType + ": " + page.title());
                browser.close();
            }
        }
    }
}
