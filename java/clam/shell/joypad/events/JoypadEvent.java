package clam.shell.joypad.events;

public class JoypadEvent {

	// 0 through 13 are mapped to incoming data from native .EXE
	public static int VBUTTON_Y = 0;
	public static int VBUTTON_B = 1;
	public static int VBUTTON_A = 2;
	public static int VBUTTON_X = 3;
	public static int LEFT_TRIGGER_1 = 4;
	public static int LEFT_TRIGGER_2 = 6;
	public static int RIGHT_TRIGGER_1 = 5;
	public static int RIGHT_TRIGGER_2 = 7;
	public static int VBUTTON_MINUS = 8;
	public static int VBUTTON_PLUS = 9;
	public static int LEFT_THUMB = 10;
	public static int RIGHT_THUMB = 11;
	public static int VBUTTON_GO = 12;
	public static int VBUTTON_MODE = 13;

	public static int VBUTTON_UP = 100;
	public static int VBUTTON_DOWN = 101;
	public static int VBUTTON_LEFT = 102;
	public static int VBUTTON_RIGHT = 103;

	public static int BUTTON_PRESSED = 200;
	public static int BUTTON_CHANGED = 201;
	public static int BUTTON_RELEASED = 202;
	public static int XAXIS_EVENT_TYPE = 203;
	public static int YAXIS_EVENT_TYPE = 204;
	public static int CONNECTED = 203;
	public static int DISCONNECTED = 204;

	float value = 0f;

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	int controllerID;
	int eventType;
	int buttonID;
	long timestamp = -1;

	public JoypadEvent(int controllerID, int eventType, float value) {
		this.setControllerID(controllerID);
		this.setEventType(eventType);
		this.setValue(value);
	}

	public JoypadEvent(int controllerID, int eventType, int buttonID) {
		this(System.currentTimeMillis(), controllerID, eventType, buttonID);
	}

	public JoypadEvent(long timestamp, int controllerID, int eventType, int buttonID) {
		this.setControllerID(controllerID);
		this.setEventType(eventType);
		this.setButtonID(buttonID);
	}

	public int getControllerID() {
		return controllerID;
	}

	public void setControllerID(int controllerID) {
		this.controllerID = controllerID;
	}

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public int getButtonID() {
		return buttonID;
	}

	public void setButtonID(int buttonID) {
		this.buttonID = buttonID;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
