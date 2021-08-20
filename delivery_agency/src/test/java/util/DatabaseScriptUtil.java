package util;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class DatabaseScriptUtil {

    private DatabaseScriptUtil() {
    }

    public static void executeSQL(String filePath, Connection connection) throws SQLException {
        try (Reader reader = new BufferedReader(new FileReader(filePath))) {
            ScriptRunner sr = new ScriptRunner(connection);
            sr.runScript(reader);
        } catch (Exception e) {
            log.error("Failed to Execute" + filePath + " The error is " + e.getMessage());
        }
    }
}
