package clam.shell.joypad.events;

public class DpadPressedEvent extends JoypadEvent {
	public DpadPressedEvent(int controllerID, int buttonID) {
		super(controllerID, JoypadEvent.BUTTON_PRESSED, buttonID);
	}
}
