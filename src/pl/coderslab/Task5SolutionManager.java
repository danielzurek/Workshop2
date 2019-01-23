package pl.coderslab;

import pl.coderslab.records.Exercises;
import pl.coderslab.records.Solutions;
import pl.coderslab.records.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;

public class Task5SolutionManager {

    public static void main(String[] args) {
        try (Connection connection = ConnectionProvider.getConnection()) {

            boolean loop = true;

            while (loop == true){


                System.out.println("Please choose one of below options: \n [ADD] - to add solution \n [VIEW] - to view yours done exercises \n [QUIT] - to quit program");
                Scanner scanner = new Scanner(System.in);
                String line = scanner.nextLine();
                line.toLowerCase();

                if (line.equals("add")) {

                    System.out.println("Enter yours user ID");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.println(Solutions.loadAllUnsolved(connection, id));
                    System.out.println("Enter ID of exercises to add solution");
                    int exercise_id = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter solution description");
                    String description = scanner.nextLine();
//                    Solutions solutions = new Solutions(id, description, exercise_id);
//                    solutions.save(connection);
                    String sql = "UPDATE solutions SET updated=?, description=? WHERE id=?";

                    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                        Timestamp timestamp = new Timestamp(new Date().getTime());
                        preparedStatement.setString(1, String.valueOf(timestamp));
                        preparedStatement.setString(2, description);
                        preparedStatement.setInt(3, exercise_id);
                        preparedStatement.execute();
                    }



                } else if(line.equals("view")){

                    System.out.println("Enter yours user ID");
                    int id = Integer.parseInt(scanner.nextLine());
                   System.out.println(Solutions.loadAllSolvedByUserId(connection, id));



                } else if (line.equals("quit")){

                    loop = false;
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
