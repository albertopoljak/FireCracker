package main;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import main.methods.Generators;
import main.methods.Helpers;
import main.methods.Log;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;
import java.awt.Color;

public class Main {
	//Basic elements
	private static JFrame frmDecypherInStyle;
	private static Settings windowSettings;
	private static JTextArea txtWords;
	public static JTextPane txtrLog;
	private static JLabel textCurrentMemUsage;
	private static PrintWriter out;
	private static Timer timer;
	public static int test = 0;

	/**
	 * Launch new screen.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmDecypherInStyle.setVisible(true);
					windowSettings = new Settings();
					windowSettings.setVisible(false);
					
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	/**
	 * Create screen elements.
	 */
	public Main() {
		initialize();
		printMemoryUsageInInterval(3);
		autoMemoryDetection();
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
			
			JLabel txtEnter = new JLabel();
			txtEnter.setForeground(Color.LIGHT_GRAY);
			txtEnter.setFont(new Font("Tahoma", Font.PLAIN, 11));
			txtEnter.setBounds(14, 11, 600, 18);
			txtEnter.setText("Enter set of possible words with space in between (char space can be changed in settings)");
			txtEnter.setOpaque(false);
			frmDecypherInStyle.getContentPane().add(txtEnter);
			
			txtrLog = new JTextPane();
			txtrLog.setBackground(Color.GRAY);
			txtrLog.setText("Log:\n");
			txtrLog.setEditable(false);
			txtrLog.setFont(new Font("Calibri", Font.PLAIN, 18));
			txtrLog.setBounds(20, 49, 399, 482);
			
			JScrollPane sp = new JScrollPane(txtrLog);
			sp.setLocation(14, 240);
			sp.setSize(600, 300);
			frmDecypherInStyle.getContentPane().add(sp, BorderLayout.CENTER);
			
			JButton btnGetSavePath = new JButton("Print save path");
			btnGetSavePath.setBackground(Color.DARK_GRAY);
			btnGetSavePath.setForeground(Color.GRAY);
			btnGetSavePath.setFocusPainted(false);
			btnGetSavePath.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnGetSavePath.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					Log.write("Save path location:\n"+Settings.savePath , 'I');
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
			
			JButton btnClearWords = new JButton("Clear Words");
			btnClearWords.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					txtWords.setText("");
				}
			});
			btnClearWords.setBackground(Color.DARK_GRAY);
			btnClearWords.setForeground(Color.GRAY);
			btnClearWords.setFocusPainted(false);
			btnClearWords.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnClearWords.setBounds(624, 6, 140, 20);
			frmDecypherInStyle.getContentPane().add(btnClearWords);	
			
			JButton btnClearLog = new JButton("Clear Log");
			btnClearLog.setBackground(Color.DARK_GRAY);
			btnClearLog.setForeground(Color.GRAY);
			btnClearLog.setFocusPainted(false);
			btnClearLog.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnClearLog.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					txtrLog.setText("Log:\n");
				}
			});
			btnClearLog.setBounds(624, 420, 140, 22);
			frmDecypherInStyle.getContentPane().add(btnClearLog);
		
			JButton btnInfo = new JButton("Help");
			btnInfo.setBackground(Color.DARK_GRAY);
			btnInfo.setForeground(Color.GRAY);
			btnInfo.setFocusPainted(false);
			btnInfo.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnInfo.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					Info.newScreen();					
				}
			});
			btnInfo.setBounds(624, 449, 140, 22);
			frmDecypherInStyle.getContentPane().add(btnInfo);
					
			JButton btnHack = new JButton("Hack!");
			btnHack.setBackground(Color.DARK_GRAY);
			btnHack.setForeground(Color.GRAY);
			btnHack.setFocusPainted(false);
			btnHack.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnHack.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					try{
						openPrintWriter("wordList.txt");
						List<List<String>> tempOutput = new ArrayList<List<String>>();
						tempOutput = buildWords( extractInput(txtWords.getText() , Settings.wordSeparator), Settings.wordCombine , Settings.optionSeparator ) ;
						Generators.combinations2D( Helpers.convertListToStringArray(tempOutput) );
						closePrintWriter();
						Log.appendToTextPanel("Test: "+test, 'I');
						test=0;
						
					}catch(Exception exc){
						Log.write("Error: "+exc , 'E');
					}
				}
			});
			btnHack.setBounds(624, 507, 140, 33);
			frmDecypherInStyle.getContentPane().add(btnHack);
			
			JLabel txtrOptions = new JLabel();
			txtrOptions.setForeground(Color.LIGHT_GRAY);
			txtrOptions.setText("Universal options:");
			txtrOptions.setOpaque(false);
			txtrOptions.setFont(new Font("Tahoma", Font.PLAIN, 11));
			txtrOptions.setBounds(14, 179, 100, 18);
			frmDecypherInStyle.getContentPane().add(txtrOptions);
			
			JLabel lblMemoryUsage = new JLabel("Current RAM usage:");
			lblMemoryUsage.setForeground(Color.LIGHT_GRAY);
			lblMemoryUsage.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblMemoryUsage.setBounds(624, 240, 140, 14);
			frmDecypherInStyle.getContentPane().add(lblMemoryUsage);
			
			textCurrentMemUsage = new JLabel("MB:");
			textCurrentMemUsage.setToolTipText("In short this is approximately the amount of RAM that this program uses. \r\nYou will not see changes in its usage until you pass a certain memory usage , then Java Virtual Mahine will automatically allocate more memory.");
			textCurrentMemUsage.setForeground(Color.LIGHT_GRAY);
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
			lblAlgorithmComplexity.setForeground(Color.LIGHT_GRAY);
			lblAlgorithmComplexity.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblAlgorithmComplexity.setBounds(14, 208, 110, 14);
			frmDecypherInStyle.getContentPane().add(lblAlgorithmComplexity);
			
			JLabel txtComplexity = new JLabel("Very High: O(N!*2N+300*N^2)");
			txtComplexity.setForeground(Color.LIGHT_GRAY);
			txtComplexity.setFont(new Font("Monospaced", Font.PLAIN, 14));
			txtComplexity.setBounds(124, 204, 640, 25);
			frmDecypherInStyle.getContentPane().add(txtComplexity);
			
			JLabel lblEstimatedMemoryUsage = new JLabel("Estimated filesize on disk:");
			lblEstimatedMemoryUsage.setForeground(Color.LIGHT_GRAY);
			lblEstimatedMemoryUsage.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblEstimatedMemoryUsage.setBounds(624, 300, 140, 14);
			frmDecypherInStyle.getContentPane().add(lblEstimatedMemoryUsage);
			
			JLabel txtEstimatedMemUsage = new JLabel("MB:");
			txtEstimatedMemUsage.setForeground(Color.LIGHT_GRAY);
			txtEstimatedMemUsage.setFont(new Font("Monospaced", Font.PLAIN, 14));
			txtEstimatedMemUsage.setBounds(624, 324, 140, 25);
			frmDecypherInStyle.getContentPane().add(txtEstimatedMemUsage);
			
			JButton btnRefreshVariables = new JButton("Refresh variables");
			btnRefreshVariables.setBackground(Color.DARK_GRAY);
			btnRefreshVariables.setForeground(Color.GRAY);
			btnRefreshVariables.setFocusPainted(false);
			btnRefreshVariables.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					textCurrentMemUsage.setText("MB: " + Helpers.getMemoryUsageLong());
					
				}
			});
			btnRefreshVariables.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnRefreshVariables.setBounds(624, 360, 140, 23);
			frmDecypherInStyle.getContentPane().add(btnRefreshVariables);
			
			JButton btnSettings = new JButton("Settings");
			btnSettings.setBackground(Color.DARK_GRAY);
			btnSettings.setForeground(Color.GRAY);
			btnSettings.setFocusPainted(false);
			btnSettings.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					windowSettings.setVisible(true);
				}
			});
			btnSettings.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnSettings.setBounds(624, 478, 140, 22);
			frmDecypherInStyle.getContentPane().add(btnSettings);
			
			
	}
	
	
	
	
////////////////////////////////////////////////////////////////////////////////////////
//Working Methods\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
////////////////////////////////////////////////////////////////////////////////////////

	private static void printMemoryUsageInInterval( int intervalSeconds){
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				textCurrentMemUsage.setText("MB: " + Helpers.getMemoryUsageLong());
			}
		}, 0, intervalSeconds*1000);
	
	}


	public static void changeUpdateVariable(int newInterval){
		timer.cancel();
		if(newInterval>0)
			printMemoryUsageInInterval(newInterval);
	}	


	//Method to clear list (.clear leaves memory footprint for the largest element, meaning N*nullElements)
	private static ArrayList<String> clearList() {
		ArrayList<String> newList = new ArrayList<String>();
		return newList;
	}


	public static void openPrintWriter(String filePath) throws IOException{
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
		} catch (IOException e) {
			Log.appendToTextPanel("Error creating file for saving!\n"+e , 'E');
			throw new IOException(e);
		}
	}

	public static void closePrintWriter(){
		if( out!=null )
			out.close();
	}


	public static void writeString(String write[]){
		int n = write.length;
		for (int i=0; i<n; i++)
			out.print(write[i]);
		out.println();
	}	


	//Methods for settings
	public static void autoMemoryDetection(){
		long maxMemory = Runtime.getRuntime().maxMemory()/1024/1024;
		Log.write("Max memory: "+maxMemory+"mb");
		if(maxMemory<2000){
			Settings.lowMemory = true;
			Log.write("Program is set to run on low memory (less that 2000mb).\n"
					+ "This will affect program execution time but will reduce memory usage.\n"
					+ "If the memory reduction is not enough and the program eats all the memory it will crash.\n"
					+ "Run in 64bits to increase avaiable memory size (works only if you have more than 2gb RAM)");
		}else
			Settings.lowMemory = false;
	}


	//Getters	
	public static String getTxtWords(){
		return txtWords.getText();
	}
	
	
	
	
	/*Extract input (all words+their options) from string input and returns them as arrayList
	 * char wordSeparator represents word separator example in string "cat dog jaguar" word separator would be char ' ' (space)
	 */
		 
		private static ArrayList<String> extractInput(String input ,char wordSeparator){
			ArrayList<String> returnedWords = new ArrayList<String>();
			String tempWord = "";
			if( input!=null && input.length()>0 ){
				for( int i=0; i<input.length() ; i++){
					char currentChar = input.charAt(i);
					if( currentChar!=wordSeparator && currentChar!='\n' )
						tempWord+=input.charAt(i);
					else{
						if(tempWord.length()>0)
							returnedWords.add(tempWord);
						else
							Log.writeDebug("Skipping empty word (you probably typed word separator twice)..." );
						tempWord = "";
					}
				}
				
				//Add last word if there is NO newline at the end
				if( input.charAt( input.length()-1 ) !='\n' )
					returnedWords.add(tempWord);

				Log.writeDebug("Extracted input: " + Arrays.toString(returnedWords.toArray()) );
				
				return returnedWords;
				
			}else{
				Log.write("Input is empty!" , 'W');
				return null;
			}
		}
	
		
		
		
		
		/*
		 * Build words from options
		 */
		/*
		 * NOTE:
		 * Total number of generated words is: X * Y
		 * Where X is number of words generated by combinations2D and Y same but from heapPermutation
		 * Example: Input is {{"sad","SAD"},{"najebo","NAJEBO"},{"sam","SAM"},{"test","TEST", "Test", "ayy"}}
		 * X would then be 2*2*2*4 = 32 
		 * Y would be 4! (1*2*3*4) because we have 4 blocks (4 words that make a sentence)
		 * Total = 768
		 */	
			
		//int Y = inputWords.size(); daje nullPointer exception ako je input prazan		
		private static List<List<String>> buildWords( ArrayList<String> inputWords , char combiningSeparator, char optionSeparator){
			int i;
			int X;
			int Y = inputWords.size();
			List<List<String>> tempOutput = new ArrayList<List<String>>();
			
			//Add Y words and start adding words and their options
			for( i=0; i<Y; i++){
				tempOutput.add(new ArrayList<String>());
				extractWords( tempOutput, i, inputWords.get(i).toString(), combiningSeparator, optionSeparator );
			}
			
			Log.writeDebug( "These words were extracted from input:" );
			for( i=0; i<Y; i++){
				for (String value : tempOutput.get(i)) {
					Log.writeDebug( (i+1) + ":" + value );
				}
			}
			
			return tempOutput;
		}
		
		
		
		
		/*
		 * Extract words from string
		 */
		
		public static void extractWords( List<List<String>> inputList , int index , String extractString , char combiningSeparator, char optionSeparator ){
			String baseString;
			String optionString;
			int indexCombining = extractString.indexOf(combiningSeparator);

			if( indexCombining!=-1 ){
				/*
				 * There is combining separator in string so we will break the word
				 * in 2 parts and call this function again with each part
				 */
			   Log.writeDebug("Found combining separator in string: " + extractString );
			   String tempInput = extractString ;
			   StringBuilder temp = new StringBuilder();
			   for( int i=0; i<tempInput.length(); i++ ){
				   if( tempInput.charAt(i)==combiningSeparator ){
					   extractWords( inputList, index, temp.toString() , combiningSeparator, optionSeparator );
					   temp = new StringBuilder();

				   }else if( i==tempInput.length()-1 ){
					   temp.append( tempInput.charAt(i) );
					   extractWords( inputList, index, temp.toString() , combiningSeparator, optionSeparator );
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
					inputList.get(index).add(baseString);
					/*
					 * Split option string to multiple parts then call function for every option part
					 * We used optionSeparatorDouble because option separator is a double char in the program
					 */
					String optionSeparatorDouble = optionSeparator+""+optionSeparator;
					String[] word = optionString.split(optionSeparatorDouble);
					word = removeEmpty(word);
					
					Log.writeDebug("Printing option array(ID:option) :" );
					for(int k =0; k<word.length;k++)
						Log.writeDebug( k + ": " + word[k] );
					
						for(String optionPart: word){
							/*
							 * Eliminate empty words produced by optionString.split
							 */
							//log.append("Test:"+optionPart+"\n");
							//if(!optionPart.isEmpty()){
								/*
								 * Before calling the option method first extract sub-options
								 */
							Log.writeDebug("Spliting:'"+optionPart+"' to suboptions!" );
								String[] subOptions = optionPart.split( ""+optionSeparator );
								/*
								 * Now remove empty words from sub-options produced by optionString.split
								 */
								subOptions = removeEmpty(subOptions);
								Log.writeDebug("Resulting suboptions array(ID:suboption) :" );
								for(int k =0; k<subOptions.length;k++)
									Log.writeDebug(k+":"+subOptions[k] );
								/*
								 * String in first index of finalSubOptions is the option while rest are subOptions
								 */
								//Log.writeDebug("baseString: "+baseString+"  ,optionPart:"+optionPart+"  ,subOptions: (printed above)");
								extractOptions( inputList, index, baseString, subOptions );
							//}
						}
					return;
				}else{
					/*
					 * There are no options in word so we just add it to the list
					 */
					Log.writeDebug("No options in word:"+extractString );
					inputList.get(index).add(extractString);
					return;
				}
			}
			
		}
		
		
		
		/*
		 * Build options
		 * optionString is already checked for empty in the previous method
		 * String in first index of finalSubOptions is the option while rest are subOptions
		 */
		public static void extractOptions( List<List<String>> inputList , int index , String baseString, String[] subOptions ){
			
			//Potential error handling (some of this might not be needed)
			if( !(subOptions.length>0) ) {
				Log.write("Options have been found for word "+baseString+" but program couldn't read them!\n"
						+ "(length!>0)" , 'W');
				return;
			}/*else if( subOptions.length==1 ) {
				log.append("Options have been found for word "+baseString+" but program couldn't read them!\n"
						+ "(length==1)\n");
				return;
			}*///Not needed?
			
			//Call functions based on found options
			
			switch (subOptions[0]) {
	        case "b":
	        	if( subOptions.length>1 ){
	        		Log.write("Option 'b' can't have suboptions! ("+subOptions[1]+" etc...)" , 'E');
	        	}else{

	        	}
	        	break;
	        case "l":
	        	if( subOptions.length>1 ){
	        		Log.write("Option 'l' can't have suboptions! ("+subOptions[1]+" etc...)" , 'E');
	        		break;
	        	}else{
	        		//inputList.get(index).add( baseString.toLowerCase() );
	        	}
	        	break;
	        case "u":
	        	if( subOptions.length>1 ){
	        		Log.write("Option 'u' can't have suboptions! ("+subOptions[1]+" etc...)" , 'E');
	        		break;
	        	}else{
	        		inputList.get(index).add( baseString.toUpperCase() );
	        	}
	        	break;
	        case "ip":
	        	Log.write("Option 'ip' is not curently in function!" , 'W');
	        	break;
	        case "ab":
	        	Log.write("Option 'ab' is not curently in function!" , 'W');
	        	break;
	        case "ae":
	        	Log.write("Option 'ae' is not curently in function!" , 'W');
	        	break;
	        default:
	        	Log.write("Can't find option '" +subOptions[0]+ "'" , 'E');
			}
			
			
		}
		
		
		/*
		 * Eliminate empty words in string array
		 * Words are considered empty if they are null or have 0 characters
		 */
		
		public static String[] removeEmpty(String[] input){
			List<String> list = new ArrayList<String>(Arrays.asList(input));
			list.removeAll(Arrays.asList("", null));
			return list.toArray(new String[0]);
		}
		
		/*
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