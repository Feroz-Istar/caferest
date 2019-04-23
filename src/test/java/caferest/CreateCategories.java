package caferest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.javafaker.Faker;

import utils.CafeeDBUtils;

public class CreateCategories {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Faker faker = new Faker();
		//for(int i=0;i<100000;i++)
			
		//CafeeDBUtils.getInstance().insertIntoDB();
		
		Connection dbConnection = null;
		dbConnection = getDBConnection();
		try {
			Statement statement = dbConnection.createStatement();
			
			for(int i=0;i<100000;i++) {
				String sql= "INSERT INTO public.categories (created_at, name) VALUES(now(), '"+faker.company().profession()+"')";
				statement.addBatch(sql);

			}
			statement.executeBatch();
			statement.close();
			dbConnection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static Connection getDBConnection() {

		Connection dbConnection = null;

		try {

			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());

		}

		try {

			dbConnection = DriverManager.getConnection(
                            "jdbc:postgresql://localhost:5432/feroz", "postgres","root");
			return dbConnection;

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		return dbConnection;

	}
}
