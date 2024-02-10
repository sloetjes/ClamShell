package clam.shell.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


public class ImageCanvas extends JPanel {
	private static final long serialVersionUID = 1L;
	BufferedImage image = null;

	public double resolutionScale = 1.0d;
	BufferedImage background = null;
//	Image cache = null;
	//public boolean useCache = true;
	public ImageCanvas () {
		
	}
	public ImageCanvas (BufferedImage image) {
		this ();
		setImage (image);
	}
	public void setImage(Image image) {
		BufferedImage temp = new BufferedImage (800,600, BufferedImage.TYPE_INT_RGB);
		setImage (temp);
	}
	public void setImage(BufferedImage image) {
		this.image=image;
		repaint();
	}
	public boolean drawGrid = true;
	public boolean scaled = false;
	public boolean centered = false;
	int scale = 16;

	/*
	 * add caching
	 */
	public void paint(Graphics g) {
		if (!isDisplayable())
			return;
		Graphics2D graphics = (Graphics2D) g;
		if (drawGrid) {
			for (int i = 0; i < getWidth(); i += scale)
				for (int j = 0; j < getHeight(); j += scale) {
					Color color = Color.GRAY;
					if ((i / scale + j / scale) % 2 == 0) {
						color = Color.DARK_GRAY;
					}
					graphics.setColor(color);
					graphics.fillRect(i, j, scale, scale);
				}
		} else {
			graphics.setColor(getBackground());
			graphics.fillRect(0,0,getWidth(),getHeight());
		}
		if (image == null) {
		} else {
			if (scaled) {
				graphics.drawImage(scaleFit(image), 0, 0, null);
			} else {
				graphics.drawImage(image, 0, 0, null);
			}
			
		}			
	}

	public BufferedImage scaleFit(BufferedImage image) {
		return scaleFit(image, getWidth(), getHeight());
	}

	public BufferedImage scaleFit(BufferedImage image, int width, int height) {
		double wd = ((double) image.getWidth()) / width;
		double hd = ((double) image.getHeight()) / height;
		int scaledWidth = -1;
		int scaledHeight = -1;
		if (wd == hd) {
			scaledWidth = width;
			scaledHeight = height;
		} else if (wd > hd) {
			scaledWidth = width;
			scaledHeight = image.getHeight() * width / image.getWidth();
		} else {
			scaledHeight = height;
			scaledWidth = image.getWidth() * height / image.getHeight();
		}
		BufferedImage result = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
		result.getGraphics().drawImage(image.getScaledInstance(scaledWidth, scaledHeight, BufferedImage.SCALE_REPLICATE),
				0, 0, null);
		return result;
	}

	public BufferedImage center(BufferedImage image) {
		return center(image, getWidth(), getHeight());
	}

	public BufferedImage center(BufferedImage image, int width, int height) {
		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int x = (width - image.getWidth()) / 2;
		int y = (height - image.getHeight()) / 2;
		result.getGraphics().drawImage(image, x, y, null);
		return result;
	}

	public BufferedImage scaleFitAndCenter(BufferedImage image) {
		return scaleFitAndCenter(image, getWidth(), getHeight());
	}

	/*
	 * For VideoCanvas, hardware acceleration should be used for scaling
	 */
	private BufferedImage scaleFitAndCenter(BufferedImage image2, int width, int height) {
		double wd = ((double) image.getWidth()) / width;
		double hd = ((double) image.getHeight()) / height;
		int scaledWidth = -1;
		int scaledHeight = -1;
		if (wd == hd) {
			scaledWidth = width;
			scaledHeight = height;
		} else if (wd > hd) {
			scaledWidth = width;
			scaledHeight = image.getHeight() * width / image.getWidth();
		} else {
			scaledHeight = height;
			scaledWidth = image.getWidth() * height / image.getHeight();
		}
		BufferedImage temp = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
		temp.getGraphics().drawImage(image.getScaledInstance(scaledWidth, scaledHeight, BufferedImage.SCALE_FAST), 0, 0,
				null);
		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int x = (width - temp.getWidth()) / 2;
		int y = (height - temp.getHeight()) / 2;
		result.getGraphics().drawImage(temp, x, y, null);
		return result;
	}
}