package clam.shell.gui;

import javax.swing.JTextField;

public class IntegerTextField extends JTextField {
	public IntegerTextField () {
		this(0);
	}
	public IntegerTextField (int v) {
		super (""+v);
	}
	public IntegerTextField(String string, int i) {
		// TODO Auto-generated constructor stub
	}
	public int getValue () throws Exception {
		return Integer.parseInt(getText());
	}
}
