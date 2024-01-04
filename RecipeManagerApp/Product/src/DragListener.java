
/*
 * DragListener which connects to Dragger panel
 * implements DropTargetListener interface
 */
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class DragListener implements DropTargetListener {
	JLabel imageLabel = new JLabel();
	JLabel pathLabel = new JLabel();
	// stores absolute path
	public static String newImagePath="";

	private int width;
	private int height;

	// DragListener sized to recipe
	public DragListener(JLabel image, JLabel path) {
		newImagePath = "";
		width = 230;
		height = 230;
		imageLabel = image;
		pathLabel = path;

	}

	// DragListener sized to meal section
	public DragListener(JLabel image, JLabel path, int width, int height) {
		this.width = width;
		this.height = height;
		imageLabel = image;
		pathLabel = path;
	}
	/**
	 * method for displaying the image on the field 
	 * @param path path of inputted image
	 */
	public void showImg(String path) {
		displayImage(pathLabel.getText());
	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragExit(DropTargetEvent dte) {
		// TODO Auto-generated method stub

	}

	@Override
	
	/**
	 * Copies dropped images to internal folder
	 */
	public void drop(DropTargetDropEvent ev) {
		ev.acceptDrop(DnDConstants.ACTION_COPY);
		Transferable t = ev.getTransferable();

		DataFlavor[] df = t.getTransferDataFlavors();

		for (DataFlavor f : df) {
			try {
				if (f.isFlavorJavaFileListType()) {

					@SuppressWarnings("unchecked")
					List<File> files = (List<File>) t.getTransferData(f);

					for (File file : files) {

						// copying file to local images folder using file input and output streams

						FileInputStream in = new FileInputStream(file.getAbsolutePath());
						Path path = Paths.get(file.getPath());
						Path fileName = path.getFileName();
						String s = fileName.toString();

						// change path name
						FileOutputStream ou = new FileOutputStream("images\\" + s);

						BufferedInputStream bin = new BufferedInputStream(in);

						BufferedOutputStream bou = new BufferedOutputStream(ou);
						int b = 0;
						while (b != -1) {
							b = bin.read();
							bou.write(b);
						}
						bin.close();
						bou.close();

						// update string name
						newImagePath = "images\\" + s;

						displayImage(newImagePath);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();

			}
		}
	}

	/**
	 * method for displaying the image in the field
	 * reads images, scales them, and resets path strings
	 * @param path is the path of the string to display
	 */
	public void displayImage(String path) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path));
			ImageIcon icon = new ImageIcon(img);
			Image image = icon.getImage();

			// size of recipe image field (230,230)
			Image newImage = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);

			icon = new ImageIcon(newImage);

			imageLabel.setIcon(icon);

			newImagePath = path;
			pathLabel.setText(path);
		} catch (Exception e) {

		}

	}

}
