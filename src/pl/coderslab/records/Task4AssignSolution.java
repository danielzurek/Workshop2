package pl.coderslab.records;

import pl.coderslab.ConnectionProvider;

import javax.swing.plaf.synth.SynthEditorPaneUI;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.Scanner;

public class Task4AssignSolution {

    public static void main(String[] args) {
        try (Connection connection = ConnectionProvider.getConnection()) {

            boolean loop = true;

            while (loop == true){

                System.out.println("Please choose one of below options: \n [ADD] to assign exercise for user \n [VIEW] - view assigned exercises  \n [QUIT] - to quit program");
                Scanner scanner = new Scanner(System.in);
                String line = scanner.nextLine();
                line.toLowerCase();

                if (line.equals("add")){

                    System.out.println(User.findAll(connection));
                    System.out.println("Enter users ID to assign task");
                    int user_id = Integer.parseInt(scanner.nextLine());
                    System.out.println(Exercises.findAll(connection));
                    System.out.println("Enter Exercise ID to assign");
                    int exercise_id = Integer.parseInt(scanner.nextLine());
                    Solutions solutions = new Solutions(exercise_id, user_id);
                    solutions.save(connection);


                } else if(line.equals("view")){

                    System.out.println("Enter ID of user to show his solutions");
                    int user_id = Integer.parseInt(scanner.nextLine());
                    System.out.println(Solutions.loadAllByUserId(connection, user_id));


                } else if (line.equals("quit")){

                    loop = false;
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
