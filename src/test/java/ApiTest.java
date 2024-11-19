import com.microsoft.playwright.*;

public class ApiTest {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            APIRequestContext apiRequestContext = playwright.request().newContext();

            // Test the JSONPlaceholder API which is a RESTful API that provides fake data for testing
            APIResponse response = apiRequestContext.get("https://jsonplaceholder.typicode.com/posts/1");

            if (response.ok()) {
                System.out.println("Response is successful!");
            } else {
                System.out.println("Response failed with status: " + response.status());
            }

            System.out.println("Response: " + response.text());
        }
    }
}