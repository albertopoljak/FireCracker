package main;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import main.methods.Methods_Universal;
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
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;
import java.awt.Color;

public class Main {
	//Basic elements
	private static JFrame frmDecypherInStyle;
	private static Settings windowSettings;
	private static JTextArea txtWords;
	private static JTextArea txtrLog;
	private static JLabel textCurrentMemUsage;
	private static PrintWriter out;
	private static Timer timer;
	

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

	
	
////////////////////////////////////////////////////////////////////////////////////////
//Working Methods\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
////////////////////////////////////////////////////////////////////////////////////////

	private static void printMemoryUsageInInterval( int intervalSeconds){
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			  @Override
			  public void run() {
				  textCurrentMemUsage.setText("MB: " + Methods_Universal.getMemoryUsageLong());
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

	
	public static void log(String text){
		txtrLog.append( text );
	}

	
	public static void openPrintWriter(String filePath){
		try {
    	    out = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
    	} catch (IOException e) {
    		txtrLog.append("Error creating file for saving!\n"+e+"\n");
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
		txtrLog.append("Max memory: "+maxMemory+"mb\n");
		if(maxMemory<2000){
			Settings.lowMemory = true;
			txtrLog.append("Program is set to run on low memory (less that 2000mb).\n"
					+ "This will affect program execution time but will reduce memory usage.\n"
					+ "If the memory reduction is not enough and the program eats all the memory it will crash.\n"
					+ "Run in 64bits to increase avaiable memory size (works only if you have more than 2gb RAM)\n");
		}else
			Settings.lowMemory = false;
	}
	

	//Getters	
	public static String getTxtWords(){
		return txtWords.getText();
	}
	
	
////////////////////////////////////////////////////////////////////////////////////////
//Testing Methods\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
////////////////////////////////////////////////////////////////////////////////////////	
	
	/*
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
	}*/
		



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
					txtrLog.append("Save path location:\n"+Settings.savePath+"\n");
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
					openPrintWriter("wordList.txt");
					closePrintWriter();
					
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
					textCurrentMemUsage.setText("MB: " + Methods_Universal.getMemoryUsageLong());
					
				}
			});
			btnRefreshVariables.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnRefreshVariables.setBounds(624, 360, 140, 23);
			frmDecypherInStyle.getContentPane().add(btnRefreshVariables);
			
			JButton btnSettings = new JButton("Settings");
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
}