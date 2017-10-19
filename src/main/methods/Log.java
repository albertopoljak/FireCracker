package main.methods;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.sun.javafx.tk.Toolkit;

import main.Main;

public final class Log {
	
	private static String n = System.getProperty("line.separator");
	private static String logPath = "log.txt";
	private static PrintWriter logOut;
	
	private Log(){};
	
	
	public static void write(String text){
		writeToTextArea(text);
		writeToFile(text);
	}
	
	public static void write(String text, int option){
		if( option==1 ){
			writeToTextArea(text);
		}else if( option==2 ){
			writeToFile(text);
		}else{
			writeToTextArea(text);
			writeToFile(text);
		}
	}
	
	public static void write(String text, char color){
		writeToTextArea(text);
		writeToFile(text);
	}
	
	/*
	 * Option:
	 * 1 = write only to text area
	 * 2 = write only to text file
	 * anything else = write to both
	 * Color:
	 * E or R = ERROR red
	 * S or G = SUCCES green
	 * W or B = WARNING blue
	 * I or anything else = INFO black
	 */
	public static void write(String text, int option, char color){
		if( option==1 ){
			writeToTextArea(text);
		}else if( option==2 ){
			writeToFile(text);
		}else{
			writeToTextArea(text);
			writeToFile(text);
		}
	}

	
	private static void writeToTextArea(String text){
		Main.log(text+n);
	}
	
	private static void writeToFile(String text){
		try{ 
			openPrintWriter(logPath) ;
			writeString(text);
			closePrintWriter();
		}catch (IOException e){
			writeToTextArea("Error creating file for saving!\n"+e);
		}
	}
	
	public static void openPrintWriter(String filePath) throws IOException{
		try {
			logOut = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
		} catch (IOException e) {
			throw new IOException(e);
		}
	}

	public static void closePrintWriter(){
		if( logOut!=null )
			logOut.close();
	}


	public static void writeString(String write){
		logOut.print(write+n);
	}	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * Appends string message to the textPanel. Importance colors the message:
	 * E=red, I=regular, S=green, W=blue
	 * where they represent:
	 * Error, Info, Success, Warning
	 */
		public static void Msg(String message, String importance){
			StyledDocument doc = Main.txtrLog.getStyledDocument();
			String date = new SimpleDateFormat("[HH:mm:ss]   ").format(Calendar.getInstance().getTime());
			javax.swing.text.Style style = Main.txtrLog.addStyle("I'm a Style", null);

			if(importance == "I")
					StyleConstants.setForeground(style, Color.darkGray);
			else if(importance == "W")
					StyleConstants.setForeground(style, Color.blue);
			else if(importance == "S"){
					StyleConstants.setForeground(style, Color.green);
					//Play error sounds, works only on windows
					//Runnable sound1 = (Runnable)Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.asterisk");
						//if(sound1 != null) sound1.run();
			}else if(importance == "E"){
					StyleConstants.setForeground(style, Color.red);
					//Toolkit.getDefaultToolkit().beep();	// play beep sound
			}

			try { doc.insertString(doc.getLength(), date+message+"\n",style); }
			catch (BadLocationException e){
				Main.txtrLog.setText("ERROR WITH ADDING STRING TO LOG! "+e);
			}
		}

}
