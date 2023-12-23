package ucu.edu.ua;

import lombok.Getter;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Getter
public class CachedDocument implements Document {
    private Document wrappedDocument;
    private DataConnection databaseConnection;
    private String gcsPath;

    public CachedDocument(Document wrappedDocument) {
        this.wrappedDocument = wrappedDocument;
        this.databaseConnection = DataConnection.getInstance();
        this.gcsPath = wrappedDocument.getGcsPath();
    }

    @Override
    public String parse() {
        String cachedResult = getCachedResult(gcsPath);
        if (cachedResult != null) {
            System.out.println("Retrieving result from local cache.");
            return cachedResult;
        }
        String result = wrappedDocument.parse();
        cacheResult(gcsPath, result);
        return result;
    }

    @SneakyThrows
    private String getCachedResult(String gcsPath) {
        Connection connection = databaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT result FROM cache WHERE gcs_path = ?");
        preparedStatement.setString(1, gcsPath);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("result");
        }
        return null;
    }

    @SneakyThrows
    private void cacheResult(String gcsPath, String result) {
        Connection connection = databaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO cache (gcs_path, result) VALUES (?, ?)");

        preparedStatement.setString(1, gcsPath);
        preparedStatement.setString(2, result);
        preparedStatement.executeUpdate();
    }
}
