package clam.shell.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Menu;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class Screen extends JPanel {
	private static final long serialVersionUID = 1L;
	protected JPanel contentPanel = new JPanel ();
	
	public Screen () {
		this ("(no title set)");
	}
	public JLabel titleLabel = null;
	public Screen (String title) {			
		titleLabel = new JLabel (title);
		titleLabel.setFont (new Font ("Arial",Font.BOLD,24));
		setLayout (new BorderLayout());
		//titleLabel.setSize(new Dimension (256,256));
		add (titleLabel, BorderLayout.NORTH);
		add (contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout());
		validate();
		setBorder (null);
		//setBackground (new Color ((int)(128+Math.random()*128),(int)(128+Math.random()*128),(int) (128+Math.random()*128)));
	}
	public String getTitle() {		
		return "No title";
	}
	public ArrayList<Menu> menus = new ArrayList<Menu> ();
	public ArrayList<Menu> getMenus() {
		return menus;
	}
	
	Screen child = null;
	public void add (Screen screen) {
		setBorder (null);
		if (child==null) {			
			add (screen, BorderLayout.SOUTH);
			validate();
			this.child=screen;	
		} else {
			child.add (screen);
		}	
	}
}
