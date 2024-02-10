package clam.shell.joypad.events;

public class ButtonPressedEvent extends JoypadEvent {

	public ButtonPressedEvent(int controllerID, int buttonID) {
		super(controllerID, JoypadEvent.BUTTON_PRESSED, buttonID);
	}

}
