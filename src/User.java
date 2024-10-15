public class User {
    private static User[] users;

    private String email;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Hard coding the users in the system
    static {
        users = new User[5];
        users[0] = new User("jim@concordia.ca", "password");
        users[1] = new User("pam@concordia.ca", "password");
        users[2] = new User("dwight@concordia.ca", "password");
        users[3] = new User("michael@concordia.ca", "password");
        users[4] = new User("angela@concordia.ca", "password");
    }

    public static User login(String email, String password) {
        for (User user : users) {
            if (user.email.equals(email) && user.password.equals(password)) {
                return user;
            }
        }
        return null;
    }

}
