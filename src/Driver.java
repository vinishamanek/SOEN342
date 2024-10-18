public class Driver {
    public static void main(String[] args) {
        Organization organization = new Organization("Dunder Mifflin Paper Company");

        User[] users = new User[5];
        users[0] = Admin.getInstance("dwight@concordia.ca", "password", organization);
        users[1] = new Instructor("pam@concordia.ca", "password", organization, new String[] { "Montreal" },
                "Swimming");
        users[2] = new Instructor("jim@concordia.ca", "password", organization,
                new String[] { "Montreal", "Quebec City" }, "Judo");
        users[3] = new Client("michael@concordia.ca", "password", organization);
        users[4] = new Client("angela@concordia.ca", "password", organization);
        User.setUsers(users);

        Console console = new Console(null);
        console.run();
        console.cleanup();
    }
}
