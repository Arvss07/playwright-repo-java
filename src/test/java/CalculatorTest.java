import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;

    @BeforeEach
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        context = browser.newContext();
    }

    @Test
    public void testAddition() {
        Calculator calculator = new Calculator();
        assertEquals(15, calculator.add(5, 10));
    }

    @AfterEach
    public void teardown() {
        context.close();
        browser.close();
        playwright.close();
    }
}