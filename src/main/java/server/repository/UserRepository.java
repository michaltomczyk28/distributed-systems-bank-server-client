package server.repository;

import server.model.UserInformation;

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

    public UserInformation getUserInformation(String userId) {
        try {
            PreparedStatement statement = this.prepareStatement("""
                    select u.firstname, u.lastname, u.username, u.pesel, a.account_number
                    from user u
                    join account a on u.id = a.user_id
                    where user_id like ?""");
            statement.setString(1, userId);

            ResultSet rs = statement.executeQuery();

            return new UserInformation(
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getString("username"),
                    rs.getString("pesel"),
                    rs.getString("account_number")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
