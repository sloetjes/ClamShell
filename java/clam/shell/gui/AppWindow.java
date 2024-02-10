package clam.shell.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MenuBar;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JWindow;

import clam.shell.Database;

public class AppWindow extends JFrame implements WindowListener {

	public Database db = null;
	String title=null;
	public AppWindow(String title, Database db) {
		super(title);
		this.db=db;
		this.title=title;
		try {
			addWindowListener(this);
			if (this.getAppWindowMenu() != null) {
				this.setMenuBar(this.getAppWindowMenu());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (db!=null) {			
			int x = db.getInt(title+".window.x");
			int y = db.getInt(title+".window.y");
			int width = db.getInt(title+".window.width");
			int height = db.getInt(title+".window.height");
			this.setLocation(x,y);
			this.setSize(width,height);
		}
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	Screen screen = null;

	

	public MenuBar getAppWindowMenu() {
		return null;
	}

	public void close() {
		if (screen != null) {
			//screen.close();
		}
		if (db!=null) {
			
			db.setInt(title+".window.x", this.getX());
			db.setInt(title+".window.y", this.getY());
			db.setInt(title+".window.width", this.getWidth());
			db.setInt(title+".window.height", this.getHeight());
			
		}
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {

	}

	public void windowClosing(WindowEvent e) {		
		close();
	}

	public void windowDeactivated(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowOpened(WindowEvent e) {
	}
	public void center(Window d) {
		Dimension screenD = Toolkit.getDefaultToolkit().getScreenSize();		
		d.setLocation((screenD.width - d.getWidth()) / 2, (screenD.height - d.getHeight()) / 2);
	}
	public void center() {
		center(this);
	}
}

