package main;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import main.methods.Methods_Universal;
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
	private static String reservedCharacters  = "ABCEFIJOPSUX\\\"1234567890,";
	private static char separatorOption  = '"';
	private JTextField txtrSeparatorOption;
	private static String basePath = new File("").getAbsolutePath();;
	private final JFileChooser fileChooser;
	//Window variables
	public static char wordSeparator = ' ';
	public static String savePath = basePath;
	public static boolean measureVariables = true;
	public static boolean lowMemory = true;

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
		//Initialize file chooser
		basePath = new File("").getAbsolutePath();
		fileChooser = new JFileChooser(basePath);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		setTitle("Settings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 310);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnKillTheMusic = new JButton("Kill the music");
		btnKillTheMusic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});
		btnKillTheMusic.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnKillTheMusic.setBounds(184, 83, 120, 23);
		contentPane.add(btnKillTheMusic);
		
		JLabel lblPerfomanceOptions = new JLabel("Perfomance options:");
		lblPerfomanceOptions.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPerfomanceOptions.setBounds(10, 11, 120, 14);
		contentPane.add(lblPerfomanceOptions);
		
		rdbtnIHaveLow = new JRadioButton("I have low RAM(<=2gb)");
		rdbtnIHaveLow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				memorySettings();
			}
		});
		rdbtnIHaveLow.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnIHaveLow.setBounds(10, 57, 170, 23);
		contentPane.add(rdbtnIHaveLow);
		
		rdbtnIHaveHigh = new JRadioButton("I have high RAM(>=2gb)");
		rdbtnIHaveHigh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				memorySettings();
			}
		});
		rdbtnIHaveHigh.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnIHaveHigh.setBounds(10, 83, 170, 23);
		contentPane.add(rdbtnIHaveHigh);
		
		rdbtnAutoRamDetection = new JRadioButton("Auto RAM detection");
		rdbtnAutoRamDetection.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				memorySettings();
			}
		});
		rdbtnAutoRamDetection.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnAutoRamDetection.setSelected(true);
		rdbtnAutoRamDetection.setBounds(10, 32, 170, 23);
		contentPane.add(rdbtnAutoRamDetection);
		
		//Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
	    group.add(rdbtnIHaveLow);
	    group.add(rdbtnIHaveHigh);
	    group.add(rdbtnAutoRamDetection);
		
		JLabel lblDiskUsage = new JLabel("Disk usage:");
		lblDiskUsage.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDiskUsage.setBounds(10, 190, 120, 14);
		contentPane.add(lblDiskUsage);
		
		textFieldSavePath = new JTextField();
		textFieldSavePath.setEditable(false);
		textFieldSavePath.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textFieldSavePath.setBounds(10, 241, 414, 20);
		contentPane.add(textFieldSavePath);
		textFieldSavePath.setColumns(10);
		
		JButton btnSetPath = new JButton("Set path");
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
		btnSetPath.setBounds(335, 211, 89, 23);
		contentPane.add(btnSetPath);
		
		comboUpdate = new JComboBox();
		comboUpdate.setToolTipText("0 = don't update");
		comboUpdate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboUpdate.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5"}));
		comboUpdate.setSelectedIndex(3);
		comboUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.changeUpdateVariable( Integer.parseInt((String)comboUpdate.getSelectedItem()));
			}
		});
		comboUpdate.setBounds(370, 33, 54, 20);
		contentPane.add(comboUpdate);
		
		JButton btnFreeAllMemory = new JButton("Free all memory");
		btnFreeAllMemory.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnFreeAllMemory.setToolTipText("This will destroy already generated words that are in memory, it will delete all inputed text, kill the music");
		btnFreeAllMemory.setBounds(314, 83, 110, 23);
		contentPane.add(btnFreeAllMemory);
		
		chckbxMeasureTimeNeeded = new JCheckBox("Measure time needed for each task");
		chckbxMeasureTimeNeeded.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				measureVariables = chckbxMeasureTimeNeeded.isSelected();
			}
		});
		chckbxMeasureTimeNeeded.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxMeasureTimeNeeded.setSelected(true);
		chckbxMeasureTimeNeeded.setBounds(182, 57, 242, 23);
		contentPane.add(chckbxMeasureTimeNeeded);
		
		txtrSeparatorWord = new JTextField();
		txtrSeparatorWord.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtrSeparatorWord.setToolTipText("Enter 1 character that symbolises new word.  NOTE THAT ALL CHARACTERS FROM OPTION STRING \"\" ARE RESERVED!");
		txtrSeparatorWord.setBounds(270, 143, 25, 20);
		contentPane.add(txtrSeparatorWord);
		txtrSeparatorWord.setColumns(10);
		
		JLabel lblMiscOptions = new JLabel("Misc options:");
		lblMiscOptions.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMiscOptions.setBounds(10, 123, 120, 14);
		contentPane.add(lblMiscOptions);
		
		JButton btnSetWordSeparator = new JButton("Set");
		btnSetWordSeparator.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				//private static String reservedCharactersFromOptions = "";
				String reservedCharactersFromOptions = Methods_Universal.getCharactersFromOptions( Main.getTxtWords(), separatorOption );
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
		btnSetWordSeparator.setBounds(300, 142, 50, 20);
		contentPane.add(btnSetWordSeparator);
		
		JLabel lblDontUseSpace = new JLabel("Don't use space as word separator, use this instead:");
		lblDontUseSpace.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDontUseSpace.setBounds(10, 146, 270, 14);
		contentPane.add(lblDontUseSpace);
		
		JLabel lblUpdateVariablesEach = new JLabel("Update variables each X seconds:");
		lblUpdateVariablesEach.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUpdateVariablesEach.setBounds(184, 36, 176, 14);
		contentPane.add(lblUpdateVariablesEach);
		
		JLabel lblNewLabel = new JLabel("Save word list to the following path:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(10, 215, 230, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblDontUseQuotes = new JLabel("Don't use backslash as option separator, use this instead:");
		lblDontUseQuotes.setForeground(Color.RED);
		lblDontUseQuotes.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDontUseQuotes.setBounds(10, 167, 294, 14);
		contentPane.add(lblDontUseQuotes);
		
		txtrSeparatorOption = new JTextField();
		txtrSeparatorOption.setToolTipText("");
		txtrSeparatorOption.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtrSeparatorOption.setColumns(10);
		txtrSeparatorOption.setBounds(300, 165, 25, 20);
		contentPane.add(txtrSeparatorOption);
		
		JButton btnSetOptionSeparator = new JButton("Set");
		btnSetOptionSeparator.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				String reservedCharactersFromOptions = Methods_Universal.getCharactersFromOptions( Main.getTxtWords(), separatorOption );
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
		btnSetOptionSeparator.setBounds(330, 165, 50, 20);
		contentPane.add(btnSetOptionSeparator);
	}
}
