package server.repository;

import server.model.UserInformation;

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
            PreparedStatement statement = this.prepareStatement("update account set balance = balance + ? where user_id = ?");
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

    public String getOwnerId(String accountNumber) {
        try {
            PreparedStatement statement = this.prepareStatement("select user_id from account where account_number like ?");
            statement.setString(1, accountNumber);

            ResultSet rs = statement.executeQuery();

            return rs.getString("user_id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void transferMoney(String fromUserId, String toAccountNumber, double amount) {
        try {
            // from
            PreparedStatement sourceAccountStatement = this.prepareStatement("update account set balance = balance - ? where user_id = ?");
            sourceAccountStatement.setDouble(1, (int) (amount * 100));
            sourceAccountStatement.setString(2, fromUserId);
            sourceAccountStatement.executeUpdate();

            // to
            PreparedStatement targetAccountStatement = this.prepareStatement("update account set balance = balance + ? where account_number like ?");
            targetAccountStatement.setDouble(1, (int) (amount * 100));
            targetAccountStatement.setString(2, toAccountNumber);
            targetAccountStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createAccount(String userId, String accountNumber) {
        try {
            PreparedStatement statement = this.prepareStatement("insert into account values (?, ?, 0)");
            statement.setString(1, accountNumber);
            statement.setString(2, userId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
