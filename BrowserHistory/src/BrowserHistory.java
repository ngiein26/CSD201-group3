public class BrowserHistory {

    private Node head;
    private Node tail;
    private Node current;
    private int size;

    public BrowserHistory() {
        head = null;
        tail = null;
        current = null;
        size = 0;
    }

    public void visit(String url, String title) {
        Node newNode = new Node(url, title);
        
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
    }

    public Node back() {
        if (!canGoBack()) {
            return null;
        }
        current = current.prev;
        return current;
    }

    public Node forward() {
        if (!canGoForward()) {
            return null;
        }
        current = current.next;
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

        size--;
        return true;
    }

    private void truncateForward() {
        Node temp = current.next;
        while (temp != null) {
            Node nextNode = temp.next;
            temp.prev = null;
            temp.next = null;
            temp = nextNode;
            size--;
        }
        current.next = null;
        tail = current;
    }
}
