package main.methods;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import main.Main;
import main.Settings;

public class ProcessInput {

	private ProcessInput(){};
	private static char combiningSeparator = Settings.wordCombinator ;
	private static char optionSeparator = Settings.optionSeparator;
	
	/*
	 * Build words from options
	 * NOTE:
	 * Total number of generated words is: a * b
	 * Example: Input is {{"sad","SAD"},{"najebo","NAJEBO"},{"sam","SAM"},{"test","TEST", "Test", "ayy"}}
	 * a would then be 2*2*2*4 = 32 
	 * b would be 4! (1*2*3*4) because we have 4 blocks (4 words that make a sentence)
	 * Total = 768
	 */	
		
	//int Y = inputWords.size(); daje nullPointer exception ako je input prazan		
	public static List<List<String>> buildWords( ArrayList<String> inputWords ){
		int i;
		int X;
		int Y = inputWords.size();
		List<List<String>> tempOutput = new ArrayList<List<String>>();
		updateSeparators();
		
		//Add Y words and start adding words and their options
		for( i=0; i<Y; i++){
			tempOutput.add(new ArrayList<String>());
			extractWords( tempOutput, i, inputWords.get(i).toString() );
		}
		
		Main.txtrLog.appendDebug( "Base words that were extracted from input(without their options):" );
		if( Settings.chckbxDebugMode.isSelected() ){
			long tempSize = Helpers.totalNumOfElementsIn2DList(tempOutput, Y);
			if( tempSize<50 )
				for( i=0; i<Y; i++){
					for (String value : tempOutput.get(i)) {
						Main.txtrLog.appendDebug( (i+1) + ":" + value );
					}
				}
			else
				Main.txtrLog.appendDebug( "Too many base words to display. I counted "+tempSize+" words.");
		}
		
		return tempOutput;
	}
	
	
	
	
	/*
	 * Extract words from string
	 */
	
	public static void extractWords( List<List<String>> inputList , int index , String extractString ){
		String baseString;
		String optionString;
		int indexCombining = extractString.indexOf(combiningSeparator);

		if( indexCombining!=-1 ){
			/*
			 * There is combining separator in string so we will break the word
			 * in 2 parts and call this function again with each part
			 */
			Main.txtrLog.appendDebug("Found combining separator in string: " + extractString );
		   String tempInput = extractString ;
		   StringBuilder temp = new StringBuilder();
		   for( int i=0; i<tempInput.length(); i++ ){
			   if( tempInput.charAt(i)==combiningSeparator ){
				   extractWords( inputList, index, temp.toString() );
				   temp = new StringBuilder();

			   }else if( i==tempInput.length()-1 ){
				   temp.append( tempInput.charAt(i) );
				   extractWords( inputList, index, temp.toString() );
				   //temp = new StringBuilder();
			   }else{
				   temp.append( tempInput.charAt(i) );
			   }
		   }
		   return;
		}else{
			/*
			 * There is NO combining separator in word meaning word is single
			 * Now we look if word has additional options or not
			 */
			int indexOption = extractString.indexOf(optionSeparator);
			if( indexOption!=-1 ){
				/*
				 * There are options in the word so we will have to generate new words
				 * based on those options and base string (which is an original word)
				 */
				baseString = extractString.substring(0, indexOption);
				optionString = extractString.substring(indexOption);

				/*
				 * Split option string to multiple parts then call function for every option part
				 * We used optionSeparatorDouble because option separator is a double char in the program
				 */
				String optionSeparatorDouble = optionSeparator+""+optionSeparator;
				String[] word = optionString.split(optionSeparatorDouble);
				word = removeEmptyWordsFromStringArray(word);
				
				Main.txtrLog.appendDebug("Printing option array for word: '" + extractString + "' in format (ID:option) :" );
				for(int k =0; k<word.length;k++)
					Main.txtrLog.appendDebug( k + ": " + word[k] );
				
					for(String optionPart: word){
						/*
						 * Eliminate empty words produced by optionString.split
						 */
							/*
							 * Before calling the option method first extract sub-options
							 */
						Main.txtrLog.appendDebug("Spliting:'"+optionPart+"' to suboptions!" );
							String[] subOptions = optionPart.split( ""+optionSeparator );
							/*
							 * Now remove empty words from sub-options produced by optionString.split
							 */
							subOptions = removeEmptyWordsFromStringArray(subOptions);
							Main.txtrLog.appendDebug("Printing suboptions (ID:suboption) where ID=0 means base option:" );
							for(int k =0; k<subOptions.length;k++)
								Main.txtrLog.appendDebug(k+":"+subOptions[k] );
							/*
							 * String in first index of finalSubOptions is the option while rest are subOptions
							 */
							WordOptions.extractOptions( inputList, index, baseString, subOptions );
					}
				return;
			}else{
				/*
				 * There are no options in word so we just add it to the list
				 */
				Main.txtrLog.appendDebug("No options in word:"+extractString );
				inputList.get(index).add(extractString);
				return;
			}
		}
		
	}




	/*
	 * Eliminate empty words in string array
	 * Words are considered empty if they are null or have 0 characters
	 */
	
	private static String[] removeEmptyWordsFromStringArray(String[] input){
		List<String> list = new ArrayList<String>(Arrays.asList(input));
		list.removeAll(Arrays.asList("", null));
		return list.toArray(new String[0]);
	}
	
	
	private static void updateSeparators(){
		combiningSeparator = Settings.wordCombinator ;
		optionSeparator = Settings.optionSeparator;
	}
	

}
