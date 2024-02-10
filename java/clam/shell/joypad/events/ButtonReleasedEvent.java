package clam.shell.joypad.events;

public class ButtonReleasedEvent extends JoypadEvent {

	public ButtonReleasedEvent(int controllerID, int buttonID) {
		super(controllerID, JoypadEvent.BUTTON_RELEASED, buttonID);	
	}

}
