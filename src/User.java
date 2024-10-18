public abstract class User {
    private static User[] users;

    public static void setUsers(User[] users) {
        User.users = users;
    }

    private String email;
    private String password;
    private Organization organization;

    public User(String email, String password, Organization organization) {
        this.email = email;
        this.password = password;
        this.organization = organization;
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

    protected Organization getOrganization() {
        return this.organization;
    }

}
