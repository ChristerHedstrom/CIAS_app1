package test;

public class TestNumberStr {

	public static void main(String[] args) {
		boolean isNumber = isPropertyNumber("sa");
		System.out.println("isNumber: " + isNumber);
	}

	private static boolean isPropertyNumber(String str) {
		try {
			new Integer(str);
		} catch (NumberFormatException nfe) {
			return false;
		}

		return true;
	}
}
