package server.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRepository extends BaseRepository {
    public float getBalanceByUserId(String userId) {
        try {
            PreparedStatement statement = this.prepareStatement("select balance from account where user_id = ?");
            statement.setString(1, userId);

            ResultSet rs = statement.executeQuery();

            return Float.parseFloat(rs.getString("balance"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
