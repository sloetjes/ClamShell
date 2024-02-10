package clam.shell;

public class Clam {
	public static void nap () {
		nap (1);
	}
	public static void nap (long ms) {
		try {
			Thread.sleep(ms);
		} catch (Exception e) {			
		}
	}
}
