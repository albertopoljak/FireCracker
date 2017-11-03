package main.methods;
import main.Main;

public class CombinatoricsPermutations {
	
	private CombinatoricsPermutations(){};
	
	/*
	 * You have to pass a clean string array, with no nulls
	 * Number of generated words: n = multiply number of words in 1D array block with each number of words from next block
	 * Example input {{"apple","Apple"},{"banana","Banana",}} will result in: applebanana , appleBanana , Applebanana , AppleBanana
	 */
	public static void combinations2D(String [][] g) {
		int n = 1;
		int length = g.length;
			
		for (int j = 0; j < length; j++) {
			n *= g[j].length;
		}

		int i[] = new int[length];
		String tempWords[] = new String[length];
		int tempPos = 0;
		for (int k = 0; k < n; k++) {
			for (int rr = 0; rr < length; rr++) {
				tempWords[tempPos] = g[rr][i[rr]];
				tempPos++;
			}
			//Immediately permute word so we don't have to store it and waste memory 
			heapPermutation(tempWords , length);
				
			tempWords = new String[length];  
			tempPos = 0;

			i[length-1]++;
			for (int j = length-1; j > 0; j--) {
				if (i[j] >= g[j].length) {
					i[j] = 0;
					i[j - 1]++;
				} else
					break;
			}
			
		}
			
	}
	
	
	
	/*
	 * Returns total number of generated words for method combinations2D
	 */
	public static long combinations2DCount(String [][] g) {
		int length = g.length;
		long totalCombinationCount = 1;
		
		for(int i = 0; i < length; ++i)
			totalCombinationCount *= g[i].length;
		
		return totalCombinationCount;	
	}
	
	
	
	/*
	 * Number of generated words is N! where N is number of input words
	 * Value size needs to be passed as a.length
	 * Example input {"apple","banana"} will result in: applebanana , bananaapple
	 */
	public static void heapPermutation(String a[], int size){
		// if size becomes 1 then save the obtained permutation
		if (size == 1){
			Main.writeStringToPrintWriter(a);
			Main.test++;
		}
		 
		for (int i=0; i<size; i++){
			heapPermutation(a, size-1);
		 
			// if size is odd, swap first and last
			// element
			if (size % 2 == 1){
				String temp = a[0];
				a[0] = a[size-1];
				a[size-1] = temp;
			}
		 
			// If size is even, swap ith and last
			// element
			else{
				String temp = a[i];
				a[i] = a[size-1];
				a[size-1] = temp;
			}
			
		}
		
	}
	
	
	
}
