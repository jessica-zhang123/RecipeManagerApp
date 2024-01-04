
/**
 * creates the panel housing prep time panels used by prep and cook time
 * also includes constructor for yield bar
 */
import java.awt.*;
import javax.swing.*;

public class TimePanel extends JPanel {
	private JLabel lab;
	private JLabel hours;
	private JLabel mins;
	private TxtField fHours;
	private TxtField fMins;

	// label specifies cook or prep time
	public TimePanel(String label) {

		setBackground(Color.white);
		setLayout(new FlowLayout());
		lab = new JLabel(label);
		lab.setFont(new Font("Arial", Font.BOLD, 20));
		lab.setForeground(Color.decode("#9B9B9B"));
		add(lab);

		fHours = new TxtField(60);
		this.fHours.getTF().setPreferredSize(new Dimension(60, 40));
		add(fHours.getTF());

		hours = new JLabel("hours");
		hours.setFont(new Font("Arial", Font.BOLD, 20));
		hours.setForeground(Color.decode("#9B9B9B"));

		add(hours);

		fMins = new TxtField(60);
		this.fMins.getTF().setPreferredSize(new Dimension(60, 40));
		add(fMins.getTF());

		mins = new JLabel("mins");
		mins.setFont(new Font("Arial", Font.BOLD, 20));
		mins.setForeground(Color.decode("#9B9B9B"));

		add(mins);
	}

	// constructor for yield bar
	public TimePanel(String label, String yield) {

		setBackground(Color.white);
		setLayout(new FlowLayout());
		lab = new JLabel(label);
		lab.setFont(new Font("Arial", Font.BOLD, 20));
		lab.setForeground(Color.decode("#9B9B9B"));
		add(lab);

		fHours = new TxtField(60);
		this.fHours.getTF().setPreferredSize(new Dimension(60, 40));
		add(fHours.getTF());

	}

	// setter methods for time and yield
	public void setTime(String hours, String mins) {
		fHours.getTF().setText(hours);
		fMins.getTF().setText(mins);
	}

	public void setYield(String yield) {
		fHours.getTF().setText(yield);
	}

	// getter methods for times and yields
	public String getHours() {
		if (fHours.getTF().getText().equals("")) {
			return "empty";
		}
		return fHours.getTF().getText();
	}

	public String getMins() {
		if (fMins.getTF().getText().equals("")) {
			return "empty";
		}
		return fMins.getTF().getText();
	}

	public String getYield() {
		if (fHours.getTF().getText().equals("")) {
			return "empty";
		}
		return fHours.getTF().getText();
	}
}
