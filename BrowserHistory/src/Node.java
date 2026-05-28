import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Node {

    private String url;
    private String title;
    private LocalDateTime timestamp;
    Node prev;
    Node next;

    public Node(String url, String title, LocalDateTime timestamp) {
        this.url = url;
        this.title = title;
        this.timestamp = timestamp;
        this.prev = null;
        this.next = null;
    }

    public Node(String url, String title) {
        this(url, title, LocalDateTime.now());
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return timestamp.format(formatter);
    }

    @Override
    public String toString() {
        return "[" + getFormattedTimestamp() + "] " + title + " (" + url + ")";
    }
}
