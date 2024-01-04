
/**
 * Panel for displaying the thumbnail of a recipe on the meal section panel
 */
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;
import javax.swing.*;

public class RecipeThumbnail extends JPanel {

	private JLabel nameLabel;
	private JLabel imgLabel;
	private JLabel imgLabelBackground;
	private JLabel timeLabel;
	private JLabel levelLabel;
	private JLabel yieldLabel;
	private BufferedImage bImg;
	private ImageIcon icon;
	private String name;
	private Image scaledImg;
	private Image newImg;
	private BufferedImage bImgBackground;
	private ImageIcon iconBackground;
	private Image scaledBackgroundImg;
	private Image newBackgroundImg;
	private ClearBtn btn;
	private String rawName;

	// name is actual recipe name with spaces
	public RecipeThumbnail(String name, String imgPath, String time, String level, String yield, String rawName) {

		// rawName is the name of the recipe column in the database
		this.rawName = rawName;
		
		this.name = name;
		setLayout(null);
		setPreferredSize(new Dimension(250, 190));

		setBackground(Color.WHITE);

		// add name
		nameLabel = new JLabel(name);
		nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
		nameLabel.setSize(220, 30);
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

		nameLabel.setForeground(Color.white);
		add(nameLabel);

		// add total time
		timeLabel = new JLabel("Total Time: " + time);
		timeLabel.setFont(new Font("Arial", Font.BOLD, 15));
		timeLabel.setSize(220, 30);
		timeLabel.setLocation(10, 90);
		timeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		timeLabel.setForeground(Color.white);
		add(timeLabel);

		// add level of difficulty
		levelLabel = new JLabel("Level: " + level);
		levelLabel.setFont(new Font("Arial", Font.BOLD, 15));
		levelLabel.setSize(220, 30);
		levelLabel.setLocation(10, 105);
		levelLabel.setHorizontalAlignment(SwingConstants.LEFT);
		levelLabel.setForeground(Color.white);
		add(levelLabel);

		// add yield
		yieldLabel = new JLabel("Yield: " + yield);
		yieldLabel.setFont(new Font("Arial", Font.BOLD, 15));
		yieldLabel.setSize(220, 30);
		yieldLabel.setLocation(10, 120);
		yieldLabel.setHorizontalAlignment(SwingConstants.LEFT);
		yieldLabel.setForeground(Color.white);
		add(yieldLabel);

		try {
			// add image
			bImg = ImageIO.read(new File(imgPath.substring(8).replace(",empty", "")));
			icon = new ImageIcon(bImg);
			scaledImg = icon.getImage();

			newImg = scaledImg.getScaledInstance(180, 49, java.awt.Image.SCALE_SMOOTH);
			icon = new ImageIcon(newImg);

			imgLabel = new JLabel();
			imgLabel.setIcon(icon);
			imgLabel.setSize(180, 49);
			imgLabel.setLocation(19, 30);

			add(imgLabel);
		} catch (IOException e) {
			// if there is no such image, then catch the error by simply displaying nothing
		}

		try {
			bImgBackground = ImageIO.read(new File("systemImages/recipeThumbnail.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		iconBackground = new ImageIcon(bImgBackground);
		scaledBackgroundImg = iconBackground.getImage();

		newBackgroundImg = scaledBackgroundImg.getScaledInstance(220, 150, java.awt.Image.SCALE_SMOOTH);
		iconBackground = new ImageIcon(newBackgroundImg);

		imgLabelBackground = new JLabel();
		imgLabelBackground.setIcon(iconBackground);
		imgLabelBackground.setSize(220, 150);
		imgLabelBackground.setLocation(0, 0);

		add(imgLabelBackground);

		btn = new ClearBtn(name, rawName, 2);
		btn.setSize(220, 150);
		btn.setLocation(0, 0);
		add(btn);

	}
	
	public String getName() {
		return name;
	}
}
