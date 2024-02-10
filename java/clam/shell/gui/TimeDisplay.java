package clam.shell.gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TimeDisplay extends JPanel  {
	public long startTime = 0;
	public void startTiming () {
		startTime = System.currentTimeMillis();
	}
	public JTextField hourTF = new JTextField ("0",2);
	public JTextField minuteTF = new JTextField ("0",2);
	public JTextField secondTF = new JTextField ("0",2);
	
	public TimeDisplay (int timeInSeconds) {
		setTimeInSeconds (timeInSeconds);
	//	hourTF.setEnabled(false);
	//	minuteTF.setEnabled(false);
	//	secondTF.setEnabled(false);
		hourTF.setEditable(false);
		minuteTF.setEditable(false);
		secondTF.setEditable(false);
		
		add(hourTF);
		add(new JLabel (":"));
		add(minuteTF);
		add(new JLabel (":"));
		add(secondTF);		
	
	}
	public int getTimeInSeconds () {
		int hour = Integer.parseInt(hourTF.getText());
		int minute = Integer.parseInt(minuteTF.getText());
		int second = Integer.parseInt(secondTF.getText());
		
		return hour*60*60+minute*60+second;
	}
	public void setTimeInSeconds(int timeInSeconds) {
		int hour = timeInSeconds/60/60;
		int minute = timeInSeconds/60%60;
		int second = timeInSeconds%60;
		
		hourTF.setText(""+((hour<10?"0":"")+hour));
		minuteTF.setText(""+((minute<10?"0":"")+minute));
		secondTF.setText(""+((second<10?"0":"")+second));
		
		repaint();	
		
		//setLayout(new GridLayout (1,5));
		
	}
}
