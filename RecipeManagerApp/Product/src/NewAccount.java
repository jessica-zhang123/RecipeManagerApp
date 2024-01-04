
/**
 * Class for making new account in the database
 * Stores username, passcode, userpasskey (concatentated string of username and passcode to create consistently unique reference to a user)
 * 
 */
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NewAccount {

	public NewAccount(String owner, String passcode) {
		try {
			// Connect to database
			String url = "jdbc:sqlite:db.db";
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection(url);

			Statement stmt = con.createStatement();
			String s = "INSERT INTO USERS VALUES ('" + passcode + "','" + owner + "','" + passcode + owner + "')";
			stmt.execute(s);
			s = "CREATE TABLE IF NOT EXISTS '" + passcode + owner + "' (\n"

					+ "	MealSection text DEFAULT NULL\n" + ");";

			stmt.execute(s);
			JOptionPane.showMessageDialog(null, "Success! Account created.");
			con.close();
		}
		// error message for showing if username already exists in database
		catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "Username already exists");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
