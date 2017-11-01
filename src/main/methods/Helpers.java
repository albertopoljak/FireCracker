package main.methods;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import main.Help;

public class Helpers {

	
	/*
	 * Returns text from file
	 */
	public static  String readTextFromFile(String filename){
		BufferedReader br = null;
		InputStreamReader isr = null;
		InputStream is = null;

		try {
			
			is = Help.class.getResourceAsStream(filename);
		    isr = new InputStreamReader(is);
		    br = new BufferedReader(isr);
			
			String sCurrentLine;
			StringBuilder sb = new StringBuilder();
			
			while ((sCurrentLine = br.readLine()) != null) {
				sb.append(sCurrentLine);
		        sb.append(System.lineSeparator());
			}
			
			return sb.toString();
			
		}catch (Exception e) {
			return e.toString();
		}finally {
			try {
				if (br != null)
					br.close();
				
				if (isr != null)
					isr.close();
					
				if (is != null)
					is.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
	
	/*
	 * Method to clear list (.clear leaves memory footprint for the largest element, meaning N*nullElements)
	 */
		private static ArrayList<String> clearList() {
			ArrayList<String> newList = new ArrayList<String>();
			return newList;
		}
	
	
	
	/*
	 * Convert List<List<String>> to String[][] 
	 * Only temporal solution until method combinations2d is reworked so it can work with lists instead of arrays
	 */
	
	public static String[][] convertListToStringArray( List<List<String>> theStrings ){
		//List<List<String>> theStrings ... coming from somewhere
		String[][] stringsAsArray = new String[theStrings.size()][];
		for (int i=0; i<theStrings.size();i++) {
		  List<String> aList = theStrings.get(i);
		  stringsAsArray[i] = aList.toArray(new String[aList.size()]);
		}
		return stringsAsArray;
	}
	
}
