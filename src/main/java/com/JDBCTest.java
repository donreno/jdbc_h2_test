package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCTest {

    private static final String SELECT_FROM_TABLE = "SELECT * FROM PERSON;";
    private static final String CREATE_TABLE = "CREATE TABLE PERSON(" +
            "NAME VARCHAR(20)," +
            "AGE INT" +
            ");";

    private static String insertData(String name, int age) {
        return "insert into person values(" + "'" + name + "'" + ", " + age + ");";
    }

    public static void main(String[] args) {

        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:")) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(CREATE_TABLE);
                statement.execute(insertData("person1", 13));
                statement.execute(insertData("person2", 25));
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_TABLE)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        System.out.println("name -> " + resultSet.getString(1));
                        System.out.println("age -> " + resultSet.getString(2));
                        System.out.println();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
