/**
 * Class for creating image drag and drop field
 * Allows for custom sizes for Recipe and Meal Section form 
 * Connects to DragListener
 */
import java.awt.Color;
import java.awt.dnd.DropTarget;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Dragger extends JPanel {
	private JLabel imageLabel;
	private JLabel pathLabel;
	private int width;
	private int height;
	public String loadedImg = "";
	private DragListener d;

	public Dragger() {
		Border border = BorderFactory.createDashedBorder(Color.decode("#9B9B9B"), 2, 1, 3, true);
		setBorder(border);
		imageLabel = new JLabel();
		pathLabel = new JLabel();
		add(imageLabel);
		connectToDragDrop();
	}

	// allows for custom sizes for either Recipe or Meal Section
	public Dragger(int width, int height) {
		this.width = width;
		this.height = height;
		Border border = BorderFactory.createDashedBorder(Color.decode("#9B9B9B"), 2, 1, 3, true);
		setBorder(border);
		imageLabel = new JLabel();
		pathLabel = new JLabel();
		add(imageLabel);
		// establishes constant connection to DragListener class
		connectToDragDrop(width, height);
	}

	// method to show the image after it has been dropped
	public void loadImg(String path) {
		d.displayImage(path);
	}

	// methods for connecting to DragListener
	private void connectToDragDrop() {

		d = new DragListener(imageLabel, pathLabel);

		new DropTarget(this, d);
	}

	private void connectToDragDrop(int width, int height) {

		d = new DragListener(imageLabel, pathLabel, width, height);

		new DropTarget(this, d);
	}

	// returns the image path of the image copied to the local "images" file. NOT
	// THE ABSOLUTE PATH
	public String getImagePath() {
		return DragListener.newImagePath;
		

	}

	/**
	 * method for adding local image path to database
	 * @param nameMS is concatenated with the imgPath
	 * @param table specifies the table specific to the user
	 * @param col is the recipe column
	 */
	public void addImgPathToDB(String nameMS, String table, String col) {
		String imgPath = nameMS + "|" + "imgPath:" + getImagePath();
		

		// for no image
		if (getImagePath() == "") {
			imgPath = nameMS + "|" + "imgPath:" + "empty";

		}

		// change double backslash \\ to forward slash / for the purposes of retrieval
		// and display from database
		imgPath = imgPath.replaceAll((char) 92 + "" + (char) 92, (char) 47 + "");

		try {
			String url = "jdbc:sqlite:db.db";
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			Connection con = DriverManager.getConnection(url);
			Statement stmt = con.createStatement();
			
			String query = "INSERT INTO '" + table + "' ('" + col + "') VALUES ('" + imgPath + "')";

			stmt.execute(query);
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// reset strings to empty in case of another loaded image after
		DragListener.newImagePath = "";

	}

}
