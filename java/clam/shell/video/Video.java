package clam.shell.video;

import java.awt.image.BufferedImage;

public class Video {
	public int width;
	public int height;
	public int framecount;
	public int FPS;
	public int byteSize;
	public Video(int width, int height, int framecount, int FPS) {
		this.width = width;
		this.height = height;
		this.framecount = framecount;
		this.FPS = FPS;
		this.byteSize=0;
	}
	
	public Video() {		
	}
}
