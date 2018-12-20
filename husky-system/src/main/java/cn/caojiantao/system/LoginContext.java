package cn.caojiantao.system;

import cn.caojiantao.system.model.security.User;

public class LoginContext {

    private static ThreadLocal<User> userLocal = new ThreadLocal<>();

    public static void setUser(User user) {
        userLocal.set(user);
    }

    public static User getUser(){
        return userLocal.get();
    }
}
