
package soen342;

import soen342.database.JPAUtil;

public class Driver {
    public static void main(String[] args) {
        JPAUtil.init();
        Console console = new Console(null);
        console.run();
        console.cleanup();
        JPAUtil.closeEntityManagerFactory();
    }
}
