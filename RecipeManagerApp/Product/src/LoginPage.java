
/**
 * Class that creates the login form and begins the program
 * Includes general login page and create new account form 
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

class CreateLoginForm extends JFrame implements ActionListener {
	private Container c;
	private JButton cont;
	private JButton newAcc;
	private JButton create;
	private JLabel title, desc, noAcc, userLab, passLab, confirmPassLab;
	private final JTextField txt1;
	private final JTextField txt2;
	private JTextField txt3;
	public static String currUser;
	public static String userName;

	public static MealSectionsUnderAccount ms;

	public CreateLoginForm(String s) {
		setTitle("Login");
		setBounds(300, 90, 900, 650);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		c = getContentPane();
		c.setLayout(null);
		c.setBackground(Color.white);

		title = new JLabel("The Pantry");
		title.setFont(new Font("Arial", Font.BOLD, 60));
		title.setForeground(Color.decode("#1E1E1E"));
		title.setSize(400, 100);
		title.setLocation(295, 40);
		c.add(title);

		desc = new JLabel("Cook, eat, enjoy.");
		desc.setFont(new Font("Arial", Font.PLAIN, 27));
		desc.setForeground(Color.decode("#55A630"));
		desc.setSize(400, 100);
		desc.setLocation(360, 110);
		c.add(desc);

		userLab = new JLabel("Username");
		userLab.setFont(new Font("Arial", Font.PLAIN, 18));
		userLab.setSize(100, 15);
		userLab.setLocation(300, 225);
		c.add(userLab);

		txt1 = new JTextField();
		txt1.setFont(new Font("Arial", Font.PLAIN, 30));
		txt1.setSize(300, 50);
		txt1.setLocation(300, 250);

		c.add(txt1);

		passLab = new JLabel("Passcode");
		passLab.setFont(new Font("Arial", Font.PLAIN, 18));
		passLab.setSize(150, 20);
		passLab.setLocation(300, 325);
		c.add(passLab);

		txt2 = new JPasswordField();
		txt2.setFont(new Font("Arial", Font.PLAIN, 30));
		txt2.setSize(300, 50);
		txt2.setLocation(300, 350);
		c.add(txt2);

		setVisible(true);

		// Login option display
		if (s.equals("Login")) {

			cont = new JButton("Continue");
			cont.setFont(new Font("Arial", Font.PLAIN, 18));
			cont.setSize(130, 40);
			cont.setForeground(Color.white);
			cont.setBackground(Color.decode("#55A630"));
			cont.setLocation(380, 420);
			c.add(cont);

			noAcc = new JLabel("Don't have an account?");
			noAcc.setFont(new Font("Arial", Font.PLAIN, 18));
			noAcc.setSize(300, 100);
			noAcc.setLocation(350, 440);
			c.add(noAcc);

			newAcc = new JButton("Create a new Account");
			newAcc.setFont(new Font("Arial", Font.PLAIN, 15));
			newAcc.setSize(190, 40);
			newAcc.setLocation(350, 515);
			newAcc.setBackground(Color.WHITE);
			newAcc.setForeground(Color.decode("#55A630"));
			c.add(newAcc);

			cont.addActionListener(this);
			newAcc.addActionListener(this);

		}

		// creating new acc display
		else if (s.equals("New acc")) {

			setTitle("Create an account");

			title.setText("Welcome to the Pantry");
			title.setFont(new Font("Arial", Font.BOLD, 50));
			title.setSize(600, 150);
			title.setLocation(190, 30);

			desc.setText("Let's Get Cooking!");
			desc.setFont(new Font("Arial", Font.PLAIN, 27));
			desc.setSize(450, 100);
			desc.setLocation(340, 120);
			c.add(desc);

			txt3 = new JPasswordField();
			txt3.setFont(new Font("Arial", Font.PLAIN, 30));
			txt3.setSize(300, 50);
			txt3.setLocation(300, 445);
			c.add(txt3);

			confirmPassLab = new JLabel("Confirm Passcode");
			confirmPassLab.setFont(new Font("Arial", Font.PLAIN, 18));
			confirmPassLab.setSize(150, 20);
			confirmPassLab.setLocation(300, 420);
			c.add(confirmPassLab);

			create = new JButton("Create");
			create.setFont(new Font("Arial", Font.PLAIN, 15));
			create.setSize(100, 40);
			create.setLocation(405, 515);
			create.setForeground(Color.white);
			create.setBackground(Color.decode("#55A630"));

			c.add(create);

			create.addActionListener(this);
		}

	}

	public void actionPerformed(ActionEvent e) {

		// if pressed continue on login page
		if (e.getSource() == cont) {
			String username = txt1.getText();
			String passcode = txt2.getText();
			currUser = passcode + username;

			// if both passcode and username fields are empty
			if (username.equals("") && passcode.equals("")) {
				JOptionPane.showMessageDialog(this, "Enter Username and Passcode");
			}

			// only username empty
			else if (username.equals("")) {
				JOptionPane.showMessageDialog(this, "Enter Username");
			}

			// only passcode empty
			else if (passcode.equals("")) {
				JOptionPane.showMessageDialog(this, "Enter Passcode");
			}

			// check if passcode and username exist in the db
			else {
				try {
					String url = "jdbc:sqlite:db.db";
					Class.forName("org.sqlite.JDBC");
					Connection con = DriverManager.getConnection(url);

					// s is the userpasskey which is a concatenation of the passcode and username
					String s = passcode + username;
					PreparedStatement st = con.prepareStatement("SELECT * from users WHERE userpasskey = ?");

					st.setString(1, s);
					ResultSet rs = st.executeQuery();

					// if found, go to the main kitchen
					if (rs.next()) {
						userName = username;
						ms = new MealSectionsUnderAccount();
						dispose();
					}

					// userpasskey not found in db
					else {
						JOptionPane.showMessageDialog(this, "Either username or password is incorrect");
					}

					con.close();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(this, "Something went wrong");

				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		}

		// when creating a new acc
		if (e.getSource() == newAcc) {
			CreateLoginForm createAccForm = new CreateLoginForm("New acc");
		} else if (e.getSource() == create) {
			String username = txt1.getText();
			String passcode = txt2.getText();
			String passcodeCheck = txt3.getText();
			Boolean validName = false;

			// if atleast 1 character in the username is non-numeric, allow the username
			for (int i = 0; i < username.length(); i++) {
				if (Character.isLetter(username.charAt(i))) {
					validName = true;
					break;
				}
			}
			
			// empty username
			if (username.equals("")) {
				JOptionPane.showMessageDialog(this, "Enter Username");
			}

			// if there are only numbers in username
			else if (!validName) {
				JOptionPane.showMessageDialog(this, "Username must include a non-numeric character");
			}

			// empty passcode
			else if (passcode.equals("") || passcodeCheck.equals("")) {
				JOptionPane.showMessageDialog(this, "Enter Passcode");
			}

			// mismatched passcodes
			else if (!passcode.equals(passcodeCheck)) {
				JOptionPane.showMessageDialog(this, "Not the same passcode");
			}

			// valid passcode and username
			// open up the new account which sends passcode and username to the database
			else {
				NewAccount newKitchen = new NewAccount(txt1.getText(), txt2.getText());
				dispose();
			}
		}

	}
}

class LoginPage {
	public static void main(String[] args) {
		try {
			CreateLoginForm login = new CreateLoginForm("Login");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}
