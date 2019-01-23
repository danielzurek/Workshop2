package pl.coderslab.records;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Solutions {
    private Integer id;
    private String created;
    private String updated;
    private String description;
    private int exercise_id;
    private int user_id;

    public Solutions(int exercise_id, int user_id) {
        this.exercise_id = exercise_id;
        this.user_id = user_id;
    }


    public Solutions() {
    }

    public Solutions(Integer id, String description, int exercise_id) {
        this.id = id;
        this.description = description;
        this.exercise_id = exercise_id;
    }

    public static Solutions findById(Connection connection, int id) throws SQLException {
        String sql = "SELECT * FROM solutions WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Solutions solution = new Solutions();
                solution.id = id;
                solution.created = rs.getString("created");
                solution.updated = rs.getString("updated");
                solution.description = rs.getString("description");
                solution.exercise_id = rs.getInt("exercise_id");
                solution.user_id = rs.getInt("user_id");


                return solution;
            }
        }
        return null;
    }

    public static List<Solutions> findAll(Connection connection) throws SQLException {

        List<Solutions> solutions = new ArrayList<>();

        String sql = "SELECT * FROM solutions";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Solutions solution = new Solutions();
                solution.id = rs.getInt("id");;
                solution.created = rs.getString("created");
                solution.updated = rs.getString("updated");
                solutions.add(solution);
            }
        }

        return solutions;
    }

    public Integer getId() {
        return id;
    }

    public String getCreated() {
        return created;
    }

    public void setTCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(Integer exercise_id) {
        this.exercise_id = exercise_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public void setCreated(String updated) {
        this.updated = updated;
    }


    @Override
    public String toString() {
        return "Solutions{" +
                "id=" + id +
                ", created='" + created + '\'' +
                ", updated'" + updated + '\'' +
                ", description'" + description + '\'' +
                ", exercise_id'" + exercise_id + '\'' +
                ", user_id'" + user_id + '\'' +
                '}';
    }

    public void save(Connection connection) throws SQLException {
        if (id == null) {

            String sql = "INSERT INTO solutions(created, exercise_id, user_id) VALUES(?, ?, ?)";;
// Opcjinalna pozycja w bazie solved_by. Finalnie nie zaimplementowana
//            String sql2 = "UPDATE exercises SET solved_by=? WHERE id=?";

            String[] generatedColumns = {"id"};

            try (PreparedStatement statement = connection.prepareStatement(sql, generatedColumns)) {
                Timestamp timestamp = new Timestamp(new Date().getTime());
                statement.setString(1, String.valueOf(timestamp));
                statement.setInt(2, exercise_id);
                statement.setInt(3, user_id);


                statement.executeUpdate();

                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);

                }
            }

//            try (PreparedStatement statement = connection.prepareStatement(sql2)){
//
//                statement.setInt(1, user_id);
//                statement.setInt(2, exercise_id);
//
//                statement.executeUpdate();
//
//
//            }
        } else {
            String sql = "UPDATE solutions SET created=?, updated=?, description=?, exercise_id=?, user_id=? WHERE id=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, created);
                statement.setString(2, updated);
                statement.setString(3, description);
                statement.setInt(4, exercise_id);
                statement.setInt(5, user_id);
                statement.execute();
            }
        }
    }

    public void delete(Connection connection) throws SQLException {
        if (id != null) {

            String sql = "DELETE FROM solutions WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.execute();
            }

            id = null;
        }
    }

    public static List<Solutions> loadAllByUserId(Connection connection, int id) throws SQLException {

        List<Solutions> solutions = new ArrayList<>();

        String sql = "SELECT * FROM solutions WHERE user_id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Solutions solution = new Solutions();
                solution.id = rs.getInt("id");;
                solution.created = rs.getString("created");
                solution.updated = rs.getString("updated");
                solution.description = rs.getString("description");
                solution.exercise_id = rs.getInt("exercise_id");
                solutions.add(solution);
            }
        }

        return solutions;
    }

    public static List<Solutions> loadAllByExerciseId(Connection connection, int id) throws  SQLException{

        List<Solutions> solutions = new ArrayList<>();

        String sql = "SELECT * FROM solutions WHERE exercise_id=? ORDER BY created;";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Solutions solution = new Solutions();
                solution.created = rs.getString("created");
                solution.updated = rs.getString("updated");
                solution.description = rs.getString("description");
                solution.exercise_id = rs.getInt("exercise_id");
                solutions.add(solution);

            }

        }
        return solutions;

    }

    public static List<Solutions> loadAllUnsolved(Connection connection, int id){
        List<Solutions> solutions = new ArrayList<>();
//        String sql = "SELECT * FROM exercises WHERE solved_by IS NULL ";
        String sql = "SELECT * FROM solutions where user_id = ? AND description IS NULL";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Solutions solution = new Solutions();
                solution.id = resultSet.getInt("id");
                solution.created = resultSet.getString("created");
                solution.description = resultSet.getString("updated");
                solution.description = resultSet.getString("description");
                solutions.add(solution);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return solutions;
    }
    public static List<Solutions> loadAllSolvedByUserId(Connection connection, int id){
        List<Solutions> solutions = new ArrayList<>();
        String sql = "SELECT * FROM solutions WHERE user_id =? AND description IS NOT NULL";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Solutions solution = new Solutions();
                solution.id = resultSet.getInt("id");
                solution.created = resultSet.getString("created");
                solution.updated = resultSet.getString("updated");
                solution.description = resultSet.getString("description");
                solution.exercise_id =  resultSet.getInt("exercise_id");
                solution.user_id =  resultSet.getInt("user_id");
                solutions.add(solution);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return solutions;
    }

}




