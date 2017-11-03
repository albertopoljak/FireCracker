package main.methods;
import java.util.ArrayList;
import java.util.List;
import main.Main;

public class WordOptions {

	private WordOptions(){};
	
	/*
	 * Build options
	 * optionString is already checked for empty in the previous method
	 * String in first index of finalSubOptions is the option while rest are subOptions
	 */
	public static void extractOptions( List<List<String>> inputList , int index , String baseString, String[] subOptions ){
		
		if( subOptions.length==0 ) {
			Main.txtrLog.append("Options have been found for word "+baseString+" but program couldn't read them!\n"
					+ "(length!>0)" , 'W');
			return;
		}
		
		/*
		 * Now call functions based on found options and their sub-options
		 */
		
		switch (subOptions[0]) {
        case "b":
        	if( subOptions.length>1 ){
        		Main.txtrLog.append("Option 'b' can't have suboptions! ("+subOptions[1]+" etc...)" , 'E');
        	}else{
        		ArrayList<String> temp = subsetsCharacters(baseString);
        		for(int i=0; i<temp.size();i++)
        			inputList.get(index).add( temp.get(i) );
        	}
        	break;
        case "l":
        	if( subOptions.length>1 ){
        		Main.txtrLog.append("Option 'l' can't have suboptions! ("+subOptions[1]+" etc...)" , 'E');
        		break;
        	}else{
        		inputList.get(index).add(baseString);
        		inputList.get(index).add( baseString.toLowerCase() );
        	}
        	break;
        case "u":
        	if( subOptions.length>1 ){
        		Main.txtrLog.append("Option 'u' can't have suboptions! ("+subOptions[1]+" etc...)" , 'E');
        		break;
        	}else{
        		inputList.get(index).add(baseString);
        		inputList.get(index).add( baseString.toUpperCase() );
        	}
        	break;
        case "ip":
        	if( subOptions.length<=1 ){ //first index is base option 
        		Main.txtrLog.append("Option 'ip' must have at least one suboption!" , 'E');
        		break;
        	}else{
        		inputList.get(index).add(baseString);
        		try{
        			for(int i=1;i<subOptions.length;i++){
        				inputList.get(index).add( inverseCharAtX( baseString, Integer.parseInt( subOptions[i] ) ) );
        			}
        		}catch(NumberFormatException e){
        			Main.txtrLog.append("Suboption for option ip must be a number! "+e, 'E');
        			break;
        		}
        	}
        	break;
        case "ab":
        	if( subOptions.length<=1 ){ //first index is base option {
        		Main.txtrLog.append("Option 'ab' must have at least one suboption!" , 'E');
        		break;
        	}
        	else if(baseString.length()<1){
        		Main.txtrLog.append("There is no base string for option 'ab'!" , 'E');
        		break;	
        	}else{
        		inputList.get(index).add(baseString);
        		for(int i=1;i<subOptions.length;i++){
        			inputList.get(index).add( subOptions[i] + baseString );
        		}
			}
        	break;
        case "ae":
        	if( subOptions.length<=1 ){ //first index is base option {
        		Main.txtrLog.append("Option 'ae' must have at least one suboption!" , 'E');
        		break;
        	}
        	else if(baseString.length()<1){
        		Main.txtrLog.append("There is no base string for option 'ae'!" , 'E');
        		break;	
        	}else{
        		inputList.get(index).add(baseString);
        		for(int i=1;i<subOptions.length;i++){
        			inputList.get(index).add( baseString + subOptions[i] );
        		}
			}
        	break;
        default:
        	Main.txtrLog.append("Can't find option '" +subOptions[0]+ "'" , 'E');
		}
		
		
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
			Main.txtrLog.append("X has to be higher that 0!", 'E');
			return null;
		}else{
			Main.txtrLog.append("Can't use function \\ip"+x+" , not that much chars in word or the word is empty!", 'E');
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
	private static ArrayList<String> subsetsCharacters( String s ) {
		ArrayList<String> generatedWords = new ArrayList<String>(); 
		int length = s.length();
			
		if( length>12 ){
			Main.txtrLog.append("Word length is larger then 10!", 'E');
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
