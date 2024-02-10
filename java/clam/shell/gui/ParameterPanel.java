package clam.shell.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import clam.shell.Database;

public class ParameterPanel extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public static void main(String[] args) {
		JFrame frame = new JFrame("test");
		JPanel panel = new JPanel();
		ParameterPanel screen = new ParameterPanel("ParameterScreen Testing", Database.load("ParameterScreenTest.db"));
		screen.add("Component 1", new JTextField("1", 16));
		screen.add("Component 2", new JCheckBox("Component 2"));
		screen.add("Component 3", new JSlider(0, 32));
		screen.add("Component 4", new TimePanel ());
		panel.add(screen);
		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	JPanel left = new JPanel();
	JPanel right = new JPanel();
	Database db = null;
	int count = 0;
	boolean showDetails = false;
	public void paint (Graphics g) {
		super.paint(g);		
	}
	public ParameterPanel(String title, Database db) {
		this.db = db;
		setLayout(new BorderLayout());
		
		JLabel titleLabel = new JLabel(title);
		
		JPanel temp = new JPanel();
		temp.setLayout(new FlowLayout(FlowLayout.LEFT));
		temp.add(titleLabel);
		titleLabel.setFont(new Font("Arial", Font.BOLD , 18));
		titleLabel.addMouseListener(
				new MouseAdapter () {
					public void mouseEntered(MouseEvent e) {
						titleLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));			
						titleLabel.setForeground(Color.BLUE);
					}
					public void mouseExited(MouseEvent e) {
						titleLabel.setForeground(Color.BLACK);
					}
					public void mouseClicked(MouseEvent e) {
						if (!showDetails) {
							expand ();
						} else {
							collapse();					
						}
						
						revalidate();
					}
				});
		
		add(temp, BorderLayout.NORTH);
		expand();
		
	}

	ArrayList<JComponent> components = new ArrayList();

	public void enableAll() {
		for (JComponent component : components) {
			component.setEnabled(true);
		}
	}

	public void disableAll() {
		for (JComponent component : components) {
			component.setEnabled(false);
		}
	}
	public void addParameterScreen(ParameterPanel screen) {
		add(screen, BorderLayout.SOUTH);
	}
	public void add(final String caption, final JComponent component) {
		count++;
		components.add(component);
		JPanel temp = new JPanel();
		temp.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JLabel captionLabel = new JLabel(caption);
		if (component instanceof JCheckBox) {
			temp.add(new JLabel(""));
		} else {
			temp.add(captionLabel);
		}
		left.add(temp);
		left.setLayout(new GridLayout(count, 1));

		temp = new JPanel();
		temp.setLayout(new FlowLayout(FlowLayout.LEFT));
		temp.setBorder(null);
		temp.add(component);
		if (component instanceof JSlider) {
			JSlider slider = (JSlider) component;
			JLabel valueIndicator = new JLabel ("    ");
			slider.addChangeListener(
					new ChangeListener () {
						public void stateChanged(ChangeEvent e) {
							valueIndicator.setText(""+slider.getValue());
						}				
			});
			//valueIndicator.setForeground(Color.WHITE);
			temp.add(valueIndicator);
		}
		
		right.add(temp);
		right.setLayout(new GridLayout(count, 1));
		
		final ParameterPanel me = this;
		
		if (component instanceof JTextField) {
			Object o = db.get(this.getClass().getName() + "." + caption+ ".value");
			if (o == null) {
			} else if ("null".equals(o)) {
			} else {
				JTextField tf = (JTextField) component;
				tf.setText("" + o);
			}
		} else if (component instanceof JCheckBox) {
			boolean v = db.getBoolean(me.getClass().getName() + "." + ((JCheckBox) component).getText()+ ".value");
			JCheckBox jcb = (JCheckBox) component;
			jcb.setSelected(v);
		} else if (component instanceof JSlider) {
			JSlider slider = (JSlider) component;
			slider.setValue(db.getInt(me.getClass().getName() + "." + caption + ".value"));		
			slider.setCursor(new Cursor(Cursor.HAND_CURSOR));	
			
		} else if (component instanceof TimePanel) {
			TimePanel tp = (TimePanel) component;
			tp.setTime(db.getInt(""+me.getClass().getName() + "." + caption + ".value"));	
			
		} else if (component instanceof JLabel) {
			
		}
		db.addShutdownHook(new Runnable() {
			public void run() {
				if (component instanceof JTextField) {
					db.set(me.getClass().getName() + "." + caption+ ".value", ((JTextField) component).getText());
				} else if (component instanceof JCheckBox) {
					db.setBoolean(me.getClass().getName() + "." + ((JCheckBox) component).getText()+ ".value",
							((JCheckBox) component).isSelected());
				} else if (component instanceof TimePanel) {
					TimePanel tp = (TimePanel) component;
					db.set(me.getClass().getName() + "." + caption + ".value", tp.getValue());
				} else if (component instanceof JSlider) {
					JSlider slider = (JSlider) component;
					db.set(me.getClass().getName() + "." + caption + ".value", slider.getValue());
				} else {
				}
			}
		});
	}
	public void addChildScreen(Screen screen) {
		add(screen,BorderLayout.SOUTH);
	}
	public void collapse() {
		remove(left);
		remove(right);
		showDetails=!showDetails;
	}
	public void expand() {
		add(left, BorderLayout.WEST);
		add(right, BorderLayout.CENTER);	
		showDetails=!showDetails;
	}
}
