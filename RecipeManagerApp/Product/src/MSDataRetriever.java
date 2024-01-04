
/**
 * Class which fetches all information about meal sections from db and places info in arraylist
 * Then, using the arraylist, the icons of each meal section are generated and organized onto a panel
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JPanel;

public class MSDataRetriever extends JPanel {
	public static ArrayList<MSThumbnail> msArr;
	public static int count = 0;
	public int yDim;

	public MSDataRetriever() {

		count = 0;
		yDim = 0;
		setBackground(Color.white);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		msArr = new ArrayList<>();

		try {
			ArrayList<String> strList = new ArrayList<>();
			String url = "jdbc:sqlite:db.db";
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			Connection con = DriverManager.getConnection(url);
			String query = "SELECT MealSection FROM '" + CreateLoginForm.currUser + "'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// collecting Meal Section info from db
			while (rs.next()) {
				if (rs.getString(1) != null) {
					String name = "";
					String path = "";
					int count = -1;
					if (rs.getString(1).contains("imgPath:")) {
						// keep track of how many meal sections are loaded so as to change sizing of
						// panel
						this.count++;
						for (int i = 0; i < rs.getString(1).length(); i++) {
							// get the name of the meal section which preceeds the |
							if (rs.getString(1).charAt(i) != '|') {
								name += rs.getString(1).charAt(i);

								// count length of just the name
								count++;
							}
							if (rs.getString(1).charAt(i) == '|') {
								break;
							}
						}
						strList.add(name);

						// retrieve image path
						for (int i = count + 2; i < rs.getString(1).length(); i++) {
							path += rs.getString(1).charAt(i);
						}

						// create its own meal section thumbnail (image, descriptors)
						MSThumbnail newMs = new MSThumbnail(name, path);

						// add to current panel
						msArr.add(newMs);
					}

				}

			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (count >= 2) {

			// if there is an even number of panels, set the height to be amount of rows *
			// 250 pixels
			if (count % 2 == 0) {
				yDim = (count / 2) * 250;
			}

			// odd number of meal sections, there will be another row to house the remaining
			// odd ones
			else {
				yDim = (count / 2 + 1) * 250;
			}
		}

		// if less than 2 meal sections, only create 1 row
		else {
			yDim = 250;
		}

		setPreferredSize(new Dimension(900, yDim));

		// sort alphabetically
		
		MSThumbnail[] arr = new MSThumbnail[msArr.size()];
		for (int i = 0; i<msArr.size(); i++) {
			arr[i] = msArr.get(i);
		}
		
		if (MealSectionsUnderAccount.sortSelected) {
			for (int i = 1; i<msArr.size(); i++) {
				MSThumbnail var = arr[i];
				int j = i-1;
				while (j>=0 && var.getName().compareToIgnoreCase(arr[j].getName())<0) {
					arr[j+1] = arr[j];
					j--;
					
				}
				arr[j+1] = var;
			
			}
			MealSectionsUnderAccount.sortSelected = false;
		}
		
		for (int i = 0; i<msArr.size(); i++) {
			add(arr[i]);
		}

	}
}
