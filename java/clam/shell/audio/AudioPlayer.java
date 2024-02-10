package clam.shell.audio;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class AudioPlayer {
	public static void main(String[] args) {
		try {		
			String [] filenames =  {};
			Process p = new ProcessBuilder("audio.exe").start();
			
			PrintStream ps = new PrintStream (p.getOutputStream());
			
			ps.println("sleepwalk.mp3");
			ps.flush();		
			p.waitFor();
			
			ps.println("music.mp3");
			ps.flush();		
			p.waitFor();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
