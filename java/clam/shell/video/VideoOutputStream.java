package clam.shell.video;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class VideoOutputStream {
	public Video video = null;
	DataOutputStream dos = null;
	public VideoOutputStream (OutputStream os, Video profile) throws Exception {
		this.video=profile;
		dos = new DataOutputStream (os);
		dos.writeInt(video.width);
		dos.writeInt(video.height);
		dos.writeInt(video.framecount);
		dos.writeInt(video.FPS);
	}	
	public void addImage (BufferedImage image) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream ();
		ImageIO.write(image, "JPEG", baos);
		byte [] data = baos.toByteArray();		
		dos.writeInt(data.length);		
		dos.write(data);
	}
	public void close () throws Exception {
		dos.close();
	}
}

 