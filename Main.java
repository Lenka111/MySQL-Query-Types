import helper.DBHandler;

import java.sql.*;
import java.util.zip.CheckedOutputStream;


public class Main{
    //class fields
    private static DBHandler dbHandler;
    private static Connection connection;
    private static PreparedStatement preparedStatement;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        dbHandler = new DBHandler();
        connection = dbHandler.getConnection();
      //  writeToDB("Vicky", "Voinu", "vikusik", "Natoma ave", 8);
    //    readFromDB();
        updateDB("Elena", "Voinu", "lenka111", "1234 mission Valleya", 33, 6);
        delete(3);
        readFromDB();


//        //load the driver
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        //establish the connection with the database
//        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/login?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
//                "root", "Flowers123." );

              //  ("jdbc:mysql://localhost:3306/login", "root", "Flowers123.");
        System.out.println("Connected to the database" + connection.getCatalog());
    }//end main

    //write data to the database
    public static void writeToDB(String firstname, String secondname, String username,
                                 String address, int age) throws SQLException {

        String insert = "INSERT INTO user(firstname, lastname, username, address, age)"
                + " VALUES(?,?,?,?,?)";
        try {
            useColumns(firstname, secondname, username, address, age, insert);
        } catch(SQLException e){
            System.out.println(e.getMessage());

        }
    }

    //read from database
    public static void readFromDB() throws  SQLException{
        String query = "Select * from user";
        preparedStatement = (PreparedStatement) connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            System.out.println("Names: " + resultSet.getString("firstname")+" "
            + resultSet.getString("lastname"));
        }

    }

    //update data in database
    public static void updateDB(String firstname, String secondname, String username,
    String address, int age, int id ) throws SQLException{
        String query = "UPDATE user SET firstname = ?, lastname = ?, username = ?, address = ?, age = ? WHERE userid = ?";
        try {
            useColumns(firstname, secondname, username, address, age, query);
            preparedStatement.setInt(6, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch(SQLException e){
            System.out.println(e.getMessage());

        }

    }
    //deleting data from db
    public static void delete(int id){
        String query = "DELETE FROM user WHERE userid = ?";
        try{
            preparedStatement = (PreparedStatement) connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //to avoid duplicate code i created a method for adding users and to update the database
    private static void useColumns(String firstname, String secondname, String username, String address, int age, String insert) throws SQLException {
        preparedStatement = (PreparedStatement) connection.prepareStatement(insert);
        preparedStatement.setString(1, firstname);
        preparedStatement.setString(2, secondname);
        preparedStatement.setString(3, username);
        preparedStatement.setString(4, address);
        preparedStatement.setInt(5, age);
       // preparedStatement.executeUpdate();
    }

}
