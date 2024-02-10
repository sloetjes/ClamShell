package sloetjes.clam_shell;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
	static String[] data = { 
			"0  ButtonPressed(DPadLeft, Code(EvCode(EvCode { kind: Button, index: 4294967295 })))",			
			"0  ButtonChanged(DPadLeft, 1.0, Code(EvCode(EvCode { kind: Button, index: 4294967295 })))",
			"1  AxisChanged(LeftStickX, 0.120, Code(EvCode(EvCode { kind: Axis, index: 0 })))",			
			"1  AxisChanged(LeftStickY, 0.66883206, Code(EvCode(EvCode { kind: Axis, index: 1 })))",			
			"2  ButtonPressed(East, Code(EvCode(EvCode { kind: Button, index: 33 })))",			
			"0  ButtonReleased(DPadDown, Code(EvCode(EvCode { kind: Button, index: 4294967294 })))",			
			"0  ButtonPressed(DPadLeft, Code(EvCode(EvCode { kind: Button, index: 4294967295 })))",			
			"0  ButtonReleased(DPadLeft, Code(EvCode(EvCode { kind: Button, index: 4294967295 })))",			
			"0  ButtonPressed(DPadRight, Code(EvCode(EvCode { kind: Button, index: 4294967293 })))",			
			"0  ButtonReleased(DPadRight, Code(EvCode(EvCode { kind: Button, index: 4294967293 })))",			
			"3  ButtonPressed(DPadUp, Code(EvCode(EvCode { kind: Button, index: 4294967292 })))",			
			"3  ButtonReleased(DPadUp, Code(EvCode(EvCode { kind: Button, index: 4294967292 })))",
	};

	public static void main(String[] args) {
		for (int i = 0; i < data.length; i++) {
			if (data[i].contains("ButtonChanged")) {
				// do nothing
			} else {
				int controllerID = Integer.parseInt(data[i].substring(0, 1));
				System.out.print("Controller ID =" + controllerID);				
				if (data[i].contains("ButtonPressed") && data[i].contains("DPadLeft")) {
					System.out.print(" +left");
				} else if (data[i].contains("ButtonReleased") && data[i].contains("DPadLeft")) {
					System.out.print(" -left");
				} else if (data[i].contains("ButtonPressed") && data[i].contains("DPadRight")) {
					System.out.print(" +right");
				} else if (data[i].contains("ButtonReleased") && data[i].contains("DPadRight")) {
					System.out.print(" -right");
				} else if (data[i].contains("ButtonPressed") && data[i].contains("DPadUp")) {
					System.out.print(" +up");					
				} else if (data[i].contains("ButtonReleased") && data[i].contains("DPadUp")) {
					System.out.print(" -up");
				} else if (data[i].contains("ButtonPressed") && data[i].contains("DPadDown")) {
					System.out.print(" +down");					
				} else if (data[i].contains("ButtonReleased") && data[i].contains("DPadDown")) {
					System.out.print(" -down");					
				} else if (data[i].contains("ButtonPressed")) {
					int buttonID = getInt(data[i], "index:");
					System.out.print(" Button Pressed =" + buttonID);
				} else if (data[i].contains("ButtonReleased")) {
					int buttonID = getInt(data[i], "index:");
					System.out.print(" Button Released =" + buttonID);
				} else if (data[i].contains("AxisChanged(LeftStickX")) {
					System.out.print(" X= " + getFloat(data[i], "AxisChanged(LeftStickX"));
				} else if (data[i].contains("AxisChanged(LeftStickY")) {
					System.out.print(" Y= " + getFloat(data[i], "AxisChanged(LeftStickY"));
				}

				System.out.println();
			}
		}
	}

	public static int getInt(String line, String key) {
		// System.out.println(line);
		line = line.replaceAll(",", "");
		// System.out.println(line);
		Scanner scanner = new Scanner(line);
		while (scanner.hasNext()) {
			if (scanner.next().contains(key)) {
				return Integer.parseInt(scanner.next());
			}
		}
		return -1;
	}

	public static float getFloat(String line, String key) {
		line = line.replace(",", "");
		//System.out.println("1 "+line);
		Scanner scanner = new Scanner(line);
		while (scanner.hasNext()) {
			if (scanner.next().contains(key)) {
				return Float.parseFloat(scanner.next());
			}
		}
		return -1;
	}
}
