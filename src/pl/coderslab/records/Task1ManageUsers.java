package pl.coderslab.records;

import pl.coderslab.ConnectionProvider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Task1ManageUsers {

    public static void main(String[] args) throws SQLException {
        try (Connection connection = ConnectionProvider.getConnection()) {

            outerloop: do  {
                System.out.println(User.findAll(connection));
                System.out.println("Choose one of below options:\n[ADD] add a new user,\n[EDIT] edit existing user,\n" +
                        "[DELETE] delete existing user,\n[quit] quit");
                Scanner scanner = new Scanner(System.in);
                String line = scanner.nextLine();
                line.toLowerCase();

                while (true) {

                    if (line.equals("add")) {
                        int userGroupId;
                        System.out.println("Put username: ");
                        String username = scanner.nextLine();
                        System.out.println("Put password: ");
                        String password = scanner.nextLine();
                        System.out.println("Put email address: ");
                        String email = scanner.nextLine();
                        try {
                            System.out.println("Put ID of the group which user will be assigned ");
                            userGroupId = scanner.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Incorrect group ID!");
                            break;
                        }

                        User user = new User(email,username,password,userGroupId);
                        user.save(connection);
                        break;

                    } else if (line.equals("edit")) {
                        int id;
                        int userGroupId;

                        System.out.println("Enter user ID: ");
                        try{
                            id = scanner.nextInt();
                        }catch(InputMismatchException e){
                            System.out.println("Incorrect ID!");
                            break;
                        }
                        System.out.println("Edit email address: ");
                        String email = scanner.next();
                        System.out.println("Edit username: ");
                        String username = scanner.next();
                        System.out.println("Enter a new password: ");
                        String password = scanner.next();
                        System.out.println("Edit users group ID: ");
                        try{
                            userGroupId = scanner.nextInt();

                        }catch(InputMismatchException e){
                            System.out.println("Incorrect group ID!");
                            break;
                        }

                        User user = new User(id,email,username,password,userGroupId);
                        user.save(connection);
                        break;


                    } else if(line.equals("delete")){
                        int id;
                        System.out.println("Enter ID of user you would like to delete: ");
                        try{
                            id = scanner.nextInt();
                        }catch(InputMismatchException e) {
                            System.out.println("Incorrect ID!");
                            break;
                        }
                        User.findById(connection,id);
                        User user = User.findById(connection,id);
                        user.delete(connection);
                        break;
                    }

                    else if(line.equals("quit")){
                        break outerloop;
                    }
                    else{
                        System.out.println("Incorrect type! Please check manual");
                        break;
                    }
                }
            }

            while(true);
        }

    }
}
