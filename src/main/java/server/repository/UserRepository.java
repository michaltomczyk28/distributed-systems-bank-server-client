package server.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository extends BaseRepository {
    public String authenticateUser(String username, String password) {
        try {
            PreparedStatement statement = this.prepareStatement("select id from user where username like ? and password like ?");
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();
            return rs.getString("id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
