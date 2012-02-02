
public class NumberUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(convertRoman2Dec("CMLXIXVIVIII"));
		System.out.println(convertDec2Roman(981));
	}

	//UCLN  (BCNN a, b = a*b/UCLN)
    int gcb(int a, int b) {
    	return (b != 0)? gcb(b, a%b) : a;
    }

	//Multiple number with 0-9
    static String mutilpt(String num, int a) {
    	if (a == 0) return "0";
    	String numT = num;
    	char[] arr = numT.toCharArray();
    	int remain = 0;
    	for (int i = arr.length-1; i >= 0; i--) {
    		int tem = (arr[i]-'0') * a + remain;
			if (tem > 9) {
				arr[i] = (char)(tem%10 + '0');
				remain = tem/10;
			} else {
				arr[i] = (char)(tem + '0');
			}
		}
    	if (remain == 0)
    		return String.valueOf(arr);
    	else {
    		return String.valueOf(remain) + String.valueOf(arr); 
    	}
    }
	
	//Subtract number with 0-9
    static String subtract(String num, int sub) {
    	String numT = num;
    	char[] arr = numT.toCharArray();
    	
    	for (int i = arr.length-1; i >= 0; i--) {
    		if (sub == 0) break;
    		int tem = (arr[i]-'0' - sub);
			if (tem < 0) {
				arr[i] = (char)(tem + 10 + '0');				
				sub = 1;
			} else {
				arr[i] = (char)(arr[i] - sub);
				sub = 0;
			}
		}
    	return String.valueOf(arr);
    }
	
	//Add a number with 0-9
    static String add(String num, int add) {
    	String numT = num;
    	char[] arr = numT.toCharArray();
    	
    	for (int i = arr.length-1; i >= 0; i--) {
    		if (add == 0) break;
    		int tem = (arr[i]-'0' + add);
			if (tem > 9) {
				arr[i] = (char)(tem - 10 + '0');				
				add = tem/10;
			} else {
				arr[i] = (char)(arr[i] + add);
				add = 0;
			}
		}
    	
    	if (add == 0)
    		return String.valueOf(arr);
    	else {
    		return String.valueOf(add) + String.valueOf(arr); 
    	}
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
