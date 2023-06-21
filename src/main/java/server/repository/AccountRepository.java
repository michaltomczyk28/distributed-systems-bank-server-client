package server.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRepository extends BaseRepository {
    public double getBalanceByUserId(String userId) {
        try {
            PreparedStatement statement = this.prepareStatement("select balance from account where user_id = ?");
            statement.setString(1, userId);

            ResultSet rs = statement.executeQuery();

            return Integer.parseInt(rs.getString("balance")) / 100.0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void depositMoneyForUser(String userId, double amount) {
        try {
            System.out.println(amount);
            PreparedStatement statement = this.prepareStatement("update account set balance = balance + ? where user_id = ?");
            System.out.println((int) (amount * 100));
            statement.setInt(1, (int) (amount * 100));
            statement.setString(2, userId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void withdrawMoneyForUser(String userId, double amount) {
        try {
            PreparedStatement statement = this.prepareStatement("update account set balance = balance - ? where user_id = ?");
            statement.setDouble(1, (int) (amount * 100));
            statement.setString(2, userId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
