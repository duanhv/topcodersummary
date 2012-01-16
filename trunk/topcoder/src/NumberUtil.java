
public class NumberUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(convertRoman2Dec("CMLXIXVIVIII"));
		System.out.println(convertDec2Roman(981));
	}

	// Check is Prime or not
	boolean isPrime(long n) {
        boolean res = true;
        if (n == 1) return false;
        for (long i = 2; i*i <=n; i++) {
            if (n%i == 0) return false;
        }
        return res;
    }

	// Convert Roman 2 Decimal 
	static char[] CODE   = {'M',  'D', 'C', 'L', 'X', 'V', 'I'};
	static int[]    NUM  = {1000, 500, 100,  50, 10,   5,   1};
	static int convertRoman2Dec(String roman) {
		int dec = 0;
		int cur = 0;
		char[] arr = roman.toCharArray();
		for (int i = arr.length-1; i >=0 ; i--) {
			for (int j = 0; j < CODE.length; j++) {
				if (CODE[j] == arr[i]) {
					dec += (cur <= NUM[j])? NUM[j]: (-1*NUM[j]);
					cur = NUM[j];
					break;
				}
			}
		}		
		return dec;
	}
	
	// Convert Decimal 2 Roman
	static String[] CODE2 = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
	static int[]  NUM2  = {1000,  900,  500, 400,  100,  90,   50,  40,  10,    9,    5,   4,   1};
	static String convertDec2Roman(int dec) {
		String roman = "";
		while (dec > 0) {
			for (int j = 0; j < CODE2.length; j++) {
				if (dec >= NUM2[j]) {
					dec -= NUM2[j];
					roman += CODE2[j];
				}
			}
		}
		return roman;
	}
	
}
