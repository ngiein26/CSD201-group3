
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BrowserHistory {

    private Node head;
    private Node tail;
    private Node current;
    private int size;
    private int forwardCount;

    public BrowserHistory() {
        head = null;
        tail = null;
        current = null;
        size = 0;
        forwardCount = 0;
    }

    public void visit(String url, String title) {
        visit(url, title, LocalDateTime.now());
    }

    public void visit(String url, String title, LocalDateTime timestamp) {
        Node newNode = new Node(url, title, timestamp);

        if (head == null) {
            head = newNode;
            tail = newNode;
            current = newNode;
        } else {
            // Xử lý rẽ nhánh (branching)
            if (current != tail) {
                truncateForward();
            }
            current.next = newNode;
            newNode.prev = current;
            tail = newNode;
            current = newNode;
        }
        size++;
        forwardCount = 0;
    }

    public List<Node> searchByTitle(String keyword) {
        List<Node> results = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty() || isEmpty()) {
            return results;
        }

        String lowerKeyword = keyword.toLowerCase();
        Node temp = head;
        while (temp != null) {
            if (temp.getTitle() != null && temp.getTitle().toLowerCase().contains(lowerKeyword)) {
                results.add(temp);
            }
            temp = temp.next;
        }
        return results;
    }

    public void generateDummyData(int numberOfPages) {
        if (numberOfPages <= 0) {
            return;
        }

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

        Random random = new Random();
        LocalDateTime startTime = LocalDateTime.now().minusSeconds(numberOfPages).withNano(0);
        for (int i = 0; i < numberOfPages; i++) {
            int index = random.nextInt(sampleUrls.length);
            String url = sampleUrls[index];
            String title = sampleTitles[index] + " #" + (i + 1);
            LocalDateTime timestamp = startTime.plusSeconds(i + 1);
            visit(url, title, timestamp);
        }
    }

    public Node back() {
        if (!canGoBack()) {
            return null;
        }
        current = current.prev;
        forwardCount++;
        return current;
    }

    public Node forward() {
        if (!canGoForward()) {
            return null;
        }
        current = current.next;
        forwardCount--;
        return current;
    }

    public void showHistory() {
        if (isEmpty()) {
            System.out.println("History is empty!");
            return;
        }

        System.out.println("========== BROWSER HISTORY ==========");
        Node temp = head;
        int index = 1;
        while (temp != null) {
            if (temp == current) {
                System.out.println(">> " + index + ". " + temp.toString() + "  << CURRENT");
            } else {
                System.out.println("   " + index + ". " + temp.toString());
            }
            temp = temp.next;
            index++;
        }
        System.out.println("=====================================");
        System.out.println("Total: " + size + " page(s)");
    }

    public boolean canGoBack() {
        return current != null && current.prev != null;
    }

    public boolean canGoForward() {
        return current != null && current.next != null;
    }

    public Node getCurrentPage() {
        return current;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public boolean removePage(int index) {
        if (index < 1 || index > size) {
            return false;
        }

        Node temp = head;
        for (int i = 1; i < index; i++) {
            temp = temp.next;
        }

        if (temp == head) {
            head = head.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
        } else if (temp == tail) {
            tail = tail.prev;
            tail.next = null;
        } else {
            temp.prev.next = temp.next;
            temp.next.prev = temp.prev;
        }

        if (temp == current) {
            if (temp.prev != null) {
                current = temp.prev;
            } else {
                current = temp.next;
            }
        }

        forwardCount = countForwardNodes(current);

        size--;
        return true;
    }

    private void truncateForward() {
        if (current.next == null) {
            return;
        }

        current.next.prev = null;
        current.next = null;
        tail = current;
        size -= forwardCount;
        forwardCount = 0;
    }

    private int countForwardNodes(Node node) {
        int count = 0;
        if (node == null) {
            return 0;
        }

        Node temp = node.next;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }
}
