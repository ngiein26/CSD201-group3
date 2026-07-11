<<<<<<< Updated upstream
g
=======
>>>>>>> Stashed changes
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

    public void loadFromCSV(String filename) {
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(filename))) {
            String line = br.readLine(); // skip header
            int loaded = 0;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String url = parts[0];
                    String title = parts[1];
                    java.time.LocalDateTime timestamp = java.time.LocalDateTime.parse(parts[2]);
                    visit(url, title, timestamp);
                    loaded++;
                }
            }
            System.out.println("=> Successfully loaded " + loaded + " history records from " + filename);
        } catch (Exception e) {
            System.out.println("Error loading from CSV: " + e.getMessage());
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
