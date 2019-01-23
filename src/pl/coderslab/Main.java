package pl.coderslab;

import pl.coderslab.records.User;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        try (Connection connection = ConnectionProvider.getConnection()) {
            System.out.println(User.findAll(connection));
        }
    }
}
