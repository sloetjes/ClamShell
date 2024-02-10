package clam.shell.maths;

public class Maths {	
	static int kilo = 1000;
	static int mega = kilo*kilo;
	static int giga = mega*kilo;
	static int tera = giga*kilo;
	//psuedo-Latin
	static int kilobyte = 1024;
	static int megabyte = kilobyte*kilobyte;
	static int gigabyte = megabyte*kilobyte;
	static int terabyte = gigabyte*kilobyte;
	public static String comma (long n) {
		if (n >= 1000) {
			return "" + comma(n / 1000) + "," + commaR(n % 1000);			
		} else {
			return "" + n;
		}
	}
	private static String commaR (long n) {
		if (n >= 1000) {
			return "" + commaR(n / 1000) + "," + commaR(n % 1000);
		} else if (n >= 100) {
			return "" + n;
		} else if (n >= 10) {
			return "0" + n;
		}
		return "00" + n;
	}
	public static String printDoubleValue(double number, int decimalPlaces) {		
		String value = String.format("%." + decimalPlaces + "f", number);
		int totalLength = Math.max(decimalPlaces + 2, value.length());
		return String.format("%-" + totalLength + "s", value);		
	}
	
	public static double sigmoid(double x) {
	        return 1 / (1 + Math.exp(-x));
	}
}
