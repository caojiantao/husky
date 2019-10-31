package cn.caojiantao.husky.system;

import cn.caojiantao.husky.system.model.security.SystemUser;

/**
 * @author caojiantao
 */
public class LoginContext {

    private static ThreadLocal<SystemUser> userLocal = new ThreadLocal<>();

    public static void setUser(SystemUser systemUser) {
        userLocal.set(systemUser);
    }

    public static SystemUser getUser() {
        return userLocal.get();
    }

    public static void clearUser() {
        userLocal.remove();
    }
}
