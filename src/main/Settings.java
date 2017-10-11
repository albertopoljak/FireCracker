package main;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import main.methods.Universal;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JSeparator;

public class Settings extends JFrame {
	//Basic variables
	private static JPanel contentPane;
	private static JTextField textFieldSavePath;
	private static JTextField txtrSeparatorWord;
	private static JComboBox comboUpdate;
	private static JCheckBox chckbxMeasureTimeNeeded;
	private static JRadioButton rdbtnIHaveLow;
	private static JRadioButton rdbtnIHaveHigh;
	private static JRadioButton rdbtnAutoRamDetection;
	private static String reservedCharacters  = "ABCEFIJOPSUX\\\"1234567890,-"; //dodaj - ?
	private static char separatorOption  = '"';
	private JTextField txtrSeparatorOption;
	private static String basePath = new File("").getAbsolutePath();
	private final JFileChooser fileChooser;
	//Window variables
	public static char wordSeparator = ' ';
	public static char wordCombine = '+';
	public static char optionSeparator = '-';
	public static String savePath = basePath;
	public static boolean measureVariables = true;
	public static boolean lowMemory = true;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void newScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Settings frame = new Settings();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//Copied from Word List Sorter
	private static boolean inputContainsCharFromString(String input , String characterSet){
		for( int i=0; i<characterSet.length(); i++){
			if( input.contains( String.valueOf(characterSet.charAt(i))) )
				return true;
		}
		return false;
	}
	
	//
	private static void memorySettings(){
		if(rdbtnIHaveLow.isSelected())
			lowMemory = true;
		else if(rdbtnIHaveHigh.isSelected())
			lowMemory = false;
		else if(rdbtnAutoRamDetection.isSelected())
			Main.autoMemoryDetection();
	}
	
	public static void changeOptionSeparator(char newSeparator){
		separatorOption = newSeparator;
	}
	

	
	/**
	 * Create the frame.
	 */
	public Settings() {
		setResizable(false);
		//Initialize file chooser
		basePath = new File("").getAbsolutePath();
		fileChooser = new JFileChooser(basePath);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		setTitle("Settings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 580);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnKillTheMusic = new JButton("Kill the music");
		btnKillTheMusic.setForeground(Color.GRAY);
		btnKillTheMusic.setBackground(Color.DARK_GRAY);
		btnKillTheMusic.setFocusPainted(false);
		btnKillTheMusic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});
		btnKillTheMusic.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnKillTheMusic.setBounds(10, 114, 120, 21);
		contentPane.add(btnKillTheMusic);
		
		JLabel lblPerfomanceOptions = new JLabel("Perfomance options:");
		lblPerfomanceOptions.setForeground(Color.LIGHT_GRAY);
		lblPerfomanceOptions.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPerfomanceOptions.setBounds(10, 11, 120, 14);
		contentPane.add(lblPerfomanceOptions);
		
		rdbtnIHaveLow = new JRadioButton("Disk");
		rdbtnIHaveLow.setForeground(Color.LIGHT_GRAY);
		rdbtnIHaveLow.setBackground(Color.DARK_GRAY);
		rdbtnIHaveLow.setFocusPainted(false);
		rdbtnIHaveLow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				memorySettings();
			}
		});
		rdbtnIHaveLow.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnIHaveLow.setBounds(257, 31, 110, 23);
		contentPane.add(rdbtnIHaveLow);
		
		rdbtnIHaveHigh = new JRadioButton("RAM");
		rdbtnIHaveHigh.setForeground(Color.LIGHT_GRAY);
		rdbtnIHaveHigh.setBackground(Color.DARK_GRAY);
		rdbtnIHaveHigh.setFocusPainted(false);
		rdbtnIHaveHigh.setToolTipText("If the program uses more RAM than you have it will stall and you will have to forcefully close it.");
		rdbtnIHaveHigh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				memorySettings();
			}
		});
		rdbtnIHaveHigh.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnIHaveHigh.setBounds(369, 31, 110, 23);
		contentPane.add(rdbtnIHaveHigh);
		
		rdbtnAutoRamDetection = new JRadioButton("Auto detection");
		rdbtnAutoRamDetection.setForeground(Color.LIGHT_GRAY);
		rdbtnAutoRamDetection.setBackground(Color.DARK_GRAY);
		rdbtnAutoRamDetection.setFocusPainted(false);
		rdbtnAutoRamDetection.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				memorySettings();
			}
		});
		rdbtnAutoRamDetection.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnAutoRamDetection.setSelected(true);
		rdbtnAutoRamDetection.setBounds(145, 31, 110, 23);
		contentPane.add(rdbtnAutoRamDetection);
		
		//Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
	    group.add(rdbtnIHaveLow);
	    group.add(rdbtnIHaveHigh);
	    group.add(rdbtnAutoRamDetection);
		
		JLabel lblDiskUsage = new JLabel("Disk usage:");
		lblDiskUsage.setForeground(Color.LIGHT_GRAY);
		lblDiskUsage.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDiskUsage.setBounds(10, 457, 120, 14);
		contentPane.add(lblDiskUsage);
		
		textFieldSavePath = new JTextField();
		textFieldSavePath.setEditable(false);
		textFieldSavePath.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textFieldSavePath.setBounds(10, 508, 465, 20);
		contentPane.add(textFieldSavePath);
		textFieldSavePath.setColumns(10);
		
		JButton btnSetPath = new JButton("Edit path");
		btnSetPath.setBackground(Color.DARK_GRAY);
		btnSetPath.setForeground(Color.GRAY);
		btnSetPath.setFocusPainted(false);
		btnSetPath.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				int returnValue = fileChooser.showSaveDialog(contentPane);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					textFieldSavePath.setText(selectedFile.getAbsolutePath());
					savePath = selectedFile.getAbsolutePath();
				}else
					Main.log("[ERROR]--> File not selected!\n");

			}
		});
		btnSetPath.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSetPath.setBounds(485, 507, 89, 23);
		contentPane.add(btnSetPath);
		
		comboUpdate = new JComboBox();
		comboUpdate.setForeground(Color.BLACK);
		comboUpdate.setBackground(Color.GRAY);
		comboUpdate.setToolTipText("0 = don't update");
		comboUpdate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboUpdate.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5"}));
		comboUpdate.setSelectedIndex(3);
		comboUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.changeUpdateVariable( Integer.parseInt((String)comboUpdate.getSelectedItem()));
			}
		});
		comboUpdate.setBounds(184, 58, 54, 20);
		contentPane.add(comboUpdate);
		
		JButton btnFreeAllMemory = new JButton("Free all memory");
		btnFreeAllMemory.setBackground(Color.DARK_GRAY);
		btnFreeAllMemory.setForeground(Color.GRAY);
		btnFreeAllMemory.setFocusPainted(false);
		btnFreeAllMemory.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnFreeAllMemory.setToolTipText("This will destroy already generated words that are in memory, it will delete all inputed text, kill the music");
		btnFreeAllMemory.setBounds(140, 114, 110, 21);
		contentPane.add(btnFreeAllMemory);
		
		chckbxMeasureTimeNeeded = new JCheckBox("Measure time needed for each task");
		chckbxMeasureTimeNeeded.setBackground(Color.DARK_GRAY);
		chckbxMeasureTimeNeeded.setForeground(Color.LIGHT_GRAY);
		chckbxMeasureTimeNeeded.setFocusPainted(false);
		chckbxMeasureTimeNeeded.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				measureVariables = chckbxMeasureTimeNeeded.isSelected();
			}
		});
		chckbxMeasureTimeNeeded.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxMeasureTimeNeeded.setSelected(true);
		chckbxMeasureTimeNeeded.setBounds(6, 84, 242, 23);
		contentPane.add(chckbxMeasureTimeNeeded);
		
		txtrSeparatorWord = new JTextField();
		txtrSeparatorWord.setForeground(Color.BLACK);
		txtrSeparatorWord.setBackground(Color.LIGHT_GRAY);
		txtrSeparatorWord.setText("+");
		txtrSeparatorWord.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtrSeparatorWord.setToolTipText("Enter 1 character that symbolises new word.  NOTE THAT ALL CHARACTERS FROM OPTION STRING \"\" ARE RESERVED!");
		txtrSeparatorWord.setBounds(300, 183, 25, 20);
		contentPane.add(txtrSeparatorWord);
		txtrSeparatorWord.setColumns(10);
		
		JLabel lblMiscOptions = new JLabel("Program execution options:");
		lblMiscOptions.setForeground(Color.LIGHT_GRAY);
		lblMiscOptions.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMiscOptions.setBounds(10, 161, 150, 14);
		contentPane.add(lblMiscOptions);
		
		JButton btnSetWordSeparator = new JButton("Set");
		btnSetWordSeparator.setBackground(Color.DARK_GRAY);
		btnSetWordSeparator.setForeground(Color.GRAY);
		btnSetWordSeparator.setFocusPainted(false);
		btnSetWordSeparator.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				//private static String reservedCharactersFromOptions = "";
				String reservedCharactersFromOptions = Universal.getCharactersFromOptions( Main.getTxtWords(), separatorOption );
				if( txtrSeparatorWord.getText()!=null &&  txtrSeparatorWord.getText().length()>0 ){
					if( inputContainsCharFromString( String.valueOf(txtrSeparatorWord.getText().charAt(0)).toUpperCase(), reservedCharacters ) || inputContainsCharFromString( String.valueOf(txtrSeparatorWord.getText().charAt(0)), reservedCharactersFromOptions ))
						System.out.println("Character is reserved!\nReserved characters are(inside the quotes, including their lowercase):\""+reservedCharacters+"\" \n,plus these characters from options(inside the quotes, case sensitive):\""+reservedCharactersFromOptions+"\"");
					else
						wordSeparator = txtrSeparatorWord.getText().charAt(0);
				}else
					System.out.println("Input a character first!");
			}
		});
		btnSetWordSeparator.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSetWordSeparator.setBounds(330, 183, 50, 20);
		contentPane.add(btnSetWordSeparator);
		
		JLabel lblDontUseSpace = new JLabel("Don't use space as word separator, use this instead:");
		lblDontUseSpace.setForeground(Color.LIGHT_GRAY);
		lblDontUseSpace.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDontUseSpace.setBounds(10, 186, 270, 14);
		contentPane.add(lblDontUseSpace);
		
		JLabel lblUpdateVariablesEach = new JLabel("Update variables each X seconds:");
		lblUpdateVariablesEach.setForeground(Color.LIGHT_GRAY);
		lblUpdateVariablesEach.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUpdateVariablesEach.setBounds(10, 61, 176, 14);
		contentPane.add(lblUpdateVariablesEach);
		
		JLabel lblNewLabel = new JLabel("Save word list to the following path:");
		lblNewLabel.setForeground(Color.LIGHT_GRAY);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(10, 482, 230, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblDontUseQuotes = new JLabel("Don't use slash as option separator, use this instead:");
		lblDontUseQuotes.setForeground(Color.LIGHT_GRAY);
		lblDontUseQuotes.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDontUseQuotes.setBounds(10, 207, 294, 14);
		contentPane.add(lblDontUseQuotes);
		
		txtrSeparatorOption = new JTextField();
		txtrSeparatorOption.setForeground(Color.BLACK);
		txtrSeparatorOption.setBackground(Color.LIGHT_GRAY);
		txtrSeparatorOption.setText("+");
		txtrSeparatorOption.setToolTipText("");
		txtrSeparatorOption.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtrSeparatorOption.setColumns(10);
		txtrSeparatorOption.setBounds(300, 205, 25, 20);
		contentPane.add(txtrSeparatorOption);
		
		JButton btnSetOptionSeparator = new JButton("Set");
		btnSetOptionSeparator.setBackground(Color.DARK_GRAY);
		btnSetOptionSeparator.setForeground(Color.GRAY);
		btnSetOptionSeparator.setFocusPainted(false);
		btnSetOptionSeparator.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				String reservedCharactersFromOptions = Universal.getCharactersFromOptions( Main.getTxtWords(), separatorOption );
				if( txtrSeparatorOption.getText()!=null &&  txtrSeparatorOption.getText().length()>0 ){
					if( inputContainsCharFromString( String.valueOf(txtrSeparatorOption.getText().charAt(0)).toUpperCase(), reservedCharacters ) || inputContainsCharFromString( String.valueOf(txtrSeparatorOption.getText().charAt(0)), reservedCharactersFromOptions ))
						System.out.println("Character is reserved!\nReserved characters are(inside the quotes, including their lowercase):\""+reservedCharacters+"\" \n,plus these characters from options(inside the quotes, case sensitive):\""+reservedCharactersFromOptions+"\"");
					else
						changeOptionSeparator( txtrSeparatorOption.getText().charAt(0) );
				}else
					System.out.println("Input a character first!");
			}
		});
		btnSetOptionSeparator.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSetOptionSeparator.setBounds(330, 205, 50, 20);
		contentPane.add(btnSetOptionSeparator);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.GRAY);
		separator.setForeground(Color.LIGHT_GRAY);
		separator.setBounds(10, 25, 564, 2);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.LIGHT_GRAY);
		separator_1.setBackground(Color.GRAY);
		separator_1.setBounds(10, 472, 564, 2);
		contentPane.add(separator_1);
		
		JLabel lblNewLabel_1 = new JLabel("Save temporary words to:");
		lblNewLabel_1.setForeground(Color.LIGHT_GRAY);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(10, 36, 136, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Advanced:");
		lblNewLabel_2.setForeground(Color.LIGHT_GRAY);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(10, 357, 60, 14);
		contentPane.add(lblNewLabel_2);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.LIGHT_GRAY);
		separator_2.setBackground(Color.GRAY);
		separator_2.setBounds(10, 371, 564, 2);
		contentPane.add(separator_2);
		
		JLabel lblPrintToLog = new JLabel("Print to log:");
		lblPrintToLog.setForeground(Color.LIGHT_GRAY);
		lblPrintToLog.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPrintToLog.setBounds(10, 382, 76, 14);
		contentPane.add(lblPrintToLog);
		
		JButton btnCurrentWordsReady = new JButton("Current words ready for permutation");
		btnCurrentWordsReady.setBackground(Color.DARK_GRAY);
		btnCurrentWordsReady.setForeground(Color.GRAY);
		btnCurrentWordsReady.setFocusPainted(false);
		btnCurrentWordsReady.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCurrentWordsReady.setBounds(96, 378, 230, 23);
		contentPane.add(btnCurrentWordsReady);
		
		JButton btnRefreshComplexityText = new JButton("Refresh complexity text");
		btnRefreshComplexityText.setBackground(Color.DARK_GRAY);
		btnRefreshComplexityText.setForeground(Color.GRAY);
		btnRefreshComplexityText.setFocusPainted(false);
		btnRefreshComplexityText.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnRefreshComplexityText.setBounds(96, 403, 229, 23);
		contentPane.add(btnRefreshComplexityText);
		
		JLabel lblNewLabel_3 = new JLabel("Don't use plus as word combinator, use this instead:");
		lblNewLabel_3.setForeground(Color.LIGHT_GRAY);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_3.setBounds(10, 228, 270, 14);
		contentPane.add(lblNewLabel_3);
		
		textField = new JTextField();
		textField.setForeground(Color.BLACK);
		textField.setBackground(Color.LIGHT_GRAY);
		textField.setText("+");
		textField.setBounds(300, 227, 25, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Set");
		btnNewButton.setBackground(Color.DARK_GRAY);
		btnNewButton.setForeground(Color.GRAY);
		btnNewButton.setFocusPainted(false);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.setBounds(330, 227, 50, 20);
		contentPane.add(btnNewButton);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setForeground(Color.LIGHT_GRAY);
		separator_3.setBackground(Color.GRAY);
		separator_3.setBounds(10, 294, 564, 2);
		contentPane.add(separator_3);
		
		JLabel lblNewLabel_4 = new JLabel("Look and feel");
		lblNewLabel_4.setForeground(Color.LIGHT_GRAY);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_4.setBounds(10, 280, 76, 14);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblTheme = new JLabel("Theme:");
		lblTheme.setForeground(Color.LIGHT_GRAY);
		lblTheme.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTheme.setBounds(10, 307, 46, 14);
		contentPane.add(lblTheme);
		
		JRadioButton rdbtnDark = new JRadioButton("Dark");
		rdbtnDark.setForeground(Color.LIGHT_GRAY);
		rdbtnDark.setBackground(Color.DARK_GRAY);
		rdbtnDark.setFocusPainted(false);
		rdbtnDark.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnDark.setBounds(62, 303, 65, 23);
		rdbtnDark.setSelected(true);
		contentPane.add(rdbtnDark);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Bright");
		rdbtnNewRadioButton.setBackground(Color.DARK_GRAY);
		rdbtnNewRadioButton.setForeground(Color.LIGHT_GRAY);
		rdbtnNewRadioButton.setFocusPainted(false);
		rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnNewRadioButton.setBounds(129, 303, 109, 23);
		contentPane.add(rdbtnNewRadioButton);
		
		//Group the radio buttons.
		ButtonGroup group2 = new ButtonGroup();
		group2.add(rdbtnDark);
		group2.add(rdbtnNewRadioButton);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setForeground(Color.LIGHT_GRAY);
		separator_4.setBackground(Color.GRAY);
		separator_4.setBounds(10, 175, 564, 2);
		contentPane.add(separator_4);
	}
}
