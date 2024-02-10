package clam.shell.joypad.events;

public class AxisEventX extends JoypadEvent {

	public AxisEventX(int controllerID, float value) {
		super(controllerID, JoypadEvent.XAXIS_EVENT_TYPE, value);
	}
	
}
