package com.alex.sfv.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**********************************
 * Class used to store some useful methods
 * 
 * @author RATEL Alexandre
 **********************************/
public class UsefulMethod
	{
	
	/**************
	 * Method used to read value from the configuration file
	 * @throws Exception 
	 */
	public static String getValue(String valueName) throws Exception
		{
		Variables.getLogger().debug("We look for the following value : "+valueName);
		for(String[] tab : Variables.getConfigurationFile().get(0))
			{
			if(tab[0].equals(valueName))
				{
				Variables.getLogger().debug("Returned value : "+tab[1]);
				return tab[1];
				}
			}
		throw new Exception("The following value has not been found in the configuration file : "+valueName);
		}
	
	/**
	 * Method used to get the last occurence of a splitted string
	 */
	public static String getLastSplit(String s, String divider)
		{
		String tab[] = s.split(divider);
		return tab[tab.length-1];
		}
	
	
	/*****
	 * Method used to retrieve a matched result
	 * @throws Exception 
	 */
	public static String getRegex(String s, String pattern) throws Exception
		{
		Pattern pat = Pattern.compile(pattern); 
		Matcher mat = pat.matcher(s);
		String temp = null;
		
		while(mat.find())
			{
			temp = mat.group();
			}
		
		if(temp == null)
			{
			throw new Exception("The pattern \""+pattern+"\" has not been found in the given String : "+s);
			}
		else
			{
			Variables.getLogger().debug("Found regex : "+temp);
			return temp;
			}
		}
	
	
	
	
	/*2017*//*RATEL Alexandre 8)*/
	}

