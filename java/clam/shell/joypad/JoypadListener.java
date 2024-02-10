package clam.shell.joypad;

import clam.shell.joypad.events.JoypadEvent;

public abstract class JoypadListener {
	
	public abstract void action(JoypadEvent event) ;
	
	public JoypadListener () {
		this(0);
	}
	private int controllerID = -1;
	public void setControllerID(int controllerID) {
		this.controllerID = controllerID;
	}
	public int getControllerID() {		
		return controllerID;
	}
	public JoypadListener (int controllerID) {
		setControllerID(controllerID);
	}	
}
