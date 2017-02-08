package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCTest {

    private static final String SELECT_FROM_TABLE = "SELECT * FROM BEER;";
    private static final String CREATE_TABLE = "CREATE TABLE BEER(" +
            "NAME VARCHAR(20)," +
            "MANF VARCHAR(20)" +
            ");";

    private static String insertData(String name, String manf) {
        return "insert into beer values(" + "'" + name + "'" + ", '" + manf + "');";
    }

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:")) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(CREATE_TABLE);
                statement.execute(insertData("Bud", "Anheuser-Busch"));
                statement.execute(insertData("Bud Lite", "Anheuser-Busch"));
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_TABLE)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        System.out.println("name -> " + resultSet.getString(1));
                        System.out.println("manufacturer -> " + resultSet.getString(2));
                        System.out.println();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
