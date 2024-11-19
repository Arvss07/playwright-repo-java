import com.microsoft.playwright.*;
import java.util.*;

public class PerformanceTest {
    public static void main(String[] args) {
        String[] websites = {
                "https://www.slu.edu.ph/",
                "https://www.uc-bcf.edu.ph/",
                "https://ubaguio.edu/"
        };

        Map<String, List<Long>> websiteLoadTimes = new LinkedHashMap<>();

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            BrowserContext context = browser.newContext();

            for (String website : websites) {
                System.out.println("Testing website: " + website);
                List<Long> loadTimes = new ArrayList<>();

                for (int i = 0; i < 5; i++) {
                    // Open a new page
                    Page page = context.newPage();

                    // Measure start time
                    long startTime = System.currentTimeMillis();

                    // Capture request and response timings
                    final long[] requestStartTime = {0};
                    final long[] responseStartTime = {0};

                    page.onRequest(request -> {
                        if (request.url().equals(website)) {
                            requestStartTime[0] = System.currentTimeMillis();
                        }
                    });

                    page.onResponse(response -> {
                        if (response.url().equals(website)) {
                            responseStartTime[0] = System.currentTimeMillis();
                        }
                    });

                    // Navigate to the website
                    page.navigate(website, new Page.NavigateOptions().setTimeout(60000));

                    // Measure end time
                    long endTime = System.currentTimeMillis();

                    // Calculate and store load time
                    long loadTime = endTime - startTime;
                    loadTimes.add(loadTime);

                    // Log performance metrics
                    System.out.println("Run " + (i + 1) + " - Page load time: " + loadTime + "ms");

                    // Calculate and print TTFB
                    long ttfb = responseStartTime[0] - requestStartTime[0];
                    System.out.println("Run " + (i + 1) + " - Time to First Byte (TTFB): " + ttfb + "ms");

                    System.out.println("------------------------------------------------------");
                    page.close();
                }

                websiteLoadTimes.put(website, loadTimes);
            }

            browser.close();
        }

        // Calculate average load times
        Map<String, Long> averageLoadTimes = new LinkedHashMap<>();
        for (Map.Entry<String, List<Long>> entry : websiteLoadTimes.entrySet()) {
            long sum = 0;
            for (long time : entry.getValue()) {
                sum += time;
            }
            long average = sum / entry.getValue().size();
            averageLoadTimes.put(entry.getKey(), average);
        }

        // Determine the fastest website
        List<Map.Entry<String, Long>> sortedWebsites = new ArrayList<>(averageLoadTimes.entrySet());
        sortedWebsites.sort(Map.Entry.comparingByValue());

        // Display Result
        System.out.println("Website Rankings by Average Load Time:");
        int rank = 1;
        for (Map.Entry<String, Long> entry : sortedWebsites) {
            System.out.println(rank + ". " + entry.getKey() + " - " + entry.getValue() + "ms");
            rank++;
        }
    }
}