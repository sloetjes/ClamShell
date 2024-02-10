package clam.shell.joypad;

public class Joypad {
	int id = -1;
	public boolean isConnected = false;
	public double x;
	public double y;
	public boolean[] isPressed = new boolean[64];
	public boolean left;
	public boolean right;
	public boolean up;
	public boolean down;	
	public Joypad(int id) {
		this.id = id;
	}	
}

