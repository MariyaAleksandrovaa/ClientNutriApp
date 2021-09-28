package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static Connection con = null;

	public static void main(String[] args) {
		
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/nutri_db", "root", "mysql");
			SpringApplication.run(Application.class, args);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
