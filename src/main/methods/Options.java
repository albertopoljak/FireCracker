package main.methods;
import java.util.ArrayList;
import main.Main;

public class Options {



	public static String inverseCharAtX( String word , int x ){
		int length = word.length();
		
		//Newline shouldn't be in string word
		word = word.replace("\n", "");
		
		if( length>0 && length>=x && x>0 ){
			if ( isCharLowerCase(word.charAt( x-1 ) ) ) {
				return word.substring( 0, x-1 )  + word.substring( x-1 , x ).toUpperCase() + word.substring( x, length );
			}else if ( isCharUpperCase(word.charAt( x-1 ) ) ) {
				return word.substring( 0, x-1 )  + word.substring( x-1, x ).toLowerCase() + word.substring( x, length );
			}else
				return null;
				//Returned null to avoid duplicates in case of first char being a number 
				//It's expected that this method will return a different string but if number is first it will return the same string(that's why return null)
		}else if( x<1 ){
			Log.write("X has to be higher that 0!", 'E');
			return null;
		}else{
			Log.write("Can't use function \\ip"+x+" , not that much chars in word!", 'E');
			return null;
		}
	}
	
	
	
	private static boolean isCharUpperCase(char C){
		if(( C >= 'A' && C <= 'Z') || C =='È' || C =='Æ' || C =='Ž' || C =='Ð' || C =='Š' )
			return true;
		else
			return false;
	}
	
	
	private static boolean isCharLowerCase(char C){
		if(( C >= 'a' && C <= 'z') || C =='è' || C =='æ' || C =='ž' || C =='ð' || C =='š' )
			return true;
		else
			return false;
	}
	
	
	
	/*
	 * Generates all permutations of inputed string WITHOUT duplicates
	 * Example input: "abc" , output is "abc","bac","acb",cab","cba",bca"
	 * Complexity N! where N is num of chars in input
	 * Aprox memory used is: 0.09*N!/1024/1024 GB
	 */
	public static ArrayList<String> subsetsCharacters( String s ) {
		ArrayList<String> generatedWords = new ArrayList<String>(); 
		int length = s.length();
			
		if( length>12 ){
			Log.write("Word length is larger then 10!", 'E');
		}else{
			char[] a = new char[length];
			for (int i = 0; i < length; i++)
				a[i] = s.charAt(i);
			permChars(a, length, generatedWords);
		}
			
		return generatedWords;
	}
		
	//Helper for subsetsCharacters
	private static void swapChars(char[] a, int i, int j) {
		char c = a[i];
		a[i] = a[j];
		a[j] = c;
	}
		
	//Main helper for subsetsCharacters
	private static void permChars(char[] a, int n, ArrayList<String> generatedWords ) {
		if (n == 1) {
			generatedWords.add(String.valueOf(a));
			return;
		}
			
		int i;
		for (i = n; i < n; i++) {
		    if (a[i] == a[n-1])
		        return;
		}
			
		int j;
		for (i = 0; i < n; i++) {
		    boolean duplicate = false;
		    for (j = 0; !duplicate && j < i; j++)
		        duplicate = a[i] == a[j];
		    if (!duplicate) {
			   	swapChars(a, i, n-1);
			   	permChars(a, n-1, generatedWords);
			   	swapChars(a, i, n-1);
		    }
		}
	}
	
	
	
	
	
}
