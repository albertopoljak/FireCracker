package main.methods;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import main.Main;

public class ProcessInput {

	
	
	/*
	 * Extract inputed words into ArrayList
	 * Recognizes words based on variable wordSeparator (newline is considered as word separator.)
	 * Blank words are eliminated
	 * Duplicates are not eliminated
	 */
	//Function is not called, duplicate?
	public static ArrayList<String> extractInputWords(String input, char wordSeparator){
		int length = input.length();

		if( !(input!=null) || length==0 ){
			Log.write("Input is empty, nothing to return!");
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
		
		Log.writeDebug("Extracted words: "+Arrays.toString(returnedWords.toArray()) );

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
			Log.write("There can't be an odd number of '"+between+"' characters!" , 'E' );
			return "";
		}

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
	private static String removeDuplicatesString( String text ){
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
	
}
