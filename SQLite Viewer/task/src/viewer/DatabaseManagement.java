package viewer;

import org.sqlite.SQLiteDataSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseManagement {
    private final SQLiteDataSource dataSource = new SQLiteDataSource();
    private ArrayList<String> columnNames;
    private ArrayList<ArrayList<String>> tableContent;

    public DatabaseManagement(String fileName) throws FileNotFoundException {
        String path = "C:/Users/Hubert/SQLite Viewer/SQLite Viewer/task/" + fileName;
        if (new File(path).exists()) {
            String url = "jdbc:sqlite:" + path;
            url = "jdbc:sqlite:" + path;
            dataSource.setUrl(url);
        } else {
            throw new FileNotFoundException();
        }
    }

    private Connection getConnection() throws SQLException {
        Connection connection = null;
            connection = dataSource.getConnection();
        return connection;
    }

    public ArrayList<String> getTablesNames() throws SQLException {
        String sqlQuery = "SELECT * FROM sqlite_master where type = 'table';";
        ArrayList<String> tablesNames = new ArrayList<>();

        try (Statement statement = getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sqlQuery)) {
                while (resultSet.next()) {
                    tablesNames.add(resultSet.getString("name"));
                }
            }
        }
        return tablesNames;
    }

    public void setRequest(String request) throws SQLException {
        columnNames = new ArrayList<>();
        tableContent = new ArrayList<>();

        try (Statement statement = getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(request)) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    columnNames.add(metaData.getColumnName(i));
                }

                while (resultSet.next()) {
                    ArrayList<String> colContent = new ArrayList<>();
                    for (String columnName : columnNames) {
                        colContent.add(resultSet.getString(columnName));
                    }
                    tableContent.add(colContent);
                }
            }
        }
    }

    public ArrayList<String> getColumnNames() {
        return columnNames;
    }

    public ArrayList<ArrayList<String>> getData() {
        return tableContent;
    }
}