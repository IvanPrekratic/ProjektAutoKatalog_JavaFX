package hr.java.projekt.login;

import java.sql.*;

public class LoginPristupBazi{
    public static String autentikacija(LoginPodaci podaci){
        String username = podaci.getUsername();
        String password = podaci.getPassword();
        String endRole = "";

        Connection veza = null;
        Statement stmt = null;
        ResultSet rs = null;

        String userNameDB;
        String passwordDB;
        String roleDB;

        try{
            veza = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/java-autokatalog", "iprekrati", "iprekrati");
            stmt = veza.createStatement();
            rs = stmt.executeQuery("SELECT * FROM USERS");
            while (rs.next()){
                int id = rs.getInt("id");
                userNameDB = rs.getString("username");
                passwordDB = rs.getString("password");
                roleDB = rs.getString("role");

                if(username.equals(userNameDB) && password.equals(passwordDB) && roleDB.equals("Admin"))
                    endRole = "Admin";
                else if(username.equals(userNameDB) && password.equals(passwordDB) && roleDB.equals("User"))
                    endRole = "User";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
                stmt.close();
                veza.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return endRole;
    }
}
