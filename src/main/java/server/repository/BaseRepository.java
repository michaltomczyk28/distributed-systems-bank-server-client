package server.repository;

import server.database.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BaseRepository {
    protected Database database;
    public BaseRepository() {
        this.database = Database.getInstance();
    }

    protected Statement createStatement() throws SQLException {
        return this.database.getConnection().createStatement();
    }

    protected PreparedStatement prepareStatement(String sql) throws SQLException {
        return this.database.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }
}
