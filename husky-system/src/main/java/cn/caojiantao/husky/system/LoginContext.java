package cn.caojiantao.husky.system;

import cn.caojiantao.husky.system.model.security.User;

/**
 * @author caojiantao
 */
public class LoginContext {

    private static ThreadLocal<User> userLocal = new ThreadLocal<>();

    public static void setUser(User user) {
        userLocal.set(user);
    }

    public static User getUser() {
        return userLocal.get();
    }

    public static void clearUser() {
        userLocal.remove();
    }
}
