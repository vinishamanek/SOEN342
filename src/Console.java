import java.util.Scanner;

public class Console {

    private User user;
    private Scanner scanner;

    public Console(User user) {
        this.user = user;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            String input = prompt();
            if (input.equals("exit")) {
                break;
            }
            System.out.println(input);
        }
    }

    private String prompt() {
        return this.prompt("> ");
    }

    private String prompt(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public void cleanup() {
        scanner.close();
    }

}
