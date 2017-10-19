/*

package main.methods;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import main.Info;
import main.Main;

public class Universal {

	
	
	
	private static String inverseFirstChar(String word){
		if( word.length()>0 ){
			if ( Helpers.isCharLowerCase(word.charAt(0)) ) {
				return word.substring(0, 1).toUpperCase() + word.substring(1);
			}else if ( Helpers.isCharUpperCase(word.charAt(0)) ) {
				return word.substring(0, 1).toLowerCase() + word.substring(1);
			}else
				return null;
				//Returned null to avoid duplicates in case of first char being a number 
				//It's expected that this method will return a different string but if number is first it will return the same string(that's why return null)
		}else
			return null;
	}
	
	
	private static String inverseLastChar(String word){
		int length = word.length();
		
		//Newline shouldn't be in string word
		word = word.replace("\n", "");
		
		if( length>0 ){
			if ( isCharLowerCase(word.charAt( length-1) ) ) {
				return word.substring( 0, length-1 )  + word.substring( length-1, length ).toUpperCase();
			}else if ( isCharUpperCase(word.charAt( length-1 ) ) ) {
				return word.substring( 0, length-1 )  + word.substring( length-1, length ).toLowerCase();
			}else
				return null;
				//Returned null to avoid duplicates in case of first char being a number 
				//It's expected that this method will return a different string but if number is first it will return the same string(that's why return null)
		}else
			return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static void printArrayList(ArrayList<String> input){
		int i;
		int size = input.size();
		Main.log("Started printing working list:\n");
		
		for( i=0 ;i<size ;i++ )
			Main.log(input.get(i));
		
		Main.log("Ended printing working list.\n");
	}
	
	
	*/
	
	/*
	 * Prints 2 dim array
	 *//*
	private static void print2DimArray(String[][] current){
		for (String[] up: current) {
		    for (String down: up) {
		    	if(down!=null)
		    		System.out.println(down);
		    		//System.out.println();
		    }
		}
	}
	
	*/
	/*
	 * Returns the number of elements in the first index of 2 dimensional array
	 *//*
	private static int getLen1(String aray[][]){
		int len = 0;
		 for (int i=0;i<aray.length;i++)
			 if(aray[i][0]!=null)
					len++;
				else
					break;
		return len;
	}
		
		*/
	/*
	 * Returns the number of elements in the second index of 2 dimensional array
	 *//*
	private static int getLen2(String aray[]){
		int len = 0;
		for(String s: aray)
			if(s!=null)
				len++;
			else
				break;
		return len;
	}
	

		
	
	
	
	
	
}*/
