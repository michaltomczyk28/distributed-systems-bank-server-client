package server.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ResetDatabaseMigration {
    public static void main(String[] args) throws SQLException {
        Database db = Database.getInstance();
        Statement statement = db.getConnection().createStatement();

        statement.executeUpdate("drop table if exists user");
        statement.executeUpdate("create table user (id integer, firstname string, lastname string, pesel string, username string, password string, primary key (id))");

        statement.executeUpdate("drop table if exists account");
        statement.executeUpdate("create table account (account_number string, user_id integer, balance real, primary key (account_number), foreign key (user_id) references user(id))");


        statement.executeUpdate("insert into user values(1, 'Jan', 'Kowalski', '01220314894', 'jankowalski', 'qwerty123')");
        statement.executeUpdate("insert into account values('PL37240463090473155465742491', 1, 120.3)");

        ResultSet rs = statement.executeQuery("select name from sqlite_schema where type = 'table' and name not like 'sqlite_%'");
        System.out.println("The database has been reset.");
    }
}
