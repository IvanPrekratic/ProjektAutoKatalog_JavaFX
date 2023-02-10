package hr.java.projekt.login;

public class SessionMenager {
    public static String username = null;
    public static String role = null;

    public SessionMenager() {
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        SessionMenager.username = username;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        SessionMenager.role = role;
    }
}
