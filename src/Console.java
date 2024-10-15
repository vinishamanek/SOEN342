import java.util.Scanner;

public class Console {

    private User user;
    private Scanner scanner;

    public Console(User user) {
        this.user = user;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        if (this.user == null) {
            this.login();
        }
        while (true) {
            String input = prompt();
            if (input.equals("exit")) {
                break;
            }
            System.out.println(input);
        }
    }

    private void login() {
        while (true) {
            System.out.println("What is your email?");
            String email = prompt();
            System.out.println("What is your password?");
            String password = prompt();
            this.user = User.login(email, password);
            if (this.user != null) {
                break;
            }
            System.out.println("Invalid credentials. Please try again.");
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
