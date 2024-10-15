public class User {
    private static User[] users;

    private String email;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Hard coding the users in the system
    // static {
    //     users = new User[5];
    //     users[0] = new User("jim@concordia.ca", "password");
    //     users[1] = new User("pam@concordia.ca", "password");
    //     users[2] = new User("dwight@concordia.ca", "password");
    //     users[3] = new User("michael@concordia.ca", "password");
    //     users[4] = new User("angela@concordia.ca", "password");
    // }

    // hard coding the users in the system with specific roles
    static {
        users = new User[5];
        users[0] = Admin.getInstance("dwight@concordia.ca", "password"); 
        users[1] = new Instructor("pam@concordia.ca", "password", new String[]{"Montreal"}, "Swimming");
        users[2] = new Instructor("jim@concordia.ca", "password",  new String[]{"Montreal", "Quebec City"}, "Judo");
        users[3] = new Client("michael@concordia.ca", "password");
        users[4] = new Client("angela@concordia.ca", "password");

    }

    public String getEmail() {
        return this.email;
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
