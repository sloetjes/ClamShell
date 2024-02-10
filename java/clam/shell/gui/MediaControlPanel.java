package clam.shell.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

public abstract class MediaControlPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public JButton recordButton = new JButton("RECORD");
	public JButton startButton = new JButton("START");
	public JButton pauseButton = new JButton("PAUSE");
	public JButton stopButton = new JButton("STOP");
	public JButton ejectButton = new JButton("EJECT");

	public void start() {
	}

	public void stop() {
	}

	public void record() {
	}

	public void pause() {
	}

	public void eject() {
	}

	long startTime = System.currentTimeMillis();

	public void paint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHints(rh);
		int v = 240;
		g.setColor(new Color(v, v, v));
		g.fillRect(0, 0, getWidth(), getHeight());

		super.paint(g);
	}

	public MediaControlPanel() {

		// startButton = new JButton(new ImageIcon ("static/play.png"));
		recordButton = new RecordButton();
		startButton = new PlayButton();
		pauseButton = new PauseButton();
		stopButton = new StopButton();
		ejectButton = new EjectButton();
		this.setOpaque(false);
		// this.setBackground(Color.BLACK);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setBorder(null);

		add(recordButton);
		add(pauseButton);
		add(startButton);
		add(stopButton);
		// add(ejectButton);
		/*
		 * ImageCanvas canvas = new ImageCanvas (); canvas.setImage(new
		 * ioLogoGraphic().getImage().getScaledInstance(64,64,BufferedImage.SCALE_SMOOTH
		 * )); canvas.setGridVisible(false); canvas.setBackground(Color.BLACK); add
		 * (canvas);
		 */
		// stopButton.setEnabled(false);
		recordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				record();
			}
		});
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pause();
			}
		});
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		ejectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eject();
			}
		});

	}

}

abstract class FancyButton extends JButton {
	public boolean hasMouse = false;
	public static int size = 16;

	public FancyButton() {
		setOpaque(false);
		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				hasMouse = true;
				triggerAnimation();
				repaint();
			}

			public void mouseExited(MouseEvent e) {
				hasMouse = false;
				triggerAnimation();
				repaint();
			}
		});
		setPreferredSize(new Dimension(size, size));
	}

	public synchronized void triggerAnimation() {
		new Thread() {
			public void run() {

			}
		}.start();
	}
}

class EjectButton extends FancyButton {
	public EjectButton() {
	}

	public void paint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		int width = getWidth();
		int height = getHeight();
		if (hasMouse) {
			g.setColor(new Color(64, 0, 64));
		} else {
			g.setColor(new Color(255, 0, 255));
		}
		g.fillRect(width / 4, width / 4, width / 2, width / 2);

	}
}

class PauseButton extends FancyButton {
	public PauseButton() {
		setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	public void paint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		int width = getWidth();
		int height = getHeight();

		if (hasMouse && isEnabled()) {
			g.setColor(new Color(64, 64, 0));
		} else {
			g.setColor(new Color(255, 255, 0));
		}

		g.fillRect(width / 3, width / 4, width / 8, width / 2);
		g.fillRect(width - width / 3, width / 4, width / 8, width / 2);
		g.setColor(new Color(0, 0, 0));

		g.drawRect(width / 3, width / 4, width / 8, width / 2);
		g.drawRect(width - width / 3, width / 4, width / 8, width / 2);
	}
}

class PlayButton extends FancyButton {
	public PlayButton() {
		setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	public void paint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHints(rh);
		int width = getWidth();
		int height = getHeight();

		if (hasMouse) {
			g.setColor(new Color(0, 64, 0));
		} else {
			g.setColor(new Color(0, 255, 0));
		}

		int[] x = new int[] { width / 2 - width / 4, width / 2 - width / 4, width / 2 + width / 4 };
		int[] y = new int[] { width / 2 - width / 4, width / 2 + width / 4, width / 2 };
		Polygon p = new Polygon(x, y, 3);
		g.fillPolygon(p);

		g.setColor(new Color(0, 0, 0));
		g.drawPolygon(p);
	}
}

class RecordButton extends FancyButton {

	public RecordButton() {

	}

	public void paint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHints(rh);
		int width = getWidth();
		int height = getHeight();

		if (hasMouse && isEnabled()) {
			g.setColor(new Color(64, 0, 0));
		} else {
			g.setColor(new Color(255, 0, 0));
		}

		g.fillRoundRect(width / 4, height / 4, width / 2, height / 2, width / 2, height / 2);

		g.setColor(new Color(255, 255, 255));
		g.drawRoundRect(width / 4, height / 4, width / 2, height / 2, width / 2, height / 2);
	}
}

class StopButton extends FancyButton {
	public StopButton() {
		setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	public void paint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		int width = getWidth();
		int height = getHeight();
		if (hasMouse) {
			g.setColor(new Color(128, 0, 0));
		} else {
			g.setColor(new Color(255, 0, 0));
		}

		g.fillRect(width / 4, width / 4, width / 2, width / 2);
		g.setColor(new Color(0, 0, 0));
		g.drawRect(width / 4, width / 4, width / 2, width / 2);

	}
}
