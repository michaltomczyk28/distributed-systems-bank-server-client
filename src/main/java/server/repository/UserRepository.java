package server.repository;

import server.model.NewUserDTO;
import server.model.UserInformation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserRepository extends BaseRepository {
    public String authenticateUser(String username, String password) {
        try {
            PreparedStatement statement = this.prepareStatement("select id from user where username like ? and password like ? and is_admin = 0");
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();
            return rs.getString("id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String authenticateAdmin(String username, String password) {
        try {
            PreparedStatement statement = this.prepareStatement("select id from user where username like ? and password like ? and is_admin = 1");
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

    public String createUser(NewUserDTO newUser) {
        try {
            PreparedStatement statement = this.prepareStatement("insert into user(firstname, lastname, pesel, username, password, is_admin) values (?, ?, ?, ?, ?, false)");
            statement.setString(1, newUser.getFirstname());
            statement.setString(2, newUser.getLastname());
            statement.setString(3, newUser.getPesel());
            statement.setString(4, newUser.getUsername());
            statement.setString(5, newUser.getPassword());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet rs = statement.getGeneratedKeys();

                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public String getUserIdByUsername(String username) {
        try {
            PreparedStatement statement = this.prepareStatement("select id from user where username like ?");
            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();

            return rs.getString("id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isUsernameUnique(String username) {
        try {
            PreparedStatement statement = this.prepareStatement("select id from user where username like ?");
            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();

            return !rs.isBeforeFirst();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUser(String id, String firstname, String lastname, String pesel) {
        try {
            PreparedStatement statement = this.prepareStatement("update user set firstname = ?, lastname = ?, pesel = ? where id like ?");
            statement.setString(1, firstname);
            statement.setString(2, lastname);
            statement.setString(3, pesel);
            statement.setString(4, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
