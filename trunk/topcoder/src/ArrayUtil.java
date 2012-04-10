import java.util.Arrays;


public class ArrayUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	// Sort in DESC
	static int[] sortInDESC(int[] input) {
		int[] arr = input.clone();
		int half = arr.length/2;
	    Arrays.sort(arr);	
	    // Reverse
	    for (int i = 0; i < half; i++) {
			int tmp = arr[i];
			arr[i] = arr[arr.length-1-i];
			arr[arr.length-1-i] = tmp;			
		}
	    return arr;
	}
}
