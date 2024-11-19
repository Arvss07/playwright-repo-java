import com.microsoft.playwright.*;
import java.util.ArrayList;
import java.util.List;

public class ResponsiveTest {
    public static void main(String[] args) {
        int[] widths = {320, 768, 1024}; // Mobile, Tablet, Desktop widths
        String[] urls = {
                "https://en.wikipedia.org/wiki/Main_Page",
                "https://en.wikipedia.org/wiki/Playwright_(software)",
                "https://en.wikipedia.org/wiki/Java_(programming_language)"
        };

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();

            for (String url : urls) {
                for (int width : widths) {
                    BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, 800));
                    Page page = context.newPage();
                    List<ConsoleMessage> consoleMessages = new ArrayList<>();

                    page.onConsoleMessage(consoleMessages::add);
                    page.navigate(url, new Page.NavigateOptions().setTimeout(60000));

                    System.out.println("Viewport width: " + width + ", URL: " + url + ", Title: " + page.title());

                    if (page.isVisible("body")) {
                        System.out.println("Page loaded successfully!");
                    } else {
                        System.out.println("Page failed to load!");
                    }

                    // Check if specific elements are visible
                    if (page.isVisible("header")) {
                        System.out.println("Header is visible.");
                    } else {
                        System.out.println("Header is not visible.");
                    }

                    if (page.isVisible("footer")) {
                        System.out.println("Footer is visible.");
                    } else {
                        System.out.println("Footer is not visible.");
                    }

                    if (page.isVisible("main")) {
                        System.out.println("Main content is visible.");
                    } else {
                        System.out.println("Main content is not visible.");
                    }

                    // Check if the page contains specific text
                    if (page.content().contains("Wikipedia")) {
                        System.out.println("Page contains the text 'Wikipedia'.");
                    } else {
                        System.out.println("Page does not contain the text 'Wikipedia'.");
                    }

                    // Check if the page has any console errors
                    boolean hasErrors = consoleMessages.stream().anyMatch(msg -> msg.type().equals("error"));
                    if (hasErrors) {
                        System.out.println("Page has console errors.");
                    } else {
                        System.out.println("Page has no console errors.");
                    }

                    System.out.println("------------------------------------------------------");
                    page.close();
                }
            }
            browser.close();
        }
    }
}