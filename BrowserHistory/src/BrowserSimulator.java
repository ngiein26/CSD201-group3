import java.util.Scanner;

public class BrowserSimulator {

    private static BrowserHistory history;
    private static Scanner scanner;

    public static void main(String[] args) {
        history = new BrowserHistory();
        scanner = new Scanner(System.in);
        
        System.out.println("=== BROWSER HISTORY SIMULATION SYSTEM ===");
        runMainLoop();
        
        scanner.close();
        System.out.println("Exited simulator.");
    }

    private static void runMainLoop() {
        while (true) {
            System.out.println("\n-------------------------------------------");
            Node current = history.getCurrentPage();
            if (current != null) {
                System.out.println("You are at: " + current.getTitle() + " (" + current.getUrl() + ")");
            } else {
                System.out.println("You are at: Blank Page (Default Page)");
            }
            
            displayMenu();
            System.out.print("Select function (0-4): ");
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    handleVisit();
                    break;
                case "2":
                    handleBackward();
                    break;
                case "3":
                    handleForward();
                    break;
                case "4":
                    history.showHistory();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid choice, please select again!");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n--- NAVIGATION MENU ---");
        System.out.println("1. Visit   - Access a new webpage");
        System.out.println("2. Back    - Go to previous page");
        System.out.println("3. Forward - Go to next page");
        System.out.println("4. History - View entire history");
        System.out.println("0. Exit    - Exit browser");
    }

    private static void handleVisit() {
        System.out.print("Enter URL: ");
        String url = scanner.nextLine();
        if (url.trim().isEmpty()) {
            System.out.println("Error: URL cannot be empty!");
            return;
        }
        
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        if (title.trim().isEmpty()) {
            title = "Untitled";
        }
        
        history.visit(url, title);
        System.out.println("=> Successfully visited: " + title);
    }

    private static void handleBackward() {
        if (!history.canGoBack()) {
            System.out.println("=> Cannot go back! You are at the first page of the session.");
            return;
        }
        
        Node previous = history.back();
        System.out.println("=> Went back to: " + previous.getTitle());
    }

    private static void handleForward() {
        if (!history.canGoForward()) {
            System.out.println("=> Cannot go forward! You are at the latest page.");
            return;
        }
        
        Node next = history.forward();
        System.out.println("=> Went forward to: " + next.getTitle());
    }
}
