package main;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import main.methods.CombinatoricsPermutations;
import main.methods.Helpers;
import main.methods.Log;
import main.methods.ProcessInput;
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
			txtEnter.setText("Enter set of possible words with their options");
			txtEnter.setOpaque(false);
			frmDecypherInStyle.getContentPane().add(txtEnter);
			
			txtrLog = new JTextPane();
			txtrLog.setEditable(false);
			txtrLog.setBackground(Color.GRAY);
			txtrLog.setText("Log:\n");
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
					Help.newScreen();					
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
						tempOutput = ProcessInput.buildWords( ProcessInput.extractInput(txtWords.getText() , Settings.wordSeparator), Settings.wordCombinator , Settings.optionSeparator ) ;
						CombinatoricsPermutations.combinations2D( Helpers.convertListToStringArray(tempOutput) );
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
					textCurrentMemUsage.setText("MB: " + getMemoryUsage());	
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
	
	
	private static void printMemoryUsageInInterval( int intervalSeconds){
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				textCurrentMemUsage.setText("MB: " + getMemoryUsage());
			}
		}, 0, intervalSeconds*1000);
	
	}

	public static long getMemoryUsage(){
		Runtime instance = Runtime.getRuntime();
		return (instance.totalMemory() - instance.freeMemory()) / 1024 / 1024;
	}
	
	public static long getMaxMemoryUsage(){
		Runtime instance = Runtime.getRuntime();
		return instance.maxMemory() / 1024 / 1024;
	}
	
	public static void changeUpdateVariable(int newInterval){
		timer.cancel();
		if(newInterval>0)
			printMemoryUsageInInterval(newInterval);
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

	public static void writeStringToPrintWriter(String write[]){
		int n = write.length;
		for (int i=0; i<n; i++)
			out.print(write[i]);
		out.println();
	}	

	
	/*
	 * Methods for settings
	 */
	public static void autoMemoryDetection(){
		long maxMemory = getMaxMemoryUsage();
		Log.write("Max memory: " + maxMemory + "mb");
		if(maxMemory<2000){
			Settings.lowMemory = true;
			Log.write("Program is set to run on low memory (less that 2000mb).\n"
					+ "This will affect program execution time but will reduce memory usage.\n"
					+ "If the memory reduction is not enough and the program eats all the memory it will crash.\n"
					+ "Run in 64bits to increase avaiable memory size (works only if you have more than 2gb RAM)");
		}else
			Settings.lowMemory = false;
	}
	
	
}
