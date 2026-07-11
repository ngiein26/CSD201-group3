import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ChartGenerator {

    public static void generateAndOpenChart(String csvFile, int windowSize) {
        StringBuilder labels = new StringBuilder();
        StringBuilder data = new StringBuilder();
        StringBuilder movingAvg = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line = br.readLine(); // skip header
            int count = 0;
            long sum = 0;
            long[] window = new long[windowSize];
            int windowIndex = 0;
            int filled = 0;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    long timeNs = Long.parseLong(parts[2]);
                    
                    // Add to moving average window
                    sum -= window[windowIndex];
                    window[windowIndex] = timeNs;
                    sum += timeNs;
                    windowIndex = (windowIndex + 1) % windowSize;
                    if (filled < windowSize) filled++;
                    
                    long avg = sum / filled;
                    
                    // To keep HTML size manageable, we can sample the points, e.g., every 100th point
                    if (count % 100 == 0) {
                        labels.append(count).append(",");
                        data.append(timeNs).append(",");
                        movingAvg.append(avg).append(",");
                    }
                    count++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV: " + e.getMessage());
            return;
        }

        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>LRU Cache Execution Time</title>\n" +
                "    <script src=\"https://cdn.jsdelivr.net/npm/chart.js\"></script>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h2>LRU Cache O(1) Performance Chart</h2>\n" +
                "    <div style=\"width: 90%; margin: auto;\">\n" +
                "        <canvas id=\"myChart\"></canvas>\n" +
                "    </div>\n" +
                "    <script>\n" +
                "        var ctx = document.getElementById('myChart').getContext('2d');\n" +
                "        var myChart = new Chart(ctx, {\n" +
                "            type: 'line',\n" +
                "            data: {\n" +
                "                labels: [" + labels.toString() + "],\n" +
                "                datasets: [{\n" +
                "                    label: 'Raw Time (ns)',\n" +
                "                    data: [" + data.toString() + "],\n" +
                "                    borderColor: 'rgba(200, 200, 200, 0.5)',\n" +
                "                    borderWidth: 1,\n" +
                "                    fill: false,\n" +
                "                    pointRadius: 0\n" +
                "                }, {\n" +
                "                    label: 'Moving Average (ns)',\n" +
                "                    data: [" + movingAvg.toString() + "],\n" +
                "                    borderColor: 'rgba(54, 162, 235, 1)',\n" +
                "                    borderWidth: 2,\n" +
                "                    fill: false,\n" +
                "                    pointRadius: 0\n" +
                "                }]\n" +
                "            },\n" +
                "            options: {\n" +
                "                responsive: true,\n" +
                "                scales: {\n" +
                "                    y: {\n" +
                "                        beginAtZero: true,\n" +
                "                        title: { display: true, text: 'Time (ns)' }\n" +
                "                    },\n" +
                "                    x: {\n" +
                "                        title: { display: true, text: 'Request Count' }\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        });\n" +
                "    </script>\n" +
                "</body>\n" +
                "</html>";

        File htmlFile = new File("lru_chart.html");
        try (FileWriter writer = new FileWriter(htmlFile)) {
            writer.write(html);
            System.out.println("=> Successfully generated " + htmlFile.getAbsolutePath());
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(htmlFile.toURI());
                System.out.println("=> Opened chart in default web browser.");
            }
        } catch (IOException e) {
            System.out.println("Error writing HTML: " + e.getMessage());
        }
    }
}
