
public class NumberUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// System.out.println(convertRoman2Dec("CMLXIXVIVIII"));
		// System.out.println(convertDec2Roman(981));
		System.out.println(convertToAotherBase(99, 17));
		//System.out.println(convertToBase10(11111111, 2));
	}

	
    public static String convertToAotherBaseMax32(int num, int base) {
        String string = new String();
        while(num>0) {
            string = "0123456789ABCDEFGHIJKLMNOPQRSTVW".charAt(num%base) + string;
            num = num/base;
        }
        return string;
    }
	private static int convertToAotherBase(int x, int a) { // from 10 base to another
		int res = 0;
		res = Integer.valueOf(Integer.toString(x, a));
		return res;
	}
	
	private static int convertToBase10(int x, int a) { // mumber x in base a
		int res = 0;
		char[] arr = String.valueOf(x).toCharArray();
		int N = arr.length;
		for (int i = 0; i < N; i++) {
			res += (arr[i]-'0')*Math.pow(a, N-i-1);
		}
		return res;
	}
	
	//For series with radix 4
	public static void radix() {
		int res = 0;
		while (Integer.valueOf(Integer.toString(res, 4)) < 10000) {
			res ++;
			System.out.println(Integer.valueOf(Integer.toString(res, 4)));
		}
		System.out.println(res);
	}
	
	//UCLN  (BCNN a, b = a/UCLN*b)
    long gcb(long a, long b) {
    	return (b != 0)? gcb(b, a%b) : a;
    }
    //Exclusive recursion
	long gcd(long a, long b) {
		while (b != 0) {
			long c = b;
			b = a % b;
			a = c;
		}
		return a;
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

	// Area for Triangle with 3 points    
    double area(long[] A, long[] B, long[] C) { // Point A, B, C: x y z: 0 1 2"

        long dx1 = A[0] - B[0];
        long dy1 = A[1] - B[1];
        long dz1 = A[2] - B[2];
        long dx2 = C[0] - B[0];
        long dy2 = C[1] - B[1];
        long dz2 = C[2] - B[2];            
        long len = (dx1 * dy2 - dx2 * dy1) * (dx1 * dy2 - dx2 * dy1);
        len += (dx1 * dz2 - dx2 * dz1) * (dx1 * dz2 - dx2 * dz1);
        len += (dz1 * dy2 - dz2 * dy1) * (dz1 * dy2 - dz2 * dy1);

    	return Math.sqrt((double)len) / 2.;
    }
    long area2(long[] A, long[] B, long[] C) { // Point A, B, C: "x y z: 0 1 2"

        long dx1 = A[0] - B[0];
        long dy1 = A[1] - B[1];
        long dz1 = A[2] - B[2];
        long dx2 = C[0] - B[0];
        long dy2 = C[1] - B[1];
        long dz2 = C[2] - B[2];            
        long len = (dx1 * dy2 - dx2 * dy1) * (dx1 * dy2 - dx2 * dy1);
        len += (dx1 * dz2 - dx2 * dz1) * (dx1 * dz2 - dx2 * dz1);
        len += (dz1 * dy2 - dz2 * dy1) * (dz1 * dy2 - dz2 * dy1);
    	return len;
    }
    // Area for Triangle with 2 points   
    double area(String p1, String p2, String p3) { // Point "x y"
    	double res = 0;
    	String[] A = p1.split(" ");
    	String[] B = p2.split(" ");
    	String[] C = p3.split(" ");    	
    	
    	res = 0.5* Math.abs(((Integer.valueOf(C[0])-Integer.valueOf(A[0]))*(Integer.valueOf(B[1])-Integer.valueOf(A[1])) -
    			(Integer.valueOf(B[0])-Integer.valueOf(A[0]))*(Integer.valueOf(C[1])-Integer.valueOf(A[1]))));
    	
    	return res;
    }
}
