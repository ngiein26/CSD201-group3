import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;

public class DataGenerator {
    public static void main(String[] args) {
        String csvFile = "sample_data.csv";
        int numberOfPages = 10000;

        String[] sampleUrls = {
            "https://www.google.com",
            "https://www.facebook.com",
            "https://www.github.com",
            "https://www.twitter.com",
            "https://www.linkedin.com",
            "https://www.reddit.com",
            "https://www.youtube.com",
            "https://www.wikipedia.org"
        };

        String[] sampleTitles = {
            "Google Search",
            "Facebook Home",
            "GitHub Repository",
            "Twitter Feed",
            "LinkedIn Profile",
            "Reddit Discussion",
            "YouTube Video",
            "Wikipedia Article"
        };

        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.append("URL,Title,Timestamp\n");
            Random random = new Random();
            LocalDateTime startTime = LocalDateTime.now().minusSeconds(numberOfPages).withNano(0);
            
            for (int i = 0; i < numberOfPages; i++) {
                int index = random.nextInt(sampleUrls.length);
                String url = sampleUrls[index];
                String title = sampleTitles[index] + " #" + (i + 1);
                LocalDateTime timestamp = startTime.plusSeconds(i + 1);
                
                writer.append(url)
                      .append(",")
                      .append(title)
                      .append(",")
                      .append(timestamp.toString())
                      .append("\n");
            }
            System.out.println("Successfully generated " + numberOfPages + " sample web pages in " + csvFile);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to CSV file.");
            e.printStackTrace();
        }
    }
}
