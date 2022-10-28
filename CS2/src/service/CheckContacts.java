package service;

import java.sql.*;

public class CheckContacts {
    public static void show() throws ClassNotFoundException, SQLException {
        String connectionUrl = "jdbc:mysql://127.0.0.1:3306/springboot-test;user=root;password=123456";
        // Declare the JDBC objects.
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        // Establish the connection.
        Class.forName("mysql.jdbc.SQLServerDriver");
        con = DriverManager.getConnection(connectionUrl);
        // Create and execute an SQL statement that returns some data.
        String SQL = "select * from mail_line ";
        stmt = con.createStatement();
        rs = stmt.executeQuery(SQL);

        // Iterate through the data in the result set and display it.
        while (rs.next()) {
            System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t");
        }
    }
}

