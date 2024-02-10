package clam.shell.joypad;

import java.util.ArrayList;
import java.util.Scanner;

import clam.shell.joypad.events.ButtonPressedEvent;
import clam.shell.joypad.events.ButtonReleasedEvent;
import clam.shell.joypad.events.DpadPressedEvent;
import clam.shell.joypad.events.DpadReleasedEvent;
import clam.shell.joypad.events.JoypadEvent;
import clam.shell.joypad.events.AxisEventX;
import clam.shell.joypad.events.AxisEventY;

/*
 * Copyright 2024 Christopher Sloetjes
 * http://sloetjes.github.io
 */
public class JoypadStream {
	final private static String pathToJoypadEXE = "./native/JoypadStream.exe";
	public Joypad[] controllers = new Joypad[8];
	
	final private ArrayList<JoypadListener> listeners = new ArrayList<JoypadListener>();
	public void addControllerListener(JoypadListener controllerListener) {
		listeners.add(controllerListener);
	}
	public void removeControllerListener(JoypadListener controllerListener) {
		listeners.remove(controllerListener);
	}

	public JoypadStream() throws Exception {
		for (int i = 0; i < controllers.length; i++) {
			controllers[i] = new Joypad(i);
		}
		final Scanner scanner = new Scanner(new ProcessBuilder(pathToJoypadEXE).start().getInputStream());
		new Thread() {
			private void fireEvent(JoypadEvent event) {
				for (JoypadListener listener : listeners) {
					if (listener.getControllerID() == -1 || event.getControllerID() == listener.getControllerID()) {
						listener.action(event);
					}
				}
			}
			public void run() {
				while (true) {
					try {
						try {
							Thread.sleep(1);
						} catch (Exception e) {
							e.printStackTrace();
							System.exit(0);
						}
						String line = scanner.nextLine();
						if (line.contains("ButtonChanged")) {
							// ignore these
						} else {
							int controllerID = Integer.parseInt(line.substring(0, 1));							
							if (line.contains("ButtonPressed") && line.contains("DPadLeft")) {
								fireEvent(new DpadPressedEvent(controllerID, JoypadEvent.VBUTTON_LEFT));
							} else if (line.contains("ButtonPressed") && line.contains("DPadRight")) {
								fireEvent(new DpadPressedEvent(controllerID, JoypadEvent.VBUTTON_RIGHT));
							} else if (line.contains("ButtonPressed") && line.contains("DPadUp")) {
								fireEvent(new DpadPressedEvent(controllerID, JoypadEvent.VBUTTON_UP));
							} else if (line.contains("ButtonPressed") && line.contains("DPadDown")) {
								fireEvent(new DpadPressedEvent(controllerID, JoypadEvent.VBUTTON_DOWN));								
							} else if (line.contains("ButtonReleased") && line.contains("DPadLeft")) {
								fireEvent(new DpadReleasedEvent(controllerID, JoypadEvent.VBUTTON_LEFT));							
							} else if (line.contains("ButtonReleased") && line.contains("DPadRight")) {
								fireEvent(new DpadReleasedEvent(controllerID, JoypadEvent.VBUTTON_RIGHT));							
							} else if (line.contains("ButtonReleased") && line.contains("DPadUp")) {
								fireEvent(new DpadReleasedEvent(controllerID, JoypadEvent.VBUTTON_UP));							
							} else if (line.contains("ButtonReleased") && line.contains("DPadDown")) {
								fireEvent(new DpadReleasedEvent(controllerID, JoypadEvent.VBUTTON_DOWN));
							} else {
								Scanner s = new Scanner(line = line.replaceAll(",", ""));
								if (line.contains("ButtonPressed")) {									
									while (s.hasNext()) {
										if (s.next().contains("index:")) {
											fireEvent(new ButtonPressedEvent(controllerID, Integer.parseInt(s.next())));
										}
									}
								} else if (line.contains("ButtonReleased")) {									
									while (s.hasNext()) {
										if (s.next().contains("index:")) {
											fireEvent(new ButtonReleasedEvent(controllerID, Integer.parseInt(s.next())));
										}
									}
								} else if (line.contains("AxisChanged(LeftStickX")) {									
									while (s.hasNext()) {
										if (s.next().contains("AxisChanged(LeftStickX")) {
											fireEvent(new AxisEventX(controllerID, Float.parseFloat(s.next())));
										}
									}
								} else if (line.contains("AxisChanged(LeftStickY")) {									
									while (s.hasNext()) {
										if (s.next().contains("AxisChanged(LeftStickY")) {
											fireEvent(new AxisEventY(controllerID, Float.parseFloat(s.next())));
										}
									}
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
}
