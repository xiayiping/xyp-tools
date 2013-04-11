package xyp.tool.util;

public class NumberUtil {

	private static final int CODE_A = 65;
	private static final int BINARY = 26;

	/**
	 * parse int to excel format column index
	 * 
	 * @param i
	 * @return
	 */
	public static String intIndexToStringIndex(int i) {
		int div = i / BINARY;
		i = i % BINARY;
		char s = (char) (('A') + i);
		if (div == 0)
			return String.valueOf(new char[] { s });
		else {
			div = div - 1;
			return intIndexToStringIndex(div) + String.valueOf(new char[] { s });
		}
	}

	/**
	 * parse A to 0, Z to 25, AA to 26 ...... like that
	 * 
	 * @param s
	 * @return
	 */
	public static int stringIndexToIntIndex(String s) {

		int ret = 0;
		s = s.toUpperCase();
		char[] chars = s.toCharArray();
		int length = chars.length;
		for (int i = 0; i < length; i++) {
			char c = chars[i];
			int ci = c - CODE_A + 1;
			int p = power(BINARY, length - 1 - i);
			ret = ret + ci * p;
			// ret = ret
		}

		return ret - 1;
	}

	public static int power(int base, int pow) {
		if (pow == 0)
			return 1;
		for (int i = 1; i < pow; i++) {
			base *= base;
		}

		return base;
	}

	/**
	 * 48 49 50 51 52 53 54 55 56 57 <br/>
	 * 65 66 67 68 69 70 <br/>
	 * 97 98 99 100 101 102<br/>
	 * 
	 * @param Hex
	 * @return
	 */
	public static byte HexToFourBitsByte(char hex) {
		byte hc = (byte) hex;
		if (hc >= 48 && hc <= 57) {
			return (byte) (hc - 48);
		}
		if (hc >= 65 && hc <= 70) {
			return (byte) (10 + (hc - 65));

		}
		if (hc >= 97 && hc <= 102) {
			return (byte) (10 + (hc - 97));

		}
		return 0;
	}

	public static char FourBitsByteToHex(byte b) {

		if (b >= 10) {
			return (char) ((byte) ('A') + (byte) (b - 10));
		}

		return (char) ((byte) ('0') + (byte) (b));
	}

	public static byte HexToByte(String Hex) {
		char[] cs = Hex.toCharArray();
		byte[] bs = new byte[] { HexToFourBitsByte(cs[0]), HexToFourBitsByte(cs[1]) };
		int r = ((bs[0] << 4) + bs[1]);
		return (byte) r;
	}

	public static void main(String[] args) {

		System.out.println(HexToByte("ff"));
		System.out.println((byte) 0xff);
	}

	public static String byteToHex(byte b) {
		char[] ret = new char[2];
		byte b0 = (byte) ((b >> 4) & (0x0F));
		byte b1 = (byte) (b & (0x0F));
		ret[0] = FourBitsByteToHex(b0);
		ret[1] = FourBitsByteToHex(b1);
		return new String(ret);
	}

	public static String byteToBit(byte b) {
		char[] ret = new char[8];
		for (int i = 0; i < 8; i++) {
			if (b % 2 == 1 || b % 2 == -1) {
				ret[7 - i] = '1';
			} else {

				ret[7 - i] = '0';
			}
			b = (byte) (b / 2);
		}
		return new String(ret);
	}

	public static String numberToAlphabet(int number) {
		// System.out.println((int) ('z'));
		String ret = "";

		if (number < 26) {
			return (char) ((byte) ('A') + (number) % 26) + ret;
		}

		while (number >= 25) {

			int left = number % 26;

			ret = (char) ((byte) ('A') + left) + ret;

			number = number / 26;
		}

		if (number > 0) {
			ret = (char) ((byte) ('A') + (number - 1) % 26) + ret;
		}
		// A65
		// Z90
		// a97
		// z122
		return ret;
	}

}
