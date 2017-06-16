import java.awt.BorderLayout;
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
import javax.swing.DefaultComboBoxModel;

public class Settings extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldSavePath;

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

	/**
	 * Create the frame.
	 */
	public Settings() {
		setTitle("Settings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnKillTheMusic = new JButton("Kill the music");
		btnKillTheMusic.setBounds(10, 11, 120, 23);
		contentPane.add(btnKillTheMusic);
		
		JLabel lblPerfomanceOptions = new JLabel("Perfomance options:");
		lblPerfomanceOptions.setBounds(10, 45, 120, 14);
		contentPane.add(lblPerfomanceOptions);
		
		JRadioButton rdbtnIHaveLow = new JRadioButton("I have low RAM(<=2gb)");
		rdbtnIHaveLow.setBounds(10, 91, 170, 23);
		contentPane.add(rdbtnIHaveLow);
		
		JRadioButton rdbtnIHaveHigh = new JRadioButton("I have high RAM(>=2gb)");
		rdbtnIHaveHigh.setBounds(10, 117, 170, 23);
		contentPane.add(rdbtnIHaveHigh);
		
		JRadioButton rdbtnAutoRamDetection = new JRadioButton("Auto RAM detection");
		rdbtnAutoRamDetection.setSelected(true);
		rdbtnAutoRamDetection.setBounds(10, 66, 170, 23);
		contentPane.add(rdbtnAutoRamDetection);
		
		JLabel lblDiskUsage = new JLabel("Disk usage:");
		lblDiskUsage.setBounds(10, 160, 120, 14);
		contentPane.add(lblDiskUsage);
		
		JCheckBox chckbxSaveWordlistTo = new JCheckBox("Save word list to the following path:");
		chckbxSaveWordlistTo.setBounds(6, 181, 200, 23);
		contentPane.add(chckbxSaveWordlistTo);
		
		textFieldSavePath = new JTextField();
		textFieldSavePath.setEnabled(false);
		textFieldSavePath.setBounds(10, 211, 414, 20);
		contentPane.add(textFieldSavePath);
		textFieldSavePath.setColumns(10);
		
		JButton btnSetPath = new JButton("Set path");
		btnSetPath.setEnabled(false);
		btnSetPath.setBounds(335, 181, 89, 23);
		contentPane.add(btnSetPath);
		
		JCheckBox chckbxUpdateVariablesEach = new JCheckBox("Update variables each X seconds:");
		chckbxUpdateVariablesEach.setSelected(true);
		chckbxUpdateVariablesEach.setBounds(182, 66, 188, 23);
		contentPane.add(chckbxUpdateVariablesEach);
		
		JComboBox comboBoxSeconds = new JComboBox();
		comboBoxSeconds.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5"}));
		comboBoxSeconds.setSelectedIndex(0);
		comboBoxSeconds.setBounds(376, 67, 48, 20);
		contentPane.add(comboBoxSeconds);
		
		JButton btnFreeAllMemory = new JButton("Free all memory");
		btnFreeAllMemory.setToolTipText("This will destroy already generated words that are in memory, it will delete all inputed text, kill the music");
		btnFreeAllMemory.setBounds(140, 11, 131, 23);
		contentPane.add(btnFreeAllMemory);
		
		JCheckBox chckbxMeasureTimeNeeded = new JCheckBox("Measure time needed for each task");
		chckbxMeasureTimeNeeded.setSelected(true);
		chckbxMeasureTimeNeeded.setBounds(182, 91, 242, 23);
		contentPane.add(chckbxMeasureTimeNeeded);
	}
}
