package clam.shell.joypad.events;

public class DpadReleasedEvent extends JoypadEvent {
	public DpadReleasedEvent(int controllerID, int buttonID) {
		super(controllerID, JoypadEvent.BUTTON_PRESSED, buttonID);
	}
}
