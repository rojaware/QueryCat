package com.rojaware.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.rojaware.query.model.Query;

/**
 * Simple Java Program to connect Oracle database by using Oracle JDBC thin
 * driver Make sure you have Oracle JDBC thin driver in your classpath before
 * running this program To run this sample, you need to add ojdbc5.jar (or 6 or
 * 7 ) into your classpath. If you run most application on tomcat server, you
 * may place the jar to <app_server>\lib folder, where most common jar libary
 * files exist Then add your ojdbc5.jar into classpath in your project built
 * path.
 * 
 * @see http://www.mkyong.com/jdbc/connect-to-oracle-db-via-jdbc-driver-java/
 * @author lees4
 */
public class OracleJdbcExample {

	public static void main(String args[]) throws SQLException {
		// URL of Oracle database server
		String url = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=cpgdtdbv01)(PORT=1515))(CONNECT_DATA=(SERVICE_NAME=FATCATS_DEV_DEFAULT.tdbank.ca)))";

		// properties for creating connection to Oracle database
		Properties props = new Properties();
		props.setProperty("user", "gdtportaldev");
		props.setProperty("password", "d6v4por7sur");

		// creating connection to Oracle database using JDBC
		Connection conn = DriverManager.getConnection(url, props);

//		String sql = "select sysdate as current_day from dual";
		String sql = "select * from query";

		// creating PreparedStatement object to execute query
		PreparedStatement preStatement = conn.prepareStatement(sql);

		ResultSet result = preStatement.executeQuery();

		while (result.next()) {
			Query q = new Query();
			q.setId(result.getInt("ID"));
			q.setName(result.getString("NAME"));
			q.setSql(result.getString("SQL"));
			q.setMap(result.getString("MAP"));
			System.out.println("Current Date from Oracle : " + q.toString());
		}
		System.out.println("done");

	}
}