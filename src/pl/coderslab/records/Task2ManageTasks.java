package pl.coderslab.records;

import pl.coderslab.ConnectionProvider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Task2ManageTasks {

    public static void main(String[] args) {
        try (Connection connection = ConnectionProvider.getConnection()) {
                boolean loop = true;
            while (loop == true){
                System.out.println(Exercises.findAll(connection));
                System.out.println("Please choose one of below options: \n [ADD] - to add exercise \n [EDIT] - to edit exercise \n [DELETE] - to delete exercise \n [QUIT] - to quit program");
                Scanner scanner = new Scanner(System.in);
                String line = scanner.nextLine();
                line.toLowerCase();


                    if (line.equals("add")) {
                        System.out.println("Put the title of exercise");
                        String title = scanner.nextLine();
                        System.out.println("Describe this exercise");
                        String description = scanner.nextLine();
                        Exercises exercises = new Exercises(title, description);
                        exercises.save(connection);

                    } else if(line.equals(("edit"))) {

                        System.out.println("Enter ID of exercise which you would like to edit");
                        int id = Integer.parseInt(scanner.nextLine());


                        System.out.println("Edit title");
                        String title = scanner.nextLine();
                        System.out.println("Edit description");
                        String description = scanner.nextLine();
                        Exercises exercises = new Exercises(id, title, description);
                        exercises.save(connection);

                    } else if (line.equals("delete")){

                        System.out.println("Enter ID of exercises which you would liek to delete");
                        int id = scanner.nextInt();
                        Exercises exercises = new Exercises(id);
                        exercises.delete(connection);

                    }   else if (line.equals("quit")){

                        loop = false;

                    }


            }










        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
