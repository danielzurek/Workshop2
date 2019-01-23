package pl.coderslab.records;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserGroups {
    private Integer id;
    private String name;

    public UserGroups(String name) {
        this.name = name;
    }

    public UserGroups() {
    }

    public UserGroups(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserGroups(Integer id) {
        this.id = id;
    }

    public static UserGroups findById(Connection connection, int id) throws SQLException {
        String sql = "SELECT * FROM user_groups WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                UserGroups groups = new UserGroups();
                groups.id = id;
                groups.name = rs.getString("name");


                return groups;
            }
        }
        return null;
    }

    public static List<UserGroups> findAll(Connection connection) throws SQLException {

        List <UserGroups> groups = new ArrayList <>();

        String sql = "SELECT * FROM user_groups";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                UserGroups group = new UserGroups();
                group.id = rs.getInt("id");;
                group.name = rs.getString("name");
                groups.add(group);
            }
        }

        return groups;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "groups_id{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }




    public void save(Connection connection) throws SQLException {
        if (id == null) {

            String sql = "INSERT INTO user_groups(name) VALUES(?)";
            String[] generatedColumns = {"id"};

            try (PreparedStatement statement = connection.prepareStatement(sql, generatedColumns)) {
                statement.setString(1, name);
                statement.executeUpdate();

                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }
        } else {
            String sql = "UPDATE user_groups SET name=? WHERE id=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setInt(2, id);
                statement.execute();
            }
        }
    }

    public void delete(Connection connection) throws SQLException {
        if (id != null) {

            String sql = "DELETE FROM user_groups WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.execute();
            }

            id = null;
        }
    }
}


