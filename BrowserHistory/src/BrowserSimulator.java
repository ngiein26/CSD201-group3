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
            System.out.print("Select function (0-6): ");
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
                case "5":
                    handleGenerateData();
                    break;
                case "6":
                    handleRemovePage();
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
        System.out.println("5. Generate Data - Generate dummy history data");
        System.out.println("6. Remove Page   - Remove a page from history by index");
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

    private static void handleGenerateData() {
        history.visit("google.com", "Google");
        history.visit("youtube.com", "YouTube");
        history.visit("stackoverflow.com", "StackOverflow");
        history.visit("github.com", "GitHub");
        history.visit("facebook.com", "Facebook");
        history.visit("twitter.com", "Twitter");
        history.visit("netflix.com", "Netflix");
        history.visit("amazon.com", "Amazon");
        history.visit("wikipedia.org", "Wikipedia");
        history.visit("reddit.com", "Reddit");
        System.out.println("=> Successfully generated 10 dummy pages.");
    }

    private static void handleRemovePage() {
        if (history.isEmpty()) {
            System.out.println("=> History is empty. Cannot remove page.");
            return;
        }
        history.showHistory();
        System.out.print("Enter the index of the page to remove (1-" + history.getSize() + "): ");
        try {
            int index = Integer.parseInt(scanner.nextLine());
            boolean success = history.removePage(index);
            if (success) {
                System.out.println("=> Successfully removed page at index " + index);
            } else {
                System.out.println("=> Invalid index!");
            }
        } catch (NumberFormatException e) {
            System.out.println("=> Invalid input. Please enter a valid number.");
        }
    }
}
