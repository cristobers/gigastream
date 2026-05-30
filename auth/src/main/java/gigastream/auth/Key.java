package gigastream.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class Key {
    public static boolean IsValid(RTMPRequestData data) throws ResponseStatusException {
        try {
            String[] splitKey = data.streamKey().split("_");
            if (splitKey.length != 2) {
                System.out.println("Key didn't split correctly.");
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Bad key."
                );
            }

            Connection connection = DatabaseInitialise.GetInstance();
            String sql = "SELECT * FROM auth WHERE name = ? AND key = ?";

            PreparedStatement pstmnt = connection.prepareStatement(sql);
            
            String splitName   = splitKey[0];
            String splitAPIKey = splitKey[1];

            if (!IsValidLength(splitAPIKey)) {
                return false;
            }

            pstmnt.setString(1, splitName);
            pstmnt.setString(2, splitAPIKey);

            ResultSet databaseResult = pstmnt.executeQuery();
            String name = databaseResult.getString(1);
            String key  = databaseResult.getString(2);

            // good key territory
            if (splitKey[0].equals(name) && splitKey[1].equals(key)) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return false;
    }

    private static boolean IsValidLength(String key) {
        return key.length() == 32;
    }
}
