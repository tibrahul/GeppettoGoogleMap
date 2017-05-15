package com.npb.gb.utils;

import java.util.ArrayList;
/**
 * 
 * @author Dan Castillo
 * Date Created: 02/27/2014</br>
 * @since .35</p>  
 *
 * 
 * The purpose of this class is to serve as a generic parser/builder</br>
 * for things that need to be put into a string for insertion into the db</br>
 * For example if class A has a parent child relationship with class B</br>
 * and an instance of class A has a reference to three instances of class B</br>
 * when class A is stored in the DB the id's from class B that are the foreign keys</br>
 * held in class A will be stored as a comma delimited string that is built and parsed</br>
 * by this class</p>
 * 
 * please note a version of this class existed in Geppetto version .2 and was called GpMultiSelectParserBuilder</p>
 * 
 *   
 * 
 * 
 */
public class GpGenericRecordParserBuilder {

	public static String GP_STD_REC_DELIM = ";";
	public static String GP_STD_FLD_DELIM = ",";
	
	public static String buildDelimitedString(ArrayList<String> thevalues){
		StringBuffer thestringbuff = new StringBuffer();
		if (thevalues == null) {
			return thestringbuff.toString();
		}
		int i = 0;
		for(String astr: thevalues){
			thestringbuff.append(astr);
			if(i != thevalues.size()-1){
				thestringbuff.append(GpGenericRecordParserBuilder.GP_STD_FLD_DELIM);	
			}
			i++;
			
		}
		thestringbuff.append(GpGenericRecordParserBuilder.GP_STD_REC_DELIM);
		return thestringbuff.toString();
	}
	
	public static String[] parseDelimitedString(String thestr) {
		String[] tokens = null;
		if(!thestr.endsWith(GP_STD_REC_DELIM)){
			tokens = thestr.split(
					GpGenericRecordParserBuilder.GP_STD_REC_DELIM);
			
		}else{
			String substr = thestr.substring(0, thestr.length()-1);
			tokens = substr.split(
					GpGenericRecordParserBuilder.GP_STD_FLD_DELIM);
			
		}
		return tokens;
	}

}
