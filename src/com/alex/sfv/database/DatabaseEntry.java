package com.alex.sfv.database;

/**********************************
 * Class used to store a new database entry
 * 
 * @author RATEL Alexandre
 **********************************/
public class DatabaseEntry
	{
	/**
	 * Variables
	 */
	private String path;
	private String separator;

	/***************
	 * Constructeur
	 ***************/
	public DatabaseEntry(String path, String Separator)
		{
		super();
		this.path = path;
		}
	
	/******
	 * Method used to return all the value of a database entry concatenate into a line
	 */
	public String getLine()
		{
		return path;//At the moment, we just need to return only the path
		}
	
	public String getPath()
		{
		return path;
		}

	public void setPath(String path)
		{
		this.path = path;
		}
	
	/*2017*//*RATEL Alexandre 8)*/
	}

