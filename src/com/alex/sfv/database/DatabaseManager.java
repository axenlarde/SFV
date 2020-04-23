package com.alex.sfv.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import com.alex.sfv.utils.Variables;

/**********************************
 * Class used to write and read into the
 * database file
 * 
 * @author RATEL Alexandre
 **********************************/
public class DatabaseManager
	{
	/**
	 * Variables
	 */
	private String databaseFileName, databasePath;
	private StringBuffer line;
	private File databaseFile;
	
	/***************
	 * Constructeur
	 ***************/
	public DatabaseManager(String databaseFileName, String databasePath)
		{
		super();
		this.databaseFileName = databaseFileName;
		this.databasePath = databasePath;
		databaseFile = new File(databasePath+"\\"+databaseFileName);
		}
	
	
	/*******
	 * Class used to write a new entry
	 */
	public void writeNewEntry(DatabaseEntry de) throws Exception
		{
		line = new StringBuffer("");
		BufferedWriter myBuffer = new BufferedWriter(new FileWriter(databaseFile, true));
		
		line.append(de.getPath());
		
		myBuffer.write(line.toString()+"\r\n");
		
		myBuffer.flush();
		myBuffer.close();
		
		Variables.getLogger().debug("The following entry has been added to the database : "+line.toString());
		}
	
	/***********
	 * Method used to check if an entry already exists in the database
	 * true : the entry exists in the database
	 */
	public boolean CheckEntry(DatabaseEntry de) throws Exception
		{
		if(databaseFile.exists())
			{
			BufferedReader myBuffer = new BufferedReader(new FileReader(databaseFile));
			
			String oneLine = "";
			
			while(true)
				{
				oneLine = myBuffer.readLine();
				if(oneLine == null)return false;
				else if(oneLine.equals(de.getPath()))return true;
				}
			}
		else
			{
			Variables.getLogger().debug("The database file doesn't exist");
			return false;
			}
		}
	
	/*2017*//*RATEL Alexandre 8)*/
	}

