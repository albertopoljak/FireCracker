import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.UIManager;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;
import java.awt.Color;

public class MainProgram {

	private static JFrame frmDecypherInStyle;
	private static String basePath = new File("").getAbsolutePath();
	private static JTextArea txtWords;
	private static JTextArea txtrLog;
	private static JLabel textCurrentMemUsage;
	private static PrintWriter out;
	private static Timer timer;
	
	private static String[] inputWords; //Used for storing words from text input (includes options example \\l , \\u ...) 
	public static int numberOfWords = 0; //num of words in input, used for stopping program if words = 0
	private static ArrayList<String> subSetWords = new ArrayList<String>(); //used with \\b
	private static ArrayList<String> lowerCaseWords = new ArrayList<String>(); //used with \\l
	private static ArrayList<String> upperCaseWords = new ArrayList<String>(); //used with \\u
	private static ArrayList<String> inverzWords = new ArrayList<String>(); //used with everything related to inverz
	private static ArrayList<String> workingWords = new ArrayList<String>(); //words to which we are finding subsets
	private static ArrayList<String> generatedWords = new ArrayList<String>(); // used instead of saving instantly to hdd
	private static int updateVariablesInterval = 3;
	
	private static int test =0;
	
	
	
	/**
	 * Launch new screen.
	 */
	public static void newScreen() {
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
		printMemoryUsageInInterval(updateVariablesInterval);
	}
	
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
	};
	
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
	
	
	private static void printMemoryUsage(){
		txtrLog.append(getMemoryUsageString());
	}
	
	
	private static String getMemoryUsageString(){
		return ("KB: " + Long.toString(getMemoryUsageLong() ) );
	}
	
	
	private static long getMemoryUsageLong(){
		return ( Runtime.getRuntime().totalMemory()/1024 );
	}
	
	
	private static void printMemoryUsageInInterval( int intervalSeconds){
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			  @Override
			  public void run() {
				  textCurrentMemUsage.setText(getMemoryUsageString());
				  System.out.println(intervalSeconds+"s");
			  }
			}, 0, intervalSeconds*1000);

	}
	
	
	//Method to clear list (.clear leaves memory footprint for the largest element, meaning N*nullElements)
		private static ArrayList<String> clearList() {
			   ArrayList<String> newList = new ArrayList<String>();
			   return newList;
		}
	
////////////////////////////////////////////////////////////////////////////////////////
//Testing Methods\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
////////////////////////////////////////////////////////////////////////////////////////
	
	//Helper for heapPermutation
	static void printStringArray(String a[], int n){
		for (int i=0; i<3; i++)
			txtrLog.append(String.valueOf(a[i]));
		txtrLog.append("\n");
		test++;
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
	
	
	private static void printWorkingList(){
		txtrLog.append("Started printing working list:\n");
		for(int i=0;i<workingWords.size();i++)
			txtrLog.append(workingWords.get(i)+"\n");
		txtrLog.append("Ended printing working list.\n");
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
			txtEnter.setBounds(14, 11, 330, 18);
			txtEnter.setText("Enter set of possible words with space in between:");
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
							numberOfWords = 0;
							inputWords = new String[0];
							generatedWords = clearList();
						//}

						
						//Start
						//txtrLog.append("[INFO]-->Hack in progress!\n");
						getWordsToString();
						printWorkingList();
						generateInputListFromOptions();
						printWorkingList();
						//generateLowerCaseCombinations();
						//charPermutationOfAllWords("","1234");
						//txtrLog.append("Permutation in progress!\n");
						
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
						//heapPermutation( b, b.length,b.length);
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
			
			textCurrentMemUsage = new JLabel("KB:");
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
			
			JLabel txtEstimatedMemUsage = new JLabel("KB:");
			txtEstimatedMemUsage.setForeground(Color.WHITE);
			txtEstimatedMemUsage.setFont(new Font("Monospaced", Font.PLAIN, 14));
			txtEstimatedMemUsage.setBounds(624, 324, 140, 25);
			frmDecypherInStyle.getContentPane().add(txtEstimatedMemUsage);
			
			JButton btnRefreshVariables = new JButton("Refresh variables");
			btnRefreshVariables.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					
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