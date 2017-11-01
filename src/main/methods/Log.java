package main.methods;
import java.awt.Color;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import main.Main;

public final class Log {
	
	private static String n = System.getProperty("line.separator");
	private static String logPath = "log.txt";
	private static PrintWriter logOut;
	
	
	private Log(){};
	
	
	public static void write(String text){
		appendToTextPanel(text, 'I');
		writeToFile(text);
	}
	
	public static void write(String text, char color){
		appendToTextPanel(text, color);
		writeToFile(text);
	}
	
	public static void writeDebug(String text){
		appendToTextPanel(text, 'D');
		writeToFile(text);
	}
	
	
	private static void writeToFile(String text){
		try{ 
			openPrintWriter(logPath) ;
			writeStringToFile(text);
			closePrintWriter();
		}catch (IOException e){
			appendToTextPanel("Error creating file for saving!\n"+e, 'E');
		}
	}
	
	public static void openPrintWriter(String filePath) throws IOException{
		try {
			logOut = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
		} catch (IOException e) {
			throw new IOException(e);
		}
	}

	public static void writeStringToFile(String write){
		logOut.print( getDate() + write + n );
	}	
	
	public static void closePrintWriter(){
		if( logOut!=null )
			logOut.close();
	}
	
	/*
	 * Appends string message to the textPanel. Importance colors the message:
	 * E or R = ERROR red
	 * S or G = SUCCES green
	 * W or B = WARNING blue
	 * I or anything else = INFO black
	 * D = debug
	 */
	public static void appendToTextPanel(String message, char importance){
		StyledDocument doc = Main.txtrLog.getStyledDocument();
		//String date = new SimpleDateFormat("[HH:mm:ss]   ").format(Calendar.getInstance().getTime());
		javax.swing.text.Style style = Main.txtrLog.addStyle("I'm a Style", null);

		if (importance == 'I' || importance== 'S' )
			StyleConstants.setForeground(style, Color.darkGray);
		else if (importance == 'W' || importance== 'B' )
			StyleConstants.setForeground(style, Color.blue);
		else if (importance == 'S' || importance== 'G' ){
			StyleConstants.setForeground(style, Color.green);
		}else if (importance == 'E' || importance== 'R' ){
			StyleConstants.setForeground(style, Color.red);
			Toolkit.getDefaultToolkit().beep();	// play beep sound
		}else if (importance == 'D' ){
			StyleConstants.setForeground(style, Color.PINK);
		}

		try { 
			doc.insertString(doc.getLength(), getDate() + message + n ,style); 
		}catch (BadLocationException e){
			Main.txtrLog.setText("ERROR WITH ADDING STRING TO LOG! "+e);
		}
	}
	
	
	public static String getDate(){
		return new SimpleDateFormat("[HH:mm:ss]   ").format(Calendar.getInstance().getTime());
	}

}
