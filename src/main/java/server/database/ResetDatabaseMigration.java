package server.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ResetDatabaseMigration {
    public static void main(String[] args) throws SQLException {
        Database db = Database.getInstance();
        Statement statement = db.getConnection().createStatement();

        statement.executeUpdate("drop table if exists user");
        statement.executeUpdate("create table user (id integer primary key autoincrement, firstname string, lastname string, pesel string, username string, password string, is_admin boolean)");

        statement.executeUpdate("drop table if exists account");
        statement.executeUpdate("create table account (account_number string, user_id integer, balance integer, primary key (account_number), foreign key (user_id) references user(id))");


        // User 1
        statement.executeUpdate("insert into user values(1, 'Jan', 'Kowalski', '01220314894', 'jankowalski', 'qwerty123', false)");
        statement.executeUpdate("insert into account values('PL37240463090473155465742491', 1, 12000)");

        // User 2
        statement.executeUpdate("insert into user values(2, 'Anna', 'Nowak', '95041215492', 'annanowak', 'qwerty123', false)");
        statement.executeUpdate("insert into account values('PL35109024022555225825342519', 2, 2500)");

        // Admin
        statement.executeUpdate("insert into user values(3, '', '', '', 'admin', 'admin', true)");

        ResultSet rs = statement.executeQuery("select name from sqlite_schema where type = 'table' and name not like 'sqlite_%'");
        System.out.println("The database has been reset.");
    }
}
