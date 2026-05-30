package gigastream.auth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitialise {

    private static Connection connection = null;

    public DatabaseInitialise() {
        GetInstance();
        // connection = DriverManager.getConnection("jdbc:sqlite:auth.db");
        SetupDatabase();
    }

    public static synchronized Connection GetInstance() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:auth.db");
            } catch (SQLException e) {
                e.printStackTrace(System.err);
            }
        }
        return connection;
    }

    private void SetupDatabase() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS auth ("        
                + "       name text PRIMARY KEY,"
                + "       key text NOT NULL"
                + ");";

                try (Statement stmnt = connection.createStatement()) {
                    stmnt.execute(sql);
                }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }
}
