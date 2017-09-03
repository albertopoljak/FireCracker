import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;
import java.awt.Color;

public class MainProgram {
	//Basic elements
	private static JFrame frmDecypherInStyle;
	private static String basePath = new File("").getAbsolutePath();
	private static JTextArea txtWords;
	private static JTextArea txtrLog;
	private static JLabel textCurrentMemUsage;
	private static PrintWriter out;
	private static Timer timer;
	//Program variables
	private static String[] inputWords; //Used for storing words from text input (includes options example \\l , \\u ...) 
	public static int numberOfWords = 0; //num of words in input, used for stopping program if words = 0
	private static ArrayList<String> lowerCaseWords = new ArrayList<String>(); //used with \\l
	private static ArrayList<String> upperCaseWords = new ArrayList<String>(); //used with \\u
	private static ArrayList<String> workingWords = new ArrayList<String>(); //words to which we are finding subsets
	private static ArrayList<String> generatedWords = new ArrayList<String>(); 
	//Settings variables
	private static int updateVariablesInterval = 3;
	private static char wordSeparator = ' ';
	private static String savePath;
	private static boolean measureVariables = true;
	private static boolean lowMemory = true;
	
	//Test variables
	private static long test =0;
	private static ArrayList<String> testList = new ArrayList<String>();
	//private static String[][] a = new String[4][3]; //12 is maximum number of words, 5 is maximum number of word synonims
	private static String[] result = new String[100];
	
	
	//Test function
	private static void doo(){
		/*a[0][0] = "sad";
		a[0][1] = "Sad";
		a[0][2] = "SAD";
		//
		a[1][0] = "najebo";
		//
		a[2][0] = "sam";
		a[2][1] = "Sam";
		a[2][2] = "SAM";*/
		//
		//a[3][0] = "d";
		//a[3][1] = "";
		///////////////
		
		
		long startTime;
		long endTime;
		long memoryStart;
		long memoryEnd;
		long etime = 0;
		long emem = 0;
		
		constructString(txtWords.getText());
		//System.out.println( getLen1(a) );
		//System.out.println(getLen2(a[0]));
		//System.out.println(getLen2(a[3]));
		
		/*
		String [][] g = {{"sad","SAD","Sad"},{"najebo","NAJEBO","Najebo"},{"sam","SAM","Sam"},{"od","OD","Od"},{"vas","VAS","Vas"}};
		//String [][] h = {{"1","2","3","4","5","6","7","8","9"},{"a","1a","2a","2a","2a","2a","2a","2a","2a"},{"b","1b","2b"},{"c","1c","2c"},{"d","1d","2d"},{"e","1e","2e"},{"f","1f","2f"},{"h","1h","2h","3h","4h","5h"},{"i","1i","2i","3i","4i","5i"},{"p","1p","2p","3p","4p","5p"}};
		//String [][] h = {{"1","2","3","4","5","6","7","8","9"},{"1","2","3","4","5","6","7","8","9"},{"1","2","3","4","5","6","7","8","9"},{"1","2","3","4","5","6","7","8","9"},{"1","2","3","4","5","6","7","8","9"},{"1","2","3","4","5","6","7","8","9"},{"1","2","3","4","5","6","7","8","9"},{"1","2","3","4","5","6","7","8","9"},{"1","2","3","4","5","6","7","8","9"}};
		String [][] h = {{"1","2","3","4","5","6","7","8","9"},{"1","2","3","4","5","6","7","8","9"},{"1","2","3","4","5","6","7","8","9"},{"1","2","3","4","5","6","7","8","9"}};
		
		//test for this method https://www.java-forums.org/advanced-java/81134-optimise-recursive-method-prints-all-possible-rows-2d-array.html
		int numOfTimes = 3;
		for(int i=0; i<numOfTimes; i++){
			memoryStart = getMemoryUsageLong();
			startTime = System.nanoTime();
			combo(h);
			endTime = System.nanoTime();
			memoryEnd = getMemoryUsageLong();
			//System.out.println("Time needed for previous: "+(endTime - startTime)/1000000+"ms\n");
			etime+= ((endTime - startTime)/1000000);
			emem+=(memoryEnd - memoryStart);
			//System.out.println("Memory difference: "+(memoryEnd - memoryStart)+" KB\n");
			//System.out.println("Words combinations:"+test);
		}
		System.out.println("Time needed: "+etime+"ms\n");
		System.out.println("Avg Time needed: "+etime/numOfTimes+"ms\n");
		System.out.println("Memory difference: "+emem/numOfTimes+" KB\n");
		System.out.println("Words combinations:"+test/numOfTimes);
		*/
		//System.out.println("NOVO");
		//permute2DimArray(a);



		//System.out.println(Arrays.toString(combinations(a).toArray()));
		}
	
	
	
	/**
	 * Produce a List<String> which contains every combination which can be
	 * made by taking one String from each inner String array within the
	 * provided two-dimensional String array.
	 * @param twoDimStringArray a two-dimensional String array which contains
	 * String arrays of variable length.
	 * @return a List which contains every String which can be formed by taking
	 * one String from each String array within the specified two-dimensional
	 * array.
	 */
	public static ArrayList<String> combinations(String[][] twoDimStringArray) {
	    // keep track of the size of each inner String array
	    int sizeArray[] = new int[twoDimStringArray.length];

	    // keep track of the index of each inner String array which will be used
	    // to make the next combination
	    int counterArray[] = new int[twoDimStringArray.length];

	    // Discover the size of each inner array and populate sizeArray.
	    // Also calculate the total number of combinations possible using the
	    // inner String array sizes.
	    int totalCombinationCount = 1;
	    for(int i = 0; i < twoDimStringArray.length; ++i) {
	        sizeArray[i] = twoDimStringArray[i].length;
	        totalCombinationCount *= twoDimStringArray[i].length;
	    }

	    // Store the combinations in a List of String objects
	    ArrayList<String> combinationList = new ArrayList<String>(totalCombinationCount);

	    StringBuilder sb;  // more efficient than String for concatenation

	    for (int countdown = totalCombinationCount; countdown > 0; --countdown) {
	        // Run through the inner arrays, grabbing the member from the index
	        // specified by the counterArray for each inner array, and build a
	        // combination string.
	        sb = new StringBuilder();
	        for(int i = 0; i < twoDimStringArray.length; ++i) {
	        	if( twoDimStringArray[i][counterArray[i]]!=null ) //fix this so that it doesnt use cpu if its null, this is temporary
	            sb.append(twoDimStringArray[i][counterArray[i]]);
	        }
	        combinationList.add(sb.toString());  // add new combination to list

	        // Now we need to increment the counterArray so that the next
	        // combination is taken on the next iteration of this loop.
	        for(int incIndex = twoDimStringArray.length - 1; incIndex >= 0; --incIndex) {
	            if(counterArray[incIndex] + 1 < sizeArray[incIndex]) {
	                ++counterArray[incIndex];
	                // None of the indices of higher significance need to be
	                // incremented, so jump out of this for loop at this point.
	                break;
	            }
	            // The index at this position is at its max value, so zero it
	            // and continue this loop to increment the index which is more
	            // significant than this one.
	            counterArray[incIndex] = 0;
	        }
	    }
	    return combinationList;
	}
	
	//Helper method for permute2DimArray. Returns the number of elements in the first index of 2 dimensional array
	private static int getLen1(String aray[][]){
		int len = 0;
		 for (int i=0;i<aray.length;i++)
			 if(aray[i][0]!=null)
					len++;
				else
					break;
		return len;
	}
	
	//Helper method for permute2DimArray. Returns the number of elements in the second index of 2 dimensional array
	private static int getLen2(String aray[]){
		int len = 0;
		for(String s: aray)
			if(s!=null)
				len++;
			else
				break;
		return len;
	}
	
	//Prints 2 dim array
	private static void print2DimArray(String[][] current){
		for (String[] up: current) {
		    for (String down: up) {
		    	if(down!=null)
		    		System.out.println(down);
		    		//System.out.println();
		    }
		}
		
	}
	
	
	
	
	
	/**
	 * Launch new screen.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainProgram window = new MainProgram();
					window.frmDecypherInStyle.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	/**
	 * Create screen elements.
	 */
	public MainProgram() {
		initialize();
		//printMemoryUsageInInterval(updateVariablesInterval);
		autoMemoryDetection();
		
		//test
		doo();
		System.gc();
		//END TEST
	}

	/*//Replaced with method EXTRACT
	private static void getWordsToString(){
		if( txtWords.getText().length()>0 ){	
			inputWords = txtWords.getText().split("\\s+");
			numberOfWords = inputWords.length;
			txtrLog.append("Number of words: " + numberOfWords + "\n");
			//Now we have all the words from text input, including their options \\
			//Now we extract those options
			for( int i=0; i<inputWords.length; i++ ){
				if( inputWords[i].contains("\\l") ){
					//Delete option
					inputWords[i] = inputWords[i].replace("\\l", "");
					//Add this original string to working words set
					workingWords.add(inputWords[i]);
					//Add this original string to array (later we will find this string and replace it with bottom string)
					lowerCaseWords.add(inputWords[i]);
					//Add modified string to array
					lowerCaseWords.add( inputWords[i].toLowerCase() );
				}else if( inputWords[i].contains("\\u") ){
					inputWords[i] = inputWords[i].replace("\\u", "");
					workingWords.add(inputWords[i]);
					upperCaseWords.add(inputWords[i]);
					upperCaseWords.add( inputWords[i].toUpperCase() );
				}else{
					//No option added
					workingWords.add(inputWords[i]);
				}
			}
			
		}else
			txtrLog.append("[ERROR] Input is empty! \n");
	};*/
	
	private static void getOptionFromString(String word){
		//Convert string to char array
		// char[] array = new char[word.length()];
	     //  for (int i = 0; i < word.length(); i++)
	    //	   array[i] = word.charAt(i);
		
		
		//for( int i=0; i<word.length() ; i++ ){
		///	if( array[i]=='\' && (i+1)<=word.length() )
		//		return null;
		//}
		
	}
	
	/*Unnecesary , just use .toLower/Upper in program, and before that check if words=0
	private String getLowerCaseString(String word){
		if( numberOfWords == 0 )
			return null;
		else{
			return word.toLowerCase();
        }
	}
	
	private String getUpperCaseString(String word){
		if( numberOfWords == 0 )
			return null;
		else{
			return word.toUpperCase();
        }
	}*/
	
////////////////////////////////////////////////////////////////////////////////////////
//Working Methods\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
////////////////////////////////////////////////////////////////////////////////////////

	
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
	
	
	//3 methods below, where they return null that is to avoid duplicate entries
	private static String inverseFirstChar(String word){
		if( word.length()>0 ){
			if ( isCharLowerCase(word.charAt(0)) ) {
				return word.substring(0, 1).toUpperCase() + word.substring(1);
			}else if ( isCharUpperCase(word.charAt(0)) ) {
				return word.substring(0, 1).toLowerCase() + word.substring(1);
			}else
				return null; //for numbers
		}else
			return null;
	}
	
	
	//Newline \n shouldn't be in string word
	private static String inverseLastChar(String word){
		if( word.length()>0 ){
			if ( isCharLowerCase(word.charAt( word.length()-1) ) ) {
				return word.substring( 0, word.length()-1 )  + word.substring( word.length()-1, word.length() ).toUpperCase();
			}else if ( isCharUpperCase(word.charAt( word.length()-1 ) ) ) {
				return word.substring( 0, word.length()-1 )  + word.substring( word.length()-1, word.length() ).toLowerCase();
			}else
				return null; //for numbers
		}else
			return null;
	}
	
	
	private static String inverseCharAtX( String word , int x ){
		if( word.length()>0 && word.length() >= x && x>0){
			if ( isCharLowerCase(word.charAt( x-1 ) ) ) {
				return word.substring( 0, x-1 )  + word.substring( x-1 , x ).toUpperCase() + word.substring( x, word.length() );
			}else if ( isCharUpperCase(word.charAt( x-1 ) ) ) {
				return word.substring( 0, x-1 )  + word.substring( x-1, x ).toLowerCase() + word.substring( x, word.length() );
			}else
				return null; //for numbers
		}else if(x<1){
			txtrLog.append("[ERROR] X has to be higher that 0!\n");
			return null;
		}else{
			txtrLog.append("[ERROR] Can't use function \\ip"+x+" , not that much chars in word!\n");
			return null;
		}
	}
	
	
	
	private static long getMemoryUsageLong(){
		return ( Runtime.getRuntime().totalMemory()/1024/1024 );
	}
	
	
	private static void printMemoryUsageInInterval( int intervalSeconds){
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			  @Override
			  public void run() {
				  textCurrentMemUsage.setText("MB: " + getMemoryUsageLong());
			  }
			}, 0, intervalSeconds*1000);

	}
	
	
	//Method to clear list (.clear leaves memory footprint for the largest element, meaning N*nullElements)
	private static ArrayList<String> clearList() {
		   ArrayList<String> newList = new ArrayList<String>();
		   return newList;
	}
		
	//Copied from Word List Sorter. Extract inputted words into ArrayList, recognises words based on variable wordSeparator
	private static ArrayList<String> extractInputWords(String input){
		ArrayList<String> returnedWords = new ArrayList<String>();
		String tempWord = "";
		if( input!=null && input.length()>0 ){
			for( int i=0; i<input.length() ; i++){
				if( input.charAt(i)!=wordSeparator)
					tempWord+=input.charAt(i);
				else{
					returnedWords.add(tempWord);
					tempWord = "";
				}
			}
			
			//Add last word if there is NO newline at the end
			if( input.charAt( input.length()-1 ) !='\n' )
				returnedWords.add(tempWord);
			System.out.println("Extracted words: "+Arrays.toString(returnedWords.toArray()));
			return returnedWords;
			
		}else{
			System.out.println("Input is empty, nothing to return!");
			return null;
		}
	}	
	
	private static String[][] extractWordsAndOptions(ArrayList<String> input){
		System.out.println("WTD");
		//Used for storing words and its options
		ArrayList<ArrayList<String>> listOfLists = new ArrayList<ArrayList<String>>();
		//Used for storing temp word and options
		 ArrayList<String> tempList = new ArrayList<String>();
		//Initialize it
		//for(int i = 0; i < input.size(); i++)  {
	    //    listOfLists.add(new ArrayList<String>());
	    //}
		 tempList.add("a1");
		 tempList.add("a2");
		 tempList.add("a3");
		 listOfLists.add(tempList);
		 tempList.clear();
		 tempList.add("b1");
		 tempList.add("b2");
		 tempList.add("b3");
		 listOfLists.add(tempList);
		 //tempList.clear();
		
		//print
		System.out.println(Arrays.toString(listOfLists.toArray()));
		System.out.println(Arrays.toString(tempList.toArray()));
		
		//retrieve
		//for(int i=0;i<listOfLists.size();i++){
		//
		//	   String[] myString= new String[4]; 
		//	   myString=listOfLists.get(i);
		//	   for(int j=0;j<myString.length;j++){
		//	      System.out.print(myString[j]); 
		//	   }
		//	   System.out.print("\n");
		//
		//	}
		
		
		
		return null;
	}	
	
	
	//Functions for settings
	public static void changeWordSeparator(char newSeparator){
		wordSeparator = newSeparator;
	}
	
	public static void changeUpdateVariable(int newInterval){
		updateVariablesInterval = newInterval;
		timer.cancel();
		if(newInterval!=0)
			printMemoryUsageInInterval(newInterval);
	}
	
	public static void changeSavePath(String path){
		savePath = path;
	}
	
	public static void setMeasureVariable(boolean state){
		measureVariables = state;
	}
	
	public static void autoMemoryDetection(){
		long maxMemory = Runtime.getRuntime().maxMemory()/1024/1024;
		System.out.println("Max memory"+maxMemory+"mb");
		if(maxMemory<2000){
			lowMemory = true;
			txtrLog.append("Program is set to run on low memory (less that 2000mb).\n"
					+ "This will affect program execution time but will reduce memory usage.\n"
					+ "If the memory reduction is not enough and the program eats all the memory it will crash.\n"
					+ "Run in 64bits to increase avaiable memory size (works only if you have more than 2gb RAM)\n");
		}else
			lowMemory = false;
	}
	
	public static void setLowMemoryState(boolean state){
		lowMemory = state;
	}

		
	//Returns all characters that are inside quotes. Example you have set option: word\\ab"prefix"
	//Then characters that will be reserved and returned are 'prefix'
	//For word\\ab"test" it will return 'tes' (no duplicates) -FIX THIS CURRENTLY RETURNS DUPLICATES
	//If quotes don't exist it returns empty string ""
	public static String getCharactersFromOptions(){
		String text = txtWords.getText();
		char between = '"';
		//
		String temp = "";
		//Set<String> tempChars = new LinkedHashSet<>();
		String tempChars ="";
		if( text!=null & text.length()>0 ){
			for(int i=0; i<text.length(); i++){
				if( text.charAt(i) == between )
					//Find all chars between  selected char and add them to temp string. 
					//Only end of string and char "between" will stop this method
					while( (i+1)<text.length() && text.charAt(i+1) != between ){
						i++;
						temp+= text.charAt(i);
					}
				//Now add chars from temp string to set (set because to avoid duplicates)
				if( temp.length()>0)
					for( int j=0; j<temp.length() ;j++)
						tempChars+= String.valueOf(temp.charAt(j) );
				temp="";
			}
			//Now return that string set in form of single string
			return tempChars.toString();
		}else
		    return "";
	}
	
////////////////////////////////////////////////////////////////////////////////////////
//Testing Methods\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
////////////////////////////////////////////////////////////////////////////////////////
	
	//Source https://www.java-forums.org/advanced-java/81134-optimise-recursive-method-prints-all-possible-rows-2d-array.html
	//You have to pass a clean string array, with no nulls
	//Complexity is ?? and apparently n is not neededd?
	//Example input ("test","TEST)("may","MAY")	will output these combinations: 
	/*
	 * testmay
	 * testMAY
	 * TESTmay
	 * TESTMAY
	 */
		
	public static void combo(String [][] g) {
		int n = 1;
		for (int j = 0; j < g.length; j++) {
			n *= g[j].length;
		}
		System.out.println("g lenghth je:"+g.length);
		int i[] = new int[g.length];
		String tempWords[] = new String[g.length];
		int tempPos = 0;
		for (int k = 0; k < n; k++) {
			for (int rr = 0; rr < g.length; rr++) {
				//System.out.print(g[rr][i[rr]] + " ");
				tempWords[tempPos] = g[rr][i[rr]];
				tempPos++;
			}
			// System.out.println(Arrays.toString(tempWords));
			testList.add(Arrays.toString(tempWords));
			//heapPermutation(tempWords , g.length, g.length);
			tempWords = new String[g.length];  
			tempPos = 0;
			test++;
			// System.out.println();
			i[g.length-1]++;
			for (int j = g.length-1; j > 0; j--) {
				if (i[j] >= g[j].length) {
					i[j] = 0;
					i[j - 1]++;
				} else
					break;
			}
		
		}
	}
		
		
		//remove word so only options remain in this function (so that the you can type \\ in the word)
	private static ArrayList<String> constructString(String input){
		System.out.println("Word:"+input);
		boolean b = false;		//for \\b and \\b\number option, This will create all combinations of strings (123 = 123, 231, 321 etc)
		int bOption = 0;
		boolean l = false;		//for "Use the word in its lowercase" option
		boolean u = false;		//for "Use the word in its uppercase" option
		boolean ipx = false;	//If you want to invert letter at specific position , ipX where x is position
		StringBuilder ipxPositions = new StringBuilder();
		boolean ab = false;		//adds specific string to beggining of the word, ab"string"
		boolean ae = false;		//adds specific string to the end of the word, ae"string"
		//Universal options
		boolean sb = false;		//Add specific string %S at beginning of each generated word
		boolean sbc = false;	//Add specific string %S at beginning of each generated word while keeping original words
		boolean se = false;		//Add specific string %S at the end of each generated word
		boolean sec = false;	//Add specific string %S at the end of each generated word while keeping original words
		//Fail safe variable
		boolean failed = false;
		//Check if options exist
		boolean optionExists = false;
		
		
		//Check for options, have to make function manually in case of duplicate options
		StringBuilder in = new StringBuilder(input);
		for(int i = 0; i<in.length(); i++){
			if( in.charAt(i) == '\\' && i+1<in.length() && in.charAt(i+1) == '\\' ){
				//remove \\
				in.deleteCharAt(i);
				in.deleteCharAt(i);
				if( i<in.length() ){
					//Check options 
					if( in.charAt(i) == 'b' ){
						b = true;
						//Check number options (\\b\0)
						if( i+1<in.length() && in.charAt(i+1) == '\\' ){ 
							if(i+2<in.length() && Character.isDigit(in.charAt(i+2))){
									System.out.println("yaTEST");
									StringBuilder tempNumber = new StringBuilder();
									tempNumber.append(in.charAt(i+2));
									//Check for second digit
									if(i+3<in.length() && Character.isDigit(in.charAt(i+3)))
										tempNumber.append(in.charAt(i+3));
									//Convert string to number
									bOption = Integer.parseInt(tempNumber.toString());
							}else{
								System.out.println("Invalid number option for \\b! Option should look like: \\\\b\\x where x is a one or two digit number");
								failed = true;
								break;
							}	
						}else
							System.out.println("No number option for \\\\b found...");
					}else if( in.charAt(i) == 'l' ){
						l = true;
					}else if( in.charAt(i) == 'u' ){
						u = true;
					}else if( in.charAt(i) == 'i' ){
						if( i+1<in.length() && in.charAt(i+1) == 'p' ){
								if( i+2<in.length() && Character.isDigit(in.charAt(i+2)) ){
									ipx= true;
									ipxPositions.append(in.charAt(i+2));
									int k = 1;
									while(i+3+k<in.length() && in.charAt(i+2+k)==','){
											ipxPositions.append(in.charAt(i+3+k));
											//For two or more digits
											while( i+4+k<in.length() && Character.isDigit(in.charAt(i+4)) ){
												ipxPositions.append(in.charAt(i+4+k));
												k++;
											}
											k+=2;
									}
								}else{
									System.out.println("Invalid option ip! Enter positions! example \\\\ip1");
									failed = true;
									break;
								}	
						}else{
							System.out.println("Invalid option! Options with char i are: ipX");
							failed = true;
							break;
						}
							
					}else{
						System.out.println("Unrecognised option!");
						failed = true;
						break;
					}
						
				}else{
					System.out.println("Characters \\\\ were found but there is no option! ");
					failed = true;
					break;
				}
					
					
					
			}
		}
		
		//Fail safe check
		try{
			if(ipxPositions.toString()!=null && ipxPositions.toString().length()>0)
				Integer.parseInt(ipxPositions.toString());
		}catch(Exception e1){
			System.out.println("Invalid option ipX! Invalid character position input!"+e1);
			failed = true;
		}
		
		
		//test print
		if(!failed && (b||l||u||ipx||ab||ae) ){
			System.out.println("\nPrinting");
			if(b){
			System.out.println("b:"+b);
			System.out.println("bOption:"+bOption);
			}if(l)
			System.out.println("l:"+l);
			if(u)
			System.out.println("u:"+u);
			if(ipx){
			System.out.println("ip:"+ipx);
			System.out.println("ip:"+ipxPositions);
			}if(ab)
			System.out.println("ab:"+ab);
			if(ae)
			System.out.println("ae:"+ae);
		}else
			System.out.println("No options or failed to read them. Please input options correctly.");
		
		ArrayList<String> temp = new ArrayList<String>();
		temp.add("Test");
		temp.add("AAyy");
		
		//printArrayList(temp);
		return temp;
	}
		

	 //Complexity is N! where N is num of words
	//N is maximum resulting length of string, if its smaller then size.length duplicates will occur(n is just used in printing string)
	//size is number of words to include in permutation example input "abc","123","!?!" and size=2 will make permutations for "abc" and "123" 
	static void heapPermutation(String a[], int size, int n){
	        // if size becomes 1 then prints the obtained
	        // permutation
	        if (size == 1)
	        	printStringArray(a,n);
	 
	        for (int i=0; i<size; i++){
	            heapPermutation(a, size-1, n);
	 
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
	//Helper for heapPermutation
		static void printStringArray(String a[], int n){
			for (int i=0; i<n; i++)
				txtrLog.append(String.valueOf(a[i]));
			txtrLog.append("\n");
			test++;
		}
	 
	 
	 
	//Generates all subsets of inputed string WITHOUT duplicates
	//Char set more efficient than String set 
	//maxLength is number of character to permute , example ("1234",3) would only permute first 3 chars "123" creating 6 words 
	 //Complexity N! where N is num of chars
	public static void subsetsCharacters(String s, int maxLength) {
		if( maxLength>12 ){
			txtrLog.append("[ERROR] Word length is larger then 12! \n");
		}else if( maxLength <= s.length()){
	       char[] a = new char[maxLength];
	       for (int i = 0; i < maxLength; i++)
	           a[i] = s.charAt(i);
	       permChars(a, maxLength);
		}else
			txtrLog.append("Can't find permutations! Error in string length!\n");
	    }
	
	//Helper for subsetsCharacters
	private static void swapChars(char[] a, int i, int j) {
        char c = a[i];
        a[i] = a[j];
        a[j] = c;
    }
	
	//Main helper for subsetsCharacters
	private static void permChars(char[] a, int n) {
		if (n == 1) {
			//generatedWords.add( String.valueOf(a) );
			//System.out.println(String.valueOf(a));
			//test++;
		    return;
		}
		for (int i = n; i < a.length; i++) {
		    if (a[i] == a[n-1])
		        return;
		}
		for (int i = 0; i < n; i++) {
		    boolean duplicate = false;
		    for (int j = 0; !duplicate && j < i; j++)
		        duplicate = a[i] == a[j];
		    if (!duplicate) {
		    	swapChars(a, i, n-1);
		    	permChars(a, n-1);
		        swapChars(a, i, n-1);
		    }
		}
    }
	
	
	
	private static void generateInputListFromOptions(){
		for(int i=0;i<lowerCaseWords.size();i++){
			for(int j=0;j<upperCaseWords.size();j++){
				
			}
		}
		
	}
	
	
	private static void printArrayList(ArrayList<String> input){
		txtrLog.append("Started printing working list:\n");
		for(int i=0;i<input.size();i++)
			System.out.println(input.get(i));
		System.out.println("Ended printing working list.\n");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private static void initialize() {
		//Set up the window
			frmDecypherInStyle = new JFrame();
			frmDecypherInStyle.getContentPane().setBackground(Color.DARK_GRAY);
			frmDecypherInStyle.setResizable(false);
			frmDecypherInStyle.setTitle("Hackerman");
			frmDecypherInStyle.setBounds(100, 100, 780, 580);
			frmDecypherInStyle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frmDecypherInStyle.getContentPane().setLayout(null);
			
		//Add text label
			JLabel txtEnter = new JLabel();
			txtEnter.setForeground(Color.WHITE);
			txtEnter.setFont(new Font("Tahoma", Font.PLAIN, 11));
			txtEnter.setBounds(14, 11, 600, 18);
			txtEnter.setText("Enter set of possible words with space in between (char space can be changed in options):");
			txtEnter.setOpaque(false);
			frmDecypherInStyle.getContentPane().add(txtEnter);
			
		//Add text log in form of JTextArea
			txtrLog = new JTextArea();
			txtrLog.setBackground(Color.LIGHT_GRAY);
			txtrLog.append("Log:\n");
			txtrLog.setLineWrap(true);
			txtrLog.setEditable(false);
			txtrLog.setFont(new Font("Calibri", Font.PLAIN, 18));
			txtrLog.setBounds(20, 49, 399, 482);
			
		//Add Scroll Pane with text log in it
			JScrollPane sp = new JScrollPane(txtrLog);
			sp.setLocation(14, 240);
			sp.setSize(600, 300);
			frmDecypherInStyle.getContentPane().add(sp, BorderLayout.CENTER);
			
		//Add button that gets relative path and updates log window
			JButton btnGetSavePath = new JButton("Get save path");
			btnGetSavePath.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnGetSavePath.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					txtrLog.append("Save path location: "+basePath+"\n");
				}
			});
			btnGetSavePath.setBounds(624, 390, 140, 23);
			frmDecypherInStyle.getContentPane().add(btnGetSavePath);
			
		JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(14, 29, 750, 139);
			frmDecypherInStyle.getContentPane().add(scrollPane);	
			
		//Add JTextArea for inputting words
			txtWords = new JTextArea();
			txtWords.setForeground(Color.BLACK);
			txtWords.setBackground(Color.LIGHT_GRAY);
			scrollPane.setViewportView(txtWords);
			txtWords.setFont(new Font("Monospaced", Font.PLAIN, 12));
			txtWords.setLineWrap(true);
			txtWords.setToolTipText("");	
			
		//Add button that clears inputed words
			JButton btnClearWords = new JButton("Clear Words");
			btnClearWords.setForeground(Color.BLACK);
			
			btnClearWords.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnClearWords.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					//txtWords.setText("");
					txtWords.setText("");
				}
			});
			btnClearWords.setBounds(624, 6, 140, 20);
			//btnClearWords.setBackground(Color.red);
			//btnClearWords.setOpaque(true);
			//btnClearWords.setBorderPainted(true);
			//btnClearWords.setContentAreaFilled(false);
			//ImageIcon icon = new ImageIcon(basePath+"\\src\\btn1.png");
			//txtrLog.append("Save path location: "+basePath+"\\src\\btn1.png"+"\n");
			//btnClearWords.setIcon(icon);
			frmDecypherInStyle.getContentPane().add(btnClearWords);	
			
		//Add button that clears log
			JButton btnClearLog = new JButton("Clear Log");
			btnClearLog.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnClearLog.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					txtrLog.setText("");
					txtrLog.append("Log:\n");
				}
			});
			btnClearLog.setBounds(624, 420, 140, 22);
			frmDecypherInStyle.getContentPane().add(btnClearLog);
		
		//Add button that opens new window ("Info")
			JButton btnInfo = new JButton("Info");
			btnInfo.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnInfo.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					Info.newScreen();					
				}
			});
			btnInfo.setBounds(624, 449, 140, 22);
			frmDecypherInStyle.getContentPane().add(btnInfo);
					
		//Add button that ?
			JButton btnHack = new JButton("Hack!");
			btnHack.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnHack.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					try{
						//clear previous memory
						//if( numberOfWords>0 ){
							//numberOfWords = 0;
							//inputWords = new String[0];
							//generatedWords = clearList();
						//}

						
						//Start
						//txtrLog.append("[INFO]-->Hack in progress!\n");
						//getWordsToString();
						//printWorkingList();
						//generateInputListFromOptions();
						//printWorkingList();
						//generateLowerCaseCombinations();
						//charPermutationOfAllWords("","1234");
						//txtrLog.append("Permutation in progress!\n");
						


						ArrayList<String> inputWords = extractInputWords( txtWords.getText() );
						String[][] extractedWordsAndOptions = extractWordsAndOptions(inputWords);
						

//(extractWords( txtWords.getText() ));
							
							
						long startTime;
						long endTime;
						long memoryStart;
						long memoryEnd;
						
						//4th method
						/*
						txtrLog.append("4th method:\n");
						startTime = System.nanoTime();
						memoryStart = getMemoryUsageLong();
						out = new PrintWriter("filename.txt");
						//subsetsCharacters("123456789012", 12);
						int a[] = {1,2,3,4,5,6,7,8,9,0,1};
						String b[] = {"1","2","3","4"};
						heapPermutation( b, b.length,b.length);
						out.close();
						endTime = System.nanoTime();
						memoryEnd = getMemoryUsageLong();
						txtrLog.append("Time needed: "+(endTime - startTime)/1000000+"ms\n");
						txtrLog.append("Memory difference: "+(memoryEnd - memoryStart)+" KB\n");
						//
						//startTime = System.nanoTime();
						//txtrLog.append("1.5milionth element is:"+String.valueOf(generatedWords.get(1400000))+"\n");
						//endTime = System.nanoTime();
						//txtrLog.append("Time needed for finding element: "+(endTime - startTime)/1000000+"ms\n");
						
						//for(int i=0;i<generatedWords.size();i++)
						//	txtrLog.append( String.valueOf(generatedWords.get(i) )+"\n");
						txtrLog.append( "Generated words:"+test +"\n"); 
						*/
					}catch (Exception e1) {
						txtrLog.append("\n[ERROR]-->"+e1+"\n");
					}
				}
			});
			btnHack.setBounds(624, 507, 140, 33);
			frmDecypherInStyle.getContentPane().add(btnHack);
			
			JLabel txtrOptions = new JLabel();
			txtrOptions.setForeground(Color.WHITE);
			txtrOptions.setText("Universal options:");
			txtrOptions.setOpaque(false);
			txtrOptions.setFont(new Font("Tahoma", Font.PLAIN, 11));
			txtrOptions.setBounds(14, 179, 100, 18);
			frmDecypherInStyle.getContentPane().add(txtrOptions);
			
			JLabel lblMemoryUsage = new JLabel("Current RAM usage:");
			lblMemoryUsage.setForeground(Color.WHITE);
			lblMemoryUsage.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblMemoryUsage.setBounds(624, 240, 140, 14);
			frmDecypherInStyle.getContentPane().add(lblMemoryUsage);
			
			textCurrentMemUsage = new JLabel("MB:");
			textCurrentMemUsage.setToolTipText("In short this is approximately the amount of RAM that this program uses. \r\nYou will not see changes in its usage until you pass a certain memory usage , then Java Virtual Mahine will automatically allocate more memory.");
			textCurrentMemUsage.setForeground(Color.WHITE);
			textCurrentMemUsage.setFont(new Font("Monospaced", Font.PLAIN, 14));
			textCurrentMemUsage.setBounds(624, 264, 140, 25);
			frmDecypherInStyle.getContentPane().add(textCurrentMemUsage);
			
			JTextArea txtrT = new JTextArea();
			txtrT.setBackground(Color.LIGHT_GRAY);
			txtrT.setToolTipText("Example: \\\\sbc\"1234567890AAa\" \\\\sec\"1234567890AAa\"");
			txtrT.setFont(new Font("Monospaced", Font.PLAIN, 12));
			txtrT.setBounds(124, 179, 640, 22);
			frmDecypherInStyle.getContentPane().add(txtrT);
			
			JLabel lblAlgorithmComplexity = new JLabel("Algorithm complexity:");
			lblAlgorithmComplexity.setForeground(Color.WHITE);
			lblAlgorithmComplexity.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblAlgorithmComplexity.setBounds(14, 208, 110, 14);
			frmDecypherInStyle.getContentPane().add(lblAlgorithmComplexity);
			
			JLabel txtComplexity = new JLabel("Very High: O(N!*2N+300*N^2)");
			txtComplexity.setForeground(Color.WHITE);
			txtComplexity.setFont(new Font("Monospaced", Font.PLAIN, 14));
			txtComplexity.setBounds(124, 204, 640, 25);
			frmDecypherInStyle.getContentPane().add(txtComplexity);
			
			JLabel lblEstimatedMemoryUsage = new JLabel("Estimated filesize on disk:");
			lblEstimatedMemoryUsage.setForeground(Color.WHITE);
			lblEstimatedMemoryUsage.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblEstimatedMemoryUsage.setBounds(624, 300, 140, 14);
			frmDecypherInStyle.getContentPane().add(lblEstimatedMemoryUsage);
			
			JLabel txtEstimatedMemUsage = new JLabel("MB:");
			txtEstimatedMemUsage.setForeground(Color.WHITE);
			txtEstimatedMemUsage.setFont(new Font("Monospaced", Font.PLAIN, 14));
			txtEstimatedMemUsage.setBounds(624, 324, 140, 25);
			frmDecypherInStyle.getContentPane().add(txtEstimatedMemUsage);
			
			JButton btnRefreshVariables = new JButton("Refresh variables");
			btnRefreshVariables.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					textCurrentMemUsage.setText("MB: " + getMemoryUsageLong());
					doo();
					
				}
			});
			btnRefreshVariables.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnRefreshVariables.setBounds(624, 360, 140, 23);
			frmDecypherInStyle.getContentPane().add(btnRefreshVariables);
			
			JButton btnSettings = new JButton("Settings");
			btnSettings.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					Settings.newScreen();	
				}
			});
			btnSettings.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnSettings.setBounds(624, 478, 140, 22);
			frmDecypherInStyle.getContentPane().add(btnSettings);
			
			
	}
}