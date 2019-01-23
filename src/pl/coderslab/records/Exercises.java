package pl.coderslab.records;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Exercises {
    private Integer id;
    private String title;
    private String description;

    public Exercises(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Exercises() {
    }

    public Exercises(Integer id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Exercises(Integer id) {
        this.id = id;
    }

    public static Exercises findById(Connection connection, int id) throws SQLException {
        String sql = "SELECT * FROM exercises WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Exercises exercise = new Exercises();
                exercise.id = id;
                exercise.title = rs.getString("title");
                exercise.description = rs.getString("description");

                return exercise;
            }
        }
        return null;
    }

    public static List <Exercises> findAll(Connection connection) throws SQLException {

        List<Exercises> exercises = new ArrayList<>();

        String sql = "SELECT * FROM exercises";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Exercises exercise = new Exercises();
                exercise.id = rs.getInt("id");;
                exercise.title = rs.getString("title");
                exercise.description = rs.getString("description");
                exercises.add(exercise);
            }
        }

        return exercises;
    }

    public Integer getId() {
        return id;
    }

    public String getTtilel() {
        return title;
    }

    public void setTitlel(String titlel) {
        this.title = titlel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description'" + description + '\'' +
                '}';
    }

    public void save(Connection connection) throws SQLException {
        if (id == null) {

            String sql = "INSERT INTO exercises(title, description) VALUES(?, ?)";
            String[] generatedColumns = {"id"};

            try (PreparedStatement statement = connection.prepareStatement(sql, generatedColumns)) {
                statement.setString(1, title);
                statement.setString(2, description);
                statement.executeUpdate();

                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }


        } else {
            String sql = "UPDATE exercises SET title=?, description=? WHERE id=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, title);
                statement.setString(2, description);
                statement.setInt(3, id);
                statement.execute();
            }
        }
    }

    public void delete(Connection connection) throws SQLException {
        if (id != null) {

            String sql = "DELETE FROM exercises WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.execute();
            }

            id = null;
        }
    }
}


