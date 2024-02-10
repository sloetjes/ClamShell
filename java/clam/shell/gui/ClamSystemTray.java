package clam.shell.gui;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ClamSystemTray {
	public SystemTray tray = SystemTray.getSystemTray();
	public ClamSystemTray(String title, String filename) {				
		PopupMenu popupMenu = new PopupMenu();
		MenuItem exitItem = new MenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		popupMenu.add(exitItem);
		BufferedImage image;
		try {
			image = ImageIO.read(new File(filename));
			TrayIcon trayIcon = new TrayIcon(image, title, popupMenu);
			trayIcon.setImageAutoSize(true);
			tray.add(trayIcon);			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
