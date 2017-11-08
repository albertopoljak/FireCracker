package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.swing.JTextPane;

public class InputConsole extends JTextPane {
	private String newLine = System.getProperty("line.separator");
	
	/*
	 * Extract inputed words into ArrayList
	 * Recognizes words based on variable wordSeparator (newline is considered as word separator.)
	 * Blank words are eliminated
	 * Duplicates are not eliminated
	 * String input can't be null
	 */ 
		public ArrayList<String> extractInput(String input ,char wordSeparator){
			ArrayList<String> returnedWords = new ArrayList<String>();
			String tempWord = "";
			char currentChar;
			int inputLength = input.length();

			if (inputLength == 0) {
				Main.txtrLog.append("Input is empty!" , 'W');
				return null;
			}
			
			
			for (int i=0; i<inputLength; i++) {
				currentChar = input.charAt(i);
				if (currentChar!=wordSeparator && !compareCharAndStringForNewline(currentChar, newLine) )
					tempWord+=input.charAt(i);
				else{
					if (tempWord.length()>0)
						returnedWords.add(tempWord);
					else
						Main.txtrLog.appendDebug("Skipping empty word (you probably typed word separator twice)..." );
					
					tempWord = "";
				}
			}
			
			//Add last word if there is NO newline at the end
			if ( !compareCharAndStringForNewline( input.charAt( inputLength-1 ) , newLine) )
				returnedWords.add(tempWord);
			
			Main.txtrLog.appendDebug("Extracted input: " + Arrays.toString(returnedWords.toArray()) );
				
			return returnedWords;
		}
		
		private boolean compareCharAndStringForNewline( char charInput , String stringInput ){
			String temp = Character.toString(charInput);
			//Check if contains because newline(stringInput) can be 2 chars (on windows \r\n)
			if ( stringInput.contains(temp)){
				return true;
			}else{
				return false;
			}
		}
		
		
		/*
		 * Returns a string of all characters that are between char 'between'
		 * Example input is: word\\ab"prefix" ,and between is '"'
		 * then the returned characters are 'prefix'
		 * Duplicates are not eliminated (we called other method to do it)
		 * If char between doesn't exist it returns empty string ""
		 * If there is an odd number of chars between it returns empty string ""
		 */
		public static String getCharactersFromOptionsS(String text, char between){
			int i;
			int j;
			String temp = "";
			String tempChars = "";
			int length = text.length();
			int tempLength;

			if ( !(text != null && !text.isEmpty()) )
				return "";

			int count = text.length() - text.replace( String.valueOf(between), "").length();
			if ( (count & 1) != 0 ){
				Main.txtrLog.append("There can't be an odd number of '"+between+"' characters!" , 'E' );
				return "";
			}

			for ( i=0; i<length; i++ ){
				if ( text.charAt(i) == between ){
					i++;
					while ( (i)<length && text.charAt(i) != between ){
						temp += text.charAt(i);
						i++;
					}
				}
				
				tempLength = temp.length();
				if ( tempLength>0 )
					for ( j=0; j<tempLength ;j++ )
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
