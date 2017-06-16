import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;

public class Info extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void newScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Info frame = new Info();
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
	public Info() {
		setTitle("Info (resizable)");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);
		
		JTextArea txtrT = new JTextArea();
		txtrT.setWrapStyleWord(true);
		txtrT.setLineWrap(true);
		scrollPane.setViewportView(txtrT);
		txtrT.setFont(new Font("Monospaced", Font.PLAIN, 12));
		txtrT.setText("Enter all possible words, choose word options and click Hack!\r\nProgram will make a wordlist.txt file inside directory of this exe.\r\n\r\nUsage examples:\r\na) You know the words but you can't remember in which order they go\r\nb) You know all the letters in the word but can't remember in which order they go\r\nc) Combination of a and b\r\nd) Special casses and combining input\r\ne) Universal options\r\n\r\nExplanation:\r\na) Lets say that your password which you forgot is  \"Best123PasswordEver\" , and you can still recall the words \"Best\" , \"Ever\" , \"Password\" and \"123\" but you don't know the combination! So instead of trying N! combinations, in this case 4!=24 combinations, and guessing manually like 123BestPasswordEver, BestPasswordEver123, PasswordBest123Ever....  you just type those words in this program and voila! It will generate all 24 possible combinations and one of those will for sure be exactly \"Best123PasswordEver\".\r\nThis becomes expecially usefull if you have many words because number of combinations is N! so for 10 words it would be 10!=3628800 combinations! Note that this can consume a lot of memory! Formula for max memory is:  N! * W * 2 / 1024 / 1024 MB\r\nWhere N is number of words and W is maximum length of string.\r\n\r\nExample of input:\r\nBest Ever Password 123\r\n\r\nb) Lets say that your password which you forgot is  \"AXBYCZ\" , and you can still recall the letters 'A' , 'B' , 'C' , 'X' , 'Y' , 'Z'  but you don't know their combination! So instead of trying N! combinations, in this case 6!=720 combinations, and guessing manually like ABXYCZ , AXBCYZ , ABCZXY....  you just type those letters in this program and voila! It will generate all 720 possible combinations and one of those will for sure be exactly \"AXBYCZ\" \r\nThis becomes expecially usefull if you have many letters because number of combinations is N! so for 10 letters it would be 10!=3628800 combinations! Note that this can consume a lot of memory! Formula for memory is:  N! * W * 2 / 1024 / 1024 MB\r\nWhere N is number of letters and W is length of password.\r\n\r\nExample of input:\r\nABCXYZ\\\\b\r\n\r\nThis will create all combinations of strings that are the same length as input string(in this case number of characters in input string is 6)! For example in this case it will NEVER create words like  ABC , XY , Z , BCXYZ... instead it will create words with length of 6 characters:  ABXYZC , XYZABC , BCAXZY  ... \r\n\r\nSo if you remember more characters that there is in your password, for example you know that your passwords consisted of  'A' , 'B' , 'C' , 'X' , 'Y' , 'Z' but you'r not sure if there was a 'D' in there (no homo) and you know the password length is 6 then your input would be:\r\n12345\\\\b\\6\r\n\r\nc) Combination of above 2; When you know all the words but some of the words are blury in your memory , for example: Your forgoten password is \"DanceNight24135\" and you still remember the words \"Night\" and \"Dance\" but for the third word you only remember that it had characters from 1-5. Your input would then be:\r\nNight Dance 12345\\\\b\r\nIf you remember that \"Night\" and \"Dance\" went in this order \"DanceNight\" then just input:\r\nDanceNight 12345\\\\b\r\n\r\nd) Special casses and combining input\r\n\r\n\\\\l  - Use the word in its lowercase\r\n\tExample input: HORSE\\\\l FLY\r\n\tGenerated words: HORSEFLY , FLYHORSE ,horseFLY , FLYhorse \r\n\r\n\\\\u  - Use the word in its upercase\r\n\tExample input: horse\\\\u fly\r\n\tGenerated words: horsefly , flyhorse , HORSEfly , flyHORSE\r\n\r\n\\\\if  - Include inverz case for first character (lower to uppper and upper to lower)\r\n\tExample input: horse\\\\if fly\r\n\tGenerated words: horsefly , flyhorse , Horsefly , flyHorse\r\n\tExample input: HORSE\\\\if fly\r\n\tGenerated words: HORSEfly , flyHORSE hORSEfly, flyhORSE \r\n\r\n\\\\il  - Include inverz case for last character (lower to uppper and upper to lower)\r\n\tExample input: horse\\\\il fly\r\n\tGenerated words: horsefly , flyhorse , horsEfly , flyhorsE\r\n\tExample input: HORSE\\\\il fly\r\n\tGenerated words: HORSEfly , flyHORSE HORSefly, flyHORSe \r\n\r\n\\\\isX  - Include inverz case for specific letter X (case doesn't affect X meaning no difference between \\\\iso and \\\\isO)\r\n\tExample input: horse\\\\isO fly\r\n\tGenerated words: horsefly , flyhorse , hOrsefly , flyhOrse \r\n\t\r\nNote: If there are more letters X in the word then more words will be created example:\r\n\tInput: alah\\\\isA fly\r\n\tGenerated words: alahfly , flyalah , Alahfly  , flyAlah , alAhfly , flyalAh , AlAhfly , flyAlAh \r\n\r\nNote: If you want to invert letter at specific specific letter, not all of them (this saves resourses and time because less words are created) then you input:\r\n\tExample input: alah\\\\ip2 fly\r\n\tGenerated words: alahfly , flyalah , alAhfly  , flyalAh\r\n\r\n\\\\ia  -  Include inverz case for all letters\r\n\tExample input: horse fly\\\\ia\r\n\tGenerated words: horsefly , flyhorse , horseFly , Flyhorse , horsefLy fLyhorse , horseflY , flYhorse \r\n\r\n\r\ne) Universal options\r\n\r\n\\\\sb\"%S\"  - Add specific string %S at beginning of each generated word example:\r\n\tAlready generated words: Europe America Asia\r\n\tYour input: \\\\s\"Continent_\"\r\n\tFinal generated words: Continent_Europe Continent_America Continent_Asia\r\n\r\nNote that original words will be overwritten by added string meaning that even if you had word \"Europe\" at beginning , that word will not appear in dictionary! Word \"Continent_Europe \" replaces it! If you want to preserve original words then your input would be: \\\\sbc\"Continent_\"  example:\r\n\tAlready generated words: Europe America Asia\r\n\tYour input: \\\\sbc\"Continent_\"\r\n\tFinal generated words: Europe America Asia Continent_Europe Continent_America Continent_Asia\r\n\r\n\r\n\\\\se\"%S\"  - Add specific string %S at the end of each generated word example:\r\n\tAlready generated words: Europe America Asia\r\n\tYour input: \\\\se\"_Continent\"\r\n\tFinal generated words:  Europe_Continent America_Continent Asia_Continent\r\n\r\nNote that original words will be overwritten by added string meaning that even if you had word \"Europe\" at beginning , that word will not appear in dictionary! Word \"Europe_Continent \" replaces it! If you want to preserve original words then your input would be: \\\\sec\"_Continent\"  example:\r\n\tAlready generated words: Europe America Asia\r\n\tYour input: \\\\sec\"_Continent\"\r\n\tFinal generated words: Europe America Asia Europe_Continent America_Continent Asia_Continent\r\n\r\n");
	}

}
