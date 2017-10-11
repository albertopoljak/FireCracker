package main.methods;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import main.Info;
import main.Main;

public class Universal {

	
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
	
	
	private static String inverseFirstChar(String word){
		if( word.length()>0 ){
			if ( isCharLowerCase(word.charAt(0)) ) {
				return word.substring(0, 1).toUpperCase() + word.substring(1);
			}else if ( isCharUpperCase(word.charAt(0)) ) {
				return word.substring(0, 1).toLowerCase() + word.substring(1);
			}else
				return null;
				//Returned null to avoid duplicates in case of first char being a number 
				//It's expected that this method will return a different string but if number is first it will return the same string(that's why return null)
		}else
			return null;
	}
	
	
	private static String inverseLastChar(String word){
		int length = word.length();
		
		//Newline shouldn't be in string word
		word = word.replace("\n", "");
		
		if( length>0 ){
			if ( isCharLowerCase(word.charAt( length-1) ) ) {
				return word.substring( 0, length-1 )  + word.substring( length-1, length ).toUpperCase();
			}else if ( isCharUpperCase(word.charAt( length-1 ) ) ) {
				return word.substring( 0, length-1 )  + word.substring( length-1, length ).toLowerCase();
			}else
				return null;
				//Returned null to avoid duplicates in case of first char being a number 
				//It's expected that this method will return a different string but if number is first it will return the same string(that's why return null)
		}else
			return null;
	}
	
	
	private static String inverseCharAtX( String word , int x ){
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
			Main.log("[ERROR] X has to be higher that 0!\n");
			return null;
		}else{
			Main.log("[ERROR] Can't use function \\ip"+x+" , not that much chars in word!\n");
			return null;
		}
	}
	
	
	public static long getMemoryUsageLong(){
		return ( Runtime.getRuntime().totalMemory()/1024/1024 );
	}
	
	
	/*
	 * Extract inputed words into ArrayList
	 * Recognizes words based on variable wordSeparator (newline is considered as word separator.)
	 * Blank words are eliminated
	 * Duplicates are not eliminated
	 */
	private static ArrayList<String> extractInputWords(String input, char wordSeparator){
		int length = input.length();

		if( !(input!=null) || length==0 ){
			Main.log("Input is empty, nothing to return!\n");
			return null;
		}

		ArrayList<String> returnedWords = new ArrayList<String>();
		String tempWord = "";
		char tempChar;
		int i;
		
		for( i=0; i<length ; i++){
			tempChar = input.charAt(i);
			if( tempChar!=wordSeparator && tempChar!='\n')
				tempWord+=tempChar;
			////Check and remove blank words
			else if( checkIsEmptyWord(tempWord, wordSeparator) ){
				//Blank word!
				tempWord = "";
			}else{
				returnedWords.add(tempWord);
				tempWord = "";
			}

		}

		//Add last word if there is NO newline at the end
		if( input.charAt( length-1 ) != '\n' ){
			//Don't add blank word
			if( !checkIsEmptyWord(tempWord, wordSeparator) ){
				returnedWords.add(tempWord);
			}
		}
		
		System.out.println("Extracted words: "+Arrays.toString(returnedWords.toArray()));

		return returnedWords;
	}
	
	
	/*
	 * Returns true if inputed word is empty
	 * Word is considered empty if:
	 * it has only characters 'check' or "\n" in it
	 */
	private static Boolean checkIsEmptyWord(String input, char check){
		int i;
		int length = input.length();
		char tempChar;
		
		if( length==0 )
			return true;
		
		for( i=0; i<length ; i++){
			tempChar = input.charAt(i);
			if(  tempChar != ' ' || tempChar != '\n')
				return false;	
		}
		
		return true;
	}
	
	
	/*
	 * Returns a string of all characters that are between char 'between'
	 * Example input is: word\\ab"prefix" ,and between is '"'
	 * then the returned characters are 'prefix'
	 * Duplicates are not eliminated (we called other method to do it)
	 * If char between doesn't exist it returns empty string ""
	 * If there is an odd number of chars between it returns empty string ""
	 */
	public static String getCharactersFromOptions(String text, char between){
		int i;
		int j;
		String temp = "";
		String tempChars = "";
		int length = text.length();
		int templength;

		if( !(text != null && !text.isEmpty()) )
			return "";

		int count = text.length() - text.replace( String.valueOf(between), "").length();
		if((count & 1) != 0 ){
			Main.log("There can't be an odd number of '"+between+"' characters!");
			return "";
		}

		//Set<String> tempChars = new LinkedHashSet<>();

		for( i=0; i<length; i++ ){
			if( text.charAt(i) == between ){
				i++;
				while( (i)<length && text.charAt(i) != between ){
					temp += text.charAt(i);
					i++;
				}
			}
			
			templength = temp.length();
			if( templength>0 )
				for( j=0; j<templength ;j++ )
					tempChars += temp.charAt(j);

			temp = "" ;
		}
			
		return removeDuplicatesString(tempChars);
	}
	
	
	/*
	 * Removes all duplicate chars from input string
	 */
	public static String removeDuplicatesString( String text ){
		char[] chars = text.toCharArray();
		Set<Character> charSet = new LinkedHashSet<Character>();
		for (char c : chars) {
		    charSet.add(c);
		}

		StringBuilder sb = new StringBuilder();
		for (Character character : charSet) {
		    sb.append(character);
		}
		
		return sb.toString();
	}
	
	
	private static void printArrayList(ArrayList<String> input){
		int i;
		int size = input.size();
		Main.log("Started printing working list:\n");
		
		for( i=0 ;i<size ;i++ )
			Main.log(input.get(i));
		
		Main.log("Ended printing working list.\n");
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
			System.out.println("[ERROR] Word length is larger then 10!");
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
	
	
	/*
	 * Prints 2 dim array
	 */
	private static void print2DimArray(String[][] current){
		for (String[] up: current) {
		    for (String down: up) {
		    	if(down!=null)
		    		System.out.println(down);
		    		//System.out.println();
		    }
		}
	}
	
	
	/*
	 * Returns the number of elements in the first index of 2 dimensional array
	 */
	private static int getLen1(String aray[][]){
		int len = 0;
		 for (int i=0;i<aray.length;i++)
			 if(aray[i][0]!=null)
					len++;
				else
					break;
		return len;
	}
		
		
	/*
	 * Returns the number of elements in the second index of 2 dimensional array
	 */
	private static int getLen2(String aray[]){
		int len = 0;
		for(String s: aray)
			if(s!=null)
				len++;
			else
				break;
		return len;
	}
	

		
	/*
	 * Returns text from file
	 */
	public static  String readTextFromFile(String filename){
		BufferedReader br = null;
		InputStreamReader isr = null;
		InputStream is = null;

		try {
			
			is = Info.class.getResourceAsStream(filename);
		    isr = new InputStreamReader(is);
		    br = new BufferedReader(isr);
			
			String sCurrentLine;
			StringBuilder sb = new StringBuilder();
			
			while ((sCurrentLine = br.readLine()) != null) {
				sb.append(sCurrentLine);
		        sb.append(System.lineSeparator());
			}
			
			return sb.toString();
			
		}catch (Exception e) {
			return e.toString();
		}finally {
			try {
				if (br != null)
					br.close();
				
				if (isr != null)
					isr.close();
					
				if (is != null)
					is.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
	/*
	 * Convert List<List<String>> to String[][] 
	 * Only temporal solution until method combinations2d is reworked so it can work with lists instead of arrays
	 */
	
	public static String[][] convertListToStringArray( List<List<String>> theStrings ){
		//List<List<String>> theStrings ... coming from somewhere
		String[][] stringsAsArray = new String[theStrings.size()][];
		for (int i=0; i<theStrings.size();i++) {
		  List<String> aList = theStrings.get(i);
		  stringsAsArray[i] = aList.toArray(new String[aList.size()]);
		}
		return stringsAsArray;
	}
	
}
