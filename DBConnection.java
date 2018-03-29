package application;

import java.sql.*;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;



public class DBConnection {
	
	public static void databaseConnection() {

		try {
			// 1. Get connection to database
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/autoqdb", "root", "");
			System.out.println("Successful Connection");
			// 2. Create Statement
			Statement myStmt = myConnection.createStatement();
			// 3. Execute a SQL query
			ResultSet myRs = myStmt.executeQuery("SELECT * FROM flightinfo");
			// 4. Process the result set
			while (myRs.next()) {
				System.out.println(myRs.getString("time") + ", " + myRs.getString("destination") + ", "
						+ myRs.getString("airline") + ", " + myRs.getString("flightnumber") + ", "
						+ myRs.getString("terminal") + ", " + myRs.getString("remarks"));
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}

	}

	public static void getFlight_time() {
		ResultSet myRowCount;
		int i = 0;
		int rowCount = 0;
		try {
			// Get connection to database
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/autoqdb", "root", "");
			System.out.println("Successful Connection");
			// Create Statement
			Statement myStmtRowCount = myConnection.createStatement();
			Statement myStmtTime = myConnection.createStatement();
			// Execute a SQL query
			ResultSet myTimeRs = myStmtRowCount.executeQuery("SELECT time FROM flightinfo");
			// Return the Row Count
			myRowCount = myStmtTime.executeQuery("select count(*) from (SELECT * FROM autoqdb.flightinfo) mySubQuery");
			// rowCount = myRowCount.getInt("count(*)");
			while (myRowCount.next()) {
				rowCount = (myRowCount.getInt("count(*)"));
			}
			// Process the result set
			while (myTimeRs.next()) {
				// for (int i = 0; i < rowCount; i++) {
				String time = (myTimeRs.getString("time"));
				System.out.println(myTimeRs.getString("time"));
				i++;
				// }
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}

	}

}
