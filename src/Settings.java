import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Settings extends JFrame {

	private static JPanel contentPane;
	private static JTextField textFieldSavePath;
	private static JTextField separator;
	private static JComboBox comboUpdate;
	private static JCheckBox chckbxMeasureTimeNeeded;
	private static JRadioButton rdbtnIHaveLow;
	private static JRadioButton rdbtnIHaveHigh;
	private static JRadioButton rdbtnAutoRamDetection;
	private static String reservedCharacters  = "ABCEFIJOPSUX\\\"1234567890,";

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
			MainProgram.setLowMemoryState(true);
		else if(rdbtnIHaveHigh.isSelected())
			MainProgram.setLowMemoryState(false);
		else if(rdbtnAutoRamDetection.isSelected())
			MainProgram.autoMemoryDetection();
	}
	
	
	/**
	 * Create the frame.
	 */
	public Settings() {
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
		textFieldSavePath.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textFieldSavePath.setBounds(10, 241, 414, 20);
		contentPane.add(textFieldSavePath);
		textFieldSavePath.setColumns(10);
		
		JButton btnSetPath = new JButton("Set path");
		btnSetPath.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if(new File(textFieldSavePath.getText()).exists()){
					MainProgram.changeSavePath(textFieldSavePath.getText());
				}else{
					System.out.println("File does not exist!");
				}
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
				MainProgram.changeUpdateVariable( Integer.parseInt((String)comboUpdate.getSelectedItem()));
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
				MainProgram.setMeasureVariable(chckbxMeasureTimeNeeded.isSelected());
			}
		});
		chckbxMeasureTimeNeeded.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxMeasureTimeNeeded.setSelected(true);
		chckbxMeasureTimeNeeded.setBounds(182, 57, 242, 23);
		contentPane.add(chckbxMeasureTimeNeeded);
		
		separator = new JTextField();
		separator.setFont(new Font("Tahoma", Font.PLAIN, 11));
		separator.setToolTipText("Enter 1 character that symbolises new word.  NOTE THAT ALL CHARACTERS FROM OPTION STRING \"\" ARE RESERVED!");
		separator.setBounds(290, 145, 25, 20);
		contentPane.add(separator);
		separator.setColumns(10);
		
		JLabel lblMiscOptions = new JLabel("Misc options:");
		lblMiscOptions.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMiscOptions.setBounds(10, 123, 120, 14);
		contentPane.add(lblMiscOptions);
		
		JButton btnSet = new JButton("Set");
		btnSet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				//private static String reservedCharactersFromOptions = "";
				String reservedCharactersFromOptions = MainProgram.getCharactersFromOptions();
				if( separator.getText()!=null &&  separator.getText().length()>0 ){
					if( inputContainsCharFromString( String.valueOf(separator.getText().charAt(0)).toUpperCase(), reservedCharacters ) || inputContainsCharFromString( String.valueOf(separator.getText().charAt(0)), reservedCharactersFromOptions ))
						System.out.println("Character is reserved!\nReserved characters are(inside the quotes, including their lowercase):\""+reservedCharacters+"\" \n,plus these characters from options(inside the quotes, case sensitive):\""+reservedCharactersFromOptions+"\"");
					else
						MainProgram.changeWordSeparator( separator.getText().charAt(0) );
				}else
					System.out.println("Input a character first!");
			}
		});
		btnSet.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSet.setBounds(320, 144, 50, 23);
		contentPane.add(btnSet);
		
		JLabel lblDontUseSpace = new JLabel("Don't use space as word separator, use this instead:");
		lblDontUseSpace.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDontUseSpace.setBounds(10, 148, 270, 14);
		contentPane.add(lblDontUseSpace);
		
		JLabel lblUpdateVariablesEach = new JLabel("Update variables each X seconds:");
		lblUpdateVariablesEach.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUpdateVariablesEach.setBounds(184, 36, 176, 14);
		contentPane.add(lblUpdateVariablesEach);
		
		JLabel lblNewLabel = new JLabel("Save word list to the following path:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(10, 215, 230, 14);
		contentPane.add(lblNewLabel);
	}
}
