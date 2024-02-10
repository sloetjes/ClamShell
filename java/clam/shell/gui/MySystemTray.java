package clam.shell.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class MySystemTray {

    public MySystemTray (BufferedImage image) {
    	try {
    		if (!SystemTray.isSupported()) {
                System.out.println("SystemTray is not supported");
                return;
            }
            SystemTray systemTray = SystemTray.getSystemTray();
            PopupMenu popupMenu = new PopupMenu();
            MenuItem exitItem = new MenuItem("Exit");
            exitItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            popupMenu.add(exitItem);
            TrayIcon trayIcon = new TrayIcon(image, "Launchpad", popupMenu);
            trayIcon.setImageAutoSize(true);
            systemTray.add(trayIcon);
            trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        // Double-clicked on the tray icon
                        System.out.println("Tray icon double-clicked!");
                    }
                }
            });
            trayIcon.displayMessage("System Tray Example", "Tray icon is now running", TrayIcon.MessageType.INFO);	
    	} catch (Exception e) {
    		e.printStackTrace();
    		System.exit(0);
    	}
    }
}
