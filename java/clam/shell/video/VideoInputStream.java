package clam.shell.video;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import clam.shell.Cache;

public class VideoInputStream {	
	InputStream is = null;		
	Video video = null;
	byte [] data = null;
	public VideoInputStream (Cache cache, String filename) throws Exception {
		
	}
	public VideoInputStream (File file) throws Exception {		
		data = new byte [(int) file.length()];
		FileInputStream fis = new FileInputStream (file);
		fis.read(data);
		fis.close();
		is = new ByteArrayInputStream (data);
		video = readVideoHeader();
	}		
	public void reset () throws Exception {
		this.is=new ByteArrayInputStream (data);
		video = readVideoHeader ();		
	}	
	public BufferedImage readVideoFrame () throws Exception {
		DataInputStream dis = new DataInputStream (is);		
		byte [] imageData = new byte [dis.readInt()];
		dis.read(imageData);		
		return ImageIO.read(new ByteArrayInputStream(imageData));		
	}
	public Video readVideoHeader () throws Exception {
		Video result = new Video ();
		DataInputStream dis = new DataInputStream (is);
		result.width=dis.readInt();
		result.height=dis.readInt();
		result.framecount=dis.readInt();
		result.FPS=dis.readInt();
		return result;
	}	
}
