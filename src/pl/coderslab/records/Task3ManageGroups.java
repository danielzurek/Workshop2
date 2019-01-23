package pl.coderslab.records;

import pl.coderslab.ConnectionProvider;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Task3ManageGroups {

    public static void main(String[] args) {
        try (Connection connection = ConnectionProvider.getConnection()) {

            boolean loop = true;

            while (loop == true){

                System.out.println(UserGroups.findAll(connection));
                System.out.println("Please choose one of below options: \n [ADD] - to add group \n [EDIT] - to edit group \n [DELETE] - to delete group \n [QUIT] to quit program");
                Scanner scanner = new Scanner(System.in);
                String line = scanner.nextLine();
                line.toLowerCase();

                if (line.equals("add")){

                    System.out.println("Please enter name of the group");
                    String name = scanner.nextLine();
                    UserGroups userGroups = new UserGroups(name);
                    userGroups.save(connection);

                } else if(line.equals("edit")){

                    System.out.println("Enter ID of group which you would like to edit");

                    try{
                        int id = Integer.parseInt(scanner.nextLine());
                        System.out.println("Enter new name");
                        String name = scanner.nextLine();
                        UserGroups userGroups = new UserGroups(id, name);
                        userGroups.save(connection);
                    }catch (SQLIntegrityConstraintViolationException e ){
                        System.out.println("Duplicated entry!");
                    }



                } else if (line.equals("delete")) {

                    System.out.println("Enter ID of user group to delete");
                    int id = Integer.parseInt(scanner.nextLine());
                    UserGroups userGroups = new UserGroups(id);
                    userGroups.delete(connection);


                } else if (line.equals("quit")){

                    loop = false;
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
