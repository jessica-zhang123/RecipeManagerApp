/**
 * Creates the main kitchen which houses all meal sections buttons
 * Has button to create new meal section
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MealSectionsUnderAccount extends JFrame implements ActionListener {

	private JPanel c;
	public static JFrame frame;
	private JScrollPane jsp;
	private JLabel sectName;
	private JButton sort;
	private JButton makeMS;
	private MSDataRetriever ms;
	private String sectText;
	public static boolean sortSelected;

	public MealSectionsUnderAccount() {

		frame = new JFrame();
		frame.setBounds(300, 90, 900, 650);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Meal Sections");

		ms = new MSDataRetriever();

		c = new JPanel();
		c.setBackground(Color.white);

		jsp = new JScrollPane(c);
		c.setPreferredSize(new Dimension(0, 300 + (ms.yDim)));
		c.setLayout(new FlowLayout(FlowLayout.CENTER));
		c.setBackground(Color.white);

		sort = new JButton("Sort alphabetically");
		sort.setFont(new Font("Arial", Font.PLAIN, 30));
		sort.setSize(230, 60);
		sort.setBackground(Color.WHITE);
	    sort.setForeground(Color.decode("#55A630"));
		sort.setFocusable(false);

		c.add(sort);

		sectText = CreateLoginForm.userName + "'s Pantry";
		sectName = new JLabel(sectText);
		sectName.setFont(new Font("Arial", Font.BOLD, 80));
		sectName.setForeground(Color.white);
		sectName.setBackground(Color.decode("#BAF2BB"));
		sectName.setPreferredSize(new Dimension(sectText.length() * 40, 130));
		sectName.setOpaque(true);

		c.add(sectName);

		c.add(ms);

		makeMS = new JButton("Add a meal section");
		makeMS.setFont(new Font("Arial", Font.PLAIN, 30));
		makeMS.setSize(230, 60);
		makeMS.setBackground(Color.WHITE);
		makeMS.setForeground(Color.decode("#55A630"));
		c.add(makeMS);

		makeMS.addActionListener(this);
		sort.addActionListener(this);

		frame.getContentPane().add(jsp);

		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// to create a new meal section, dispose of current frame and load the meal
		// section template
		if (e.getSource() == makeMS) {
			MSTemplate newMS = new MSTemplate();
			frame.dispose();
		}
		
		if (e.getSource() == sort) {
			sortSelected = true;
			frame.dispose();
			CreateLoginForm.ms= new MealSectionsUnderAccount();
			
		}

	}

}
