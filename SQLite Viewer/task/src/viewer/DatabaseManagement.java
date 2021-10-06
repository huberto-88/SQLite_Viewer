package viewer;

import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManagement {
    private final SQLiteDataSource dataSource = new SQLiteDataSource();
    private ArrayList<String> columnNames;
    private ArrayList<ArrayList<String>> tableContent;

    public DatabaseManagement(String fileName) {
        String url = "jdbc:sqlite:" + fileName;
    //    url = "jdbc:sqlite:C:/Users/Hubert/SQLite Viewer/SQLite Viewer/task/" + fileName;
        dataSource.setUrl(url);
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return connection;
    }

    public ArrayList<String> getTablesNames() {
        String sqlQuery = "SELECT * FROM sqlite_master where type = 'table';";
        ArrayList<String> tablesNames = new ArrayList<>();

        try (Statement statement = getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sqlQuery)) {
                while (resultSet.next()) {
                    tablesNames.add(resultSet.getString("name"));
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return tablesNames;
    }

    public void setRequest(String request) {
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList<String> getColumnNames() {
        return columnNames;
    }

    public ArrayList<ArrayList<String>> getData() {
        return tableContent;
    }
}