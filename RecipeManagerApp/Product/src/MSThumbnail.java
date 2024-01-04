
/**
 * Creates panel which acts as thumbnail for each meal section
 * Uses the clear button overlay made in ClearBtn class
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MSThumbnail extends JPanel {

	private JLabel nameLabel;
	private JLabel imgLabel;
	private BufferedImage bImg;
	private ImageIcon icon;
	private Image scaledImg;
	private Image newImg;
	private ClearBtn btn;
	private JLabel imgLabelBackground;
	private BufferedImage bImgBackground;
	private ImageIcon iconBackground;
	private Image scaledBackgroundImg;
	private Image newBackgroundImg;
	private String name;

	// input name of meal section and image path
	public MSThumbnail(String name, String imgPath) {
		
		this.name = name;
		setLayout(null);
		setPreferredSize(new Dimension(400, 250));
		setBackground(Color.WHITE);

		nameLabel = new JLabel(name);
		nameLabel.setFont(new Font("Arial", Font.BOLD, 30));
		nameLabel.setSize(230, 40);
		nameLabel.setLocation(60, 150);
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setForeground(Color.white);
		add(nameLabel);

		try {

			// read image and resize
			bImg = ImageIO.read(new File(imgPath.substring(8)));
			icon = new ImageIcon(bImg);
			scaledImg = icon.getImage();

			newImg = scaledImg.getScaledInstance(290, 120, java.awt.Image.SCALE_SMOOTH);
			icon = new ImageIcon(newImg);

			imgLabel = new JLabel();
			imgLabel.setIcon(icon);
			imgLabel.setSize(290, 120);
			imgLabel.setLocation(27, 20);

			add(imgLabel);
		} catch (Exception e) {
			// if theres an error, nothing will be displayed as the thumb nail image
		}
		try {
			
			// using inputted image of green rectangle as the background of the thumbnail
			bImgBackground = ImageIO.read(new File("systemImages/msGreenRec.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		iconBackground = new ImageIcon(bImgBackground);
		scaledBackgroundImg = iconBackground.getImage();

		newBackgroundImg = scaledBackgroundImg.getScaledInstance(350, 200, java.awt.Image.SCALE_SMOOTH);
		iconBackground = new ImageIcon(newBackgroundImg);

		imgLabelBackground = new JLabel();
		imgLabelBackground.setIcon(iconBackground);
		imgLabelBackground.setSize(350, 200);
		imgLabelBackground.setLocation(0, 0);

		add(imgLabelBackground);

		// add clear button overlay - > keep size and location same as iconBackground
		btn = new ClearBtn(name, "no raw", 1);
		btn.setSize(350, 200);
		btn.setLocation(0, 0);
		add(btn);
	}
	public String getName() {
		return name;
	}
}
