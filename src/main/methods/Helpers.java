package main.methods;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import main.Help;
import main.Main;

public class Helpers {

	private Helpers(){};
	
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
		String[][] stringsAsArray = new String[theStrings.size()][];
		for (int i=0; i<theStrings.size();i++) {
		  List<String> aList = theStrings.get(i);
		  stringsAsArray[i] = aList.toArray(new String[aList.size()]);
		}
		return stringsAsArray;
	}
	
	
	public static long totalNumOfElementsIn2DList( List<List<String>> input, int rows ){
		long counter = 0;
		for( int i=0; i<rows; i++)
			counter+=input.get(i).size();
		return counter;
	}
	
	
	public static long factorial( long n ){
		if(n==0)
			return 1;
		else return (n*factorial(n-1));
		
	}
	
	
	public static long totalExpectedGeneratedWords( List<List<String>> input ){
		long multiplier = 1;
		int rows = input.size();
		for( int i=0; i<rows; i++){
			multiplier*=input.get(i).size();
		}
		return factorial(rows)*multiplier;
	}
	
	
	public static String getAlgorithmComplexityString( long totalWords ){
		if( totalWords<100000 )
			return "Very low";
		else if( totalWords<5000000 )
			return "Low";
		else if( totalWords<50000000 )
			return "Medium";
		else if( totalWords<500000000 )
			return "High";
		else if( totalWords<1000000000 )
			return "Very High";
		else
			return "Almost Impossible!";
	}
	
	
	
	
	public static String getDate(){
		return new SimpleDateFormat("[HH:mm:ss]   ").format(Calendar.getInstance().getTime());
	}
	
}
