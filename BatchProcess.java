import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class BatchProcess {
    public static void main(String[] args) {
        String url = "jdbc.mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "ishangadi1235";


        try{
            Class.forName("com.sql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try{
            Connection con = DriverManager.getConnection(url,username,password);
            System.out.println("Connection established successfully");
            con.setAutoCommit(false);

            String query = "INSERT INTO employees(name,job_title,salary) VALUES(?,?,?)";

            PreparedStatement preparedStatement = con.prepareStatement(query);
            Scanner scanner = new Scanner(System.in);

            while(true){
                System.out.println("Name: ");
                String name = scanner.nextLine();
                System.out.println("Job title: ");
                String job_title = scanner.nextLine();
                System.out.println("Salary: ");
                Double salary = scanner.nextDouble();
                preparedStatement.setString(1,name);
                preparedStatement.setString(2,job_title);
                preparedStatement.setDouble(3,salary);
                scanner.nextLine();
                preparedStatement.addBatch();
                System.out.println("Add more values? Y/N");
                String decision = scanner.nextLine();
                if(decision.toUpperCase().equals("N")){
                    break;
                }
                int[] batchResult = preparedStatement.executeBatch();
                con.commit();
                System.out.println("Batch Executed successfully");
            }

        }catch(SQLException e){
            System.out.println("Connection failed");
        }
    }
}
