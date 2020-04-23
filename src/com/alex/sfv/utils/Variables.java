package com.alex.sfv.utils;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/**********************************
 * Class used to store static variables
 * 
 * @author RATEL Alexandre
 **********************************/
public class Variables
	{
	/****
	 * Variables
	 ***/
	public enum vType {simple,complexe};
	
	private static String softwareName;
	private static String softwareVersion;
	private static String fileName;
	private static String configFileName;
	private static String databaseFileName;
	private static String mainDirectory;
	private static ArrayList<String[][]> configurationFile;
	private static String fileVersion;
	private static String backupDirectoryName;
	private static vType versionningType;
	
	private static Logger logger;
	
	/***************
	 * Constructor
	 ***************/
	public Variables()
		{
		configFileName = "SFVConfigFile.xml";
		databaseFileName = "SFVDatabase.xml";
		configurationFile = new ArrayList<String[][]>();
		
		//More if needed
		}

	public static String getFileName()
		{
		return fileName;
		}

	public static void setFileName(String fileName)
		{
		Variables.fileName = fileName;
		}

	public static String getConfigFileName()
		{
		return configFileName;
		}

	public static void setConfigFileName(String configFileName)
		{
		Variables.configFileName = configFileName;
		}

	public static String getDatabaseFileName()
		{
		return databaseFileName;
		}

	public static void setDatabaseFileName(String databaseFileName)
		{
		Variables.databaseFileName = databaseFileName;
		}

	public static String getMainDirectory()
		{
		return mainDirectory;
		}

	public static void setMainDirectory(String mainDirectory)
		{
		Variables.mainDirectory = mainDirectory;
		}

	public static String getSoftwareName()
		{
		return softwareName;
		}

	public static void setSoftwareName(String softwareName)
		{
		Variables.softwareName = softwareName;
		}

	public static String getSoftwareVersion()
		{
		return softwareVersion;
		}

	public static void setSoftwareVersion(String softwareVersion)
		{
		Variables.softwareVersion = softwareVersion;
		}

	public static Logger getLogger()
		{
		return logger;
		}

	public static void setLogger(Logger logger)
		{
		Variables.logger = logger;
		}

	public static ArrayList<String[][]> getConfigurationFile()
		{
		return configurationFile;
		}

	public static void setConfigurationFile(ArrayList<String[][]> configurationFile)
		{
		Variables.configurationFile = configurationFile;
		}

	public static String getFileVersion()
		{
		return fileVersion;
		}

	public static void setFileVersion(String fileVersion)
		{
		Variables.fileVersion = fileVersion;
		}

	public static String getBackupDirectoryName()
		{
		return backupDirectoryName;
		}

	public static void setBackupDirectoryName(String backupDirectoryName)
		{
		Variables.backupDirectoryName = backupDirectoryName;
		}

	public static vType getVersionningType()
		{
		return versionningType;
		}

	public static void setVersionningType(vType versionningType)
		{
		Variables.versionningType = versionningType;
		}
	
	/*2017*//*RATEL Alexandre 8)*/
	}

