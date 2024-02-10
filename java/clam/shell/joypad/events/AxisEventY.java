package clam.shell.joypad.events;

public class AxisEventY extends JoypadEvent {

	public AxisEventY(int controllerID, float value) {
		super(controllerID, JoypadEvent.XAXIS_EVENT_TYPE, value);
	}
	
}
