package clam.shell.pong;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

import clam.shell.joypad.JoypadListener;
import clam.shell.joypad.JoypadStream;
import clam.shell.joypad.events.JoypadEvent;
import clam.shell.joypad.events.AxisEventX;
import clam.shell.joypad.events.AxisEventY;

public class MultiplayerPong {
	JoypadStream bridge = null;
	PongPaddle[] paddles = { new PongPaddle(0), new PongPaddle(1), new PongPaddle(2), new PongPaddle(3) };
	int dim1 = 32;
	int dim2 = 256;
	int screenWidth = 1024;
	int screenHeight = 1024;

	public MultiplayerPong() throws Exception {
		bridge=new JoypadStream();
		// top
		paddles[0].setLocation((screenWidth - dim2) / 2, 0);
		paddles[0].setSize(dim2, dim1);
		// bottom
		paddles[1].setLocation((screenWidth - dim2) / 2, screenHeight - dim1);
		paddles[1].setSize(dim2, dim1);
		
		// left
		paddles[2].setLocation(0, (screenHeight - dim2) / 2);
		paddles[2].setSize(dim1, dim2);
		// right
		paddles[3].setLocation(screenWidth - dim1, (screenHeight - dim2) / 2);
		paddles[3].setSize(dim1, dim2);
		
		
		bridge.addControllerListener(new JoypadListener() {
			public void action(JoypadEvent event) {
				int xd = 0;
				int yd = 0;
				if (event instanceof AxisEventX) {					
					
				} else if (event instanceof AxisEventY) {
					
				}
				if (xd!=0||yd!=0) {
					
				}
			}
		});
	}

	public static void main(String[] args) {
		try {
			new MultiplayerPong();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class PongPaddle extends JFrame {
	Color[] colors = { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW };
	int id;

	public PongPaddle(int id) {
		this.id = id;
		this.setUndecorated(true);
		this.setVisible(true);
		this.setAlwaysOnTop(true);
	}

	public void paint(Graphics g) {
		g.setColor(colors[id]);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	Color color = null;

	public void setColor(Color color) {
		this.color = color;

	}

	public void moveX(float amount) {		
		this.setLocation(this.getX() + (int) (8 * amount), this.getY());

	}

	public void moveY(float amount) {
		this.setLocation(this.getX(), (int) (8 * amount) + this.getY());

	}
}