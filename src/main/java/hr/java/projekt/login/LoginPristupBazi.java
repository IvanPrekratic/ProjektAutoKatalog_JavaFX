package hr.java.projekt.login;

import java.io.IOException;
import java.sql.*;

public class LoginPristupBazi{
    public static String autentikacija(String username, int hash){
        String endRole = "";

        String userNameDB;
        int hashDB;
        String roleDB;

        try(Connection veza = Database.connectingToDatabase()){
            Statement stmt = veza.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERS");
            while (rs.next()){
                int id = rs.getInt("id");
                userNameDB = rs.getString("username");
                hashDB = rs.getInt("password");
                roleDB = rs.getString("role");

                if(username.equals(userNameDB) && (hash == hashDB) && roleDB.equals("Admin"))
                    endRole = "Admin";
                else if(username.equals(userNameDB) && (hash == hashDB) && roleDB.equals("User"))
                    endRole = "User";
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return endRole;
    }
}
