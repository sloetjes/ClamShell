package clam.shell;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.VolatileImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class ImageTools {
	public static final int HORIZONTAL_FLIP = 0;
	public static final int VERTICAL_FLIP = 0;
	
	public static final String BMP = null;

	public static BufferedImage buffer(Image image) {
		BufferedImage result = new BufferedImage(image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		result.getGraphics().drawImage(image, 0, 0, null);
		return result;
	}

	public static VolatileImage makeVolatile(BufferedImage bufferedImage) {
		GraphicsConfiguration graphicsConfig = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice().getDefaultConfiguration();
		VolatileImage volatileImage = graphicsConfig.createCompatibleVolatileImage(bufferedImage.getWidth(),
				bufferedImage.getHeight(), Transparency.TRANSLUCENT);
		int valid = volatileImage.validate(graphicsConfig);
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsConfiguration graphicsConfiguration = graphicsEnvironment.getDefaultScreenDevice()
				.getDefaultConfiguration();
		BufferedImage convertedImage = graphicsConfiguration.createCompatibleImage(bufferedImage.getWidth(),
				bufferedImage.getHeight(), bufferedImage.getTransparency());
		convertedImage.getGraphics().drawImage(bufferedImage, 0, 0, null);
		volatileImage.getGraphics().drawImage(convertedImage, 0, 0, null);
		return volatileImage;
	}

	public static BufferedImage scale(BufferedImage portalImage, int width, int height) {
		return ImageTools.buffer(portalImage.getScaledInstance(width, height, BufferedImage.SCALE_AREA_AVERAGING));
	}
	public static BufferedImage load(String filename) {
		try {
			InputStream fis = new FileInputStream (filename);
			BufferedImage result = ImageIO.read(fis);
			fis.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		return null;
	}
	public static BufferedImage load(Cache cache, String filename) {		
		try {
			if (cache==null) {
				return ImageIO.read(new FileInputStream (filename));
			} else {								
				return ImageIO.read(cache.open (filename));	
			}			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		return null;
	}

	public static BufferedImage convertToGrayscale(BufferedImage image) {
		BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				int rgb = image.getRGB(x, y);
				Color c = new Color(rgb);
				int gray = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
				c = new Color(gray, gray, gray);
				result.setRGB(x, y, c.getRGB());
			}
		}
		return result;
	}

	public static BufferedImage blend(BufferedImage original, BufferedImage addon, float alpha) {
		BufferedImage result = new BufferedImage(original.getWidth(), original.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) result.createGraphics();
		g.drawImage(original, 0, 0, null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)); // Set the alpha transparency
		g.drawImage(addon, 0, 0, null);
		return result;
	}

	public static BufferedImage flipHorizontal (BufferedImage original) {
		BufferedImage result = new BufferedImage(original.getWidth(), original.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < result.getWidth(); x++) {
			for (int y = 0; y < result.getHeight(); y++) {
				result.setRGB(x, y, original.getRGB(result.getWidth() - 1 - x, y));
			}
		}
		return result;
	}
	public static BufferedImage flipVertical (BufferedImage original) {
		BufferedImage result = new BufferedImage(original.getWidth(), original.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < result.getWidth(); x++) {
			for (int y = 0; y < result.getHeight(); y++) {
				result.setRGB(x, y, original.getRGB(x, result.getHeight() - 1 - y));
			}
		}
		return result;
	}

	public static BufferedImage centerAndSquare(BufferedImage original) {
		BufferedImage result = null;
		if (original.getWidth() > original.getHeight()) {
			result = new BufferedImage(original.getWidth(), original.getWidth(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) result.getGraphics();
			g.drawImage(original, 0, (original.getWidth() - original.getHeight()) / 2, null);
		} else if (original.getHeight() > original.getWidth()) {
			result = new BufferedImage(original.getHeight(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) result.getGraphics();
			g.drawImage(original, 0, (original.getHeight() - original.getWidth()) / 2, null);
		} else if (original.getHeight() == original.getWidth()) {
			return original;
		} else {
			System.exit(0);
		}
		return result;
	}

	public static BufferedImage subImage(BufferedImage original, int width, int height) {
		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		result.getGraphics().drawImage(original, 0, 0, null);
		return result;
	}

	public static BufferedImage blackToAlpha(BufferedImage original) {
		BufferedImage result = new BufferedImage (original.getWidth(),original.getHeight(),BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < result.getWidth(); x++) {
			for (int y = 0; y < result.getHeight(); y++) {
				Color c = new Color (original.getRGB(x, y));
				int alpha = (c.getRed()+c.getGreen()+c.getBlue())/3;
				if (alpha<128) {
					alpha=0;
				}
				result.setRGB(x, y, new Color (c.getRed(),c.getGreen(),c.getBlue(),alpha).getRGB());
			}
		}
		return result;
	}

	public static void save(BufferedImage image, String format, String filename) {
		try {
			save (image, format, new FileOutputStream(new File(filename)));			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}		
	}

	public static void save(BufferedImage image, String format, OutputStream os) {
		try {
			ImageIO.write (image,format,os);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}			
	}
}
