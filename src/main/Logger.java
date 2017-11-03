package main;
import java.awt.Color;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import main.methods.Helpers;

public class Logger extends JTextPane {

	private String newLine = System.getProperty("line.separator");;
	private String logFileName;
	private PrintWriter logOut;
	
	
	public Logger(){
		this.logFileName = "log.txt";
	};
	
	public Logger(String logFileName){
		this.logFileName = logFileName;
	};
	
	
	public void append(String text){
		appendToTextPanel(text, 'I');
		writeToFile(text);
	}
	
	public void append(String text, char color){
		appendToTextPanel(text, color);
		writeToFile(text);
	}
	
	public void appendDebug(String text){
		if( Settings.chckbxDebugMode.isSelected() ){
			appendToTextPanel(text, 'D');
			writeToFile(text);
		}
	}
	
	public void changeSavePath(String logFileName){
		this.logFileName = logFileName;
	}
	
	private void writeToFile(String text){
		try{ 
			openPrintWriter(logFileName) ;
			writeStringToFile(text);
			closePrintWriter();
		}catch (IOException e){
			appendToTextPanel("Error creating file for saving!\n"+e, 'E');
		}
	}
	
	private void openPrintWriter(String filePath) throws IOException{
		try {
			logOut = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
		} catch (IOException e) {
			throw new IOException(e);
		}
	}

	private void writeStringToFile(String write){
		logOut.print( Helpers.getDate() + write + newLine );
	}	
	
	private void closePrintWriter(){
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
	public void appendToTextPanel(String message, char importance){
		StyledDocument doc = this.getStyledDocument();
		//String date = new SimpleDateFormat("[HH:mm:ss]   ").format(Calendar.getInstance().getTime());
		javax.swing.text.Style style = this.addStyle("I'm a Style", null);

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
			doc.insertString(doc.getLength(), Helpers.getDate() + message + newLine ,style); 
		}catch (BadLocationException e){
			this.setText("ERROR WITH ADDING STRING TO LOG! "+e);
		}
	}

	
}
