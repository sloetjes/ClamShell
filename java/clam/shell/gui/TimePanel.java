package clam.shell.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
//import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class TimePanel extends JPanel {
	JTextField hourTF = new JTextField("0", 2);
	JTextField minuteTF = new JTextField("1", 2);
	JTextField secondTF = new JTextField("0", 2);

	JPanel panel = new JPanel();

	public void setEditable(boolean val) {
		hourTF.setEditable(val);
		minuteTF.setEditable(val);
		secondTF.setEditable(val);
	}

	public TimePanel() {
		panel.setLayout(new GridLayout(1, 5));
		panel.add(hourTF);
		panel.add(minuteTF);
		panel.add(secondTF);

		add(panel, BorderLayout.SOUTH);
	}

	public void setTime(int time) {
		int seconds = time % 60;
		time /= 60;
		int minutes = time % 60;
		time /= 60;
		int hours = time;
		setHours(hours);
		setMinutes(minutes);
		setSeconds(seconds);

	}

	public int getHours() {
		return Integer.parseInt(hourTF.getText().trim());
	}

	public int getMinutes() {
		return Integer.parseInt(minuteTF.getText().trim());
	}

	public int getSeconds() {
		return Integer.parseInt(secondTF.getText().trim());
	}

	public void setHours(int hours) {
		hourTF.setText("" + getDigit(hours));
	}

	public void setMinutes(int minutes) {
		minuteTF.setText("" + getDigit(minutes));
	}

	public void setSeconds(int seconds) {
		secondTF.setText("" + getDigit(seconds));
	}

	public String getDigit(int x) {
		if (x < 10)
			return "0" + x;
		return "" + x;
	}

	public int getValue() {
		return getHours() * 3600 + getMinutes() * 60 + getSeconds();
	}
}
