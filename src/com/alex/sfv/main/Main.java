package com.alex.sfv.main;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.jws.soap.SOAPBinding.Use;

import org.apache.commons.io.FileUtils;

import com.alex.sfv.database.DatabaseEntry;
import com.alex.sfv.database.DatabaseManager;
import com.alex.sfv.utils.UsefulMethod;
import com.alex.sfv.utils.Variables;
import com.alex.sfv.utils.initLogging;
import com.alex.sfv.utils.xMLGear;
import com.alex.sfv.utils.xMLReader;
import com.alex.sfv.utils.Variables.vType;

/**********************************
 * Class used to launch the SFV application
 * 
 * @author RATEL Alexandre
 **********************************/
public class Main
	{
	/**
	 * Variables
	 */
	private File targetFile;
	private String targetDirectory;
	private String separator = ",";
	
	public Main(String[] tabS)
		{
		Variables.setMainDirectory(tabS[1]);
		Variables.setFileName(tabS[0]);
		Variables.setSoftwareName("SFV : Simple File Versionning");
		Variables.setSoftwareVersion("1.0");
		
		init();//Start app
		}
	
	/*****
	 * Used to initiate the application
	 *****/
	private void init()
		{
		/**************************************
		 * Initialisation de la journalisation
		 **************************************/
		Variables.setLogger(initLogging.init(Variables.getMainDirectory()+"\\SFV_LogFile.txt"));
		Variables.getLogger().info("\r\n");
		Variables.getLogger().info("#### Entering application");
		Variables.getLogger().info("## Welcome to : \""+Variables.getSoftwareName()+"\" version "+Variables.getSoftwareVersion());
		Variables.getLogger().info("## Author : RATEL Alexandre : 2017");
		/**************/
		
		new Variables();
		
		//We get the configuration file
		ArrayList<String> list = new ArrayList<String>();
		list.add("config");
		
		try
			{
			Variables.setConfigurationFile(xMLGear.getResultListTab(xMLReader.fileRead(Variables.getMainDirectory()+"\\"+Variables.getConfigFileName()), list));
			
			Variables.getLogger().info("The configuration file has been successfuly read");
			}
		catch(Exception E)
			{
			Variables.getLogger().error("Error while reading the configuration file : "+E.getMessage(),E);
			Variables.getLogger().error("We will now exit the program");
			System.exit(0);
			}
		
		process();
		}
	
	/**
	 * Method used to process the given file 
	 */
	private void process()
		{
		/**
		 * First we manage backup directory
		 */
		
		try
			{
			//We check if the file exists
			targetFile = new File(Variables.getFileName());
			if(!targetFile.exists())throw new Exception("The file doesn't exist");
			
			//We get the selected file directory path
			targetDirectory = targetFile.getParent();
			Variables.getLogger().debug("Target directory : "+targetDirectory);
			
			//We check if a backup directory exists and if not we create it
			String backupDirectory = targetDirectory+"\\"+UsefulMethod.getValue("versionfiledirectoryname");
			Variables.getLogger().debug("We check for the following backup directory : "+backupDirectory);
			File targetBackupDirectory = new File(backupDirectory);
			if(targetBackupDirectory.exists() && targetBackupDirectory.isDirectory())
				{
				Variables.getLogger().info("The backup directory exists so we don't need to create it");
				}
			else
				{
				//We create it
				Variables.getLogger().info("The backup directory doesn't exist so we create it");
				Variables.getLogger().info("Creation of the directory named : "+UsefulMethod.getValue("versionfiledirectoryname"));
				targetBackupDirectory.mkdir();
				Variables.getLogger().info("Backup directory created");
				}
			Variables.setBackupDirectoryName(backupDirectory);
			Variables.getLogger().info("End of backup directory management");
			}
		catch (Exception e)
			{
			Variables.getLogger().error("An error occured while managing the backup directory. We will now exit : "+e.getMessage(), e);
			System.exit(0);
			}
		
		/**
		 * Second we manage the file backup
		 */
		try
			{
			//We get the file name
			Variables.getLogger().info("File name : "+Variables.getFileName());
			String fileName = UsefulMethod.getLastSplit(Variables.getFileName(), "\\\\");
			Variables.getLogger().info("Extracted file name : "+fileName);
			
			//We get and remove the extension
			String fileExtension = UsefulMethod.getLastSplit(fileName, "\\.");
			String newFileName = fileName.replace("."+fileExtension, "");
			
			//We check if the file has already been backed up
			boolean managed = false;
			if(Pattern.matches(".*_v\\d+\\.\\d+", newFileName))
				{
				//Versionning complexe : 1.0
				Variables.setVersionningType(vType.complexe);
				//The file has already been backed up, so we have to retrieve the current version
				Variables.setFileVersion(UsefulMethod.getRegex(newFileName, "\\d+\\.\\d+"));
				
				managed = true;
				}
			else if(Pattern.matches(".*_v\\d+", newFileName))
				{
				//Versionning simple : 1
				Variables.setVersionningType(vType.simple);
				//The file has already been backed up, so we have to retrieve the current version			
				Variables.setFileVersion(UsefulMethod.getRegex(newFileName, "\\d+"));
				managed = true;
				}
			else
				{
				//We retrieve the versionning type to apply from the configuration file
				Variables.setVersionningType(vType.valueOf(UsefulMethod.getValue("versionningtype")));
				//The file has never been backed up, so we start the versionning from scratch
				if(Variables.getVersionningType().equals(vType.complexe))
					{
					Variables.setFileVersion("1.0");
					}
				else
					{
					Variables.setFileVersion("1");
					}
				}
			
			Variables.getLogger().info("Current file version : "+Variables.getFileVersion());
			
			//We process the new file name with the version
			if(managed)
				{
				Variables.getLogger().info("The file is already managed");
				if(Variables.getVersionningType().equals(vType.complexe))
					{
					//We have to get the main version and the subversion
					String[] tabV = Variables.getFileVersion().split("\\.");
					int mainVersion = Integer.parseInt(tabV[0]);
					int subVersion = Integer.parseInt(tabV[1]);
					
					//Then we have to check if we reached the max subversion and increase what is needed
					if(subVersion >= Integer.parseInt(UsefulMethod.getValue("maxsubversion")))
						{
						subVersion = 0;
						mainVersion++;
						}
					else
						{
						subVersion++;
						}
					
					//Finally we replace the version in the file name
					newFileName = newFileName.replaceAll("_v\\d+\\.\\d+", "_v"+mainVersion+"."+subVersion);
					}
				else
					{
					int newVersion = Integer.parseInt(Variables.getFileVersion());
					newVersion++;
					//Finally we replace the version in the file name
					newFileName = newFileName.replaceAll("_v\\d+", "_v"+newVersion);
					}
				}
			else
				{
				Variables.getLogger().info("The file is not managed");
				newFileName = newFileName+"_v"+Variables.getFileVersion();
				}
			
			newFileName = newFileName+"."+fileExtension;
			Variables.getLogger().info("New file name : "+newFileName);
			
			//We copy the current file to the backup directory
			File newFilePath = new File(Variables.getBackupDirectoryName()+"\\"+fileName);
			if(newFilePath.exists())
				{
				Variables.getLogger().info("The file already exists, so we do no copy it");
				}
			else
				{
				FileUtils.copyFile(targetFile, newFilePath);
				Variables.getLogger().info("File copied to the backup directory : "+newFilePath.getPath());
				}
			
			
			//We rename the current file
			File renamedFile = new File(targetDirectory+"\\"+newFileName);
			if(renamedFile.exists())
				{
				Variables.getLogger().info("We cannot proceed because the file already exists");
				}
			else
				{
				targetFile.renameTo(renamedFile);
				Variables.getLogger().info("Current file renamed to : "+newFileName);
				}
			
			//We add a new entry into the database file
			DatabaseEntry myDE = new DatabaseEntry(Variables.getBackupDirectoryName(), separator);
			DatabaseManager myDM = new DatabaseManager("SFVDatabase.csv", UsefulMethod.getValue("maindirectory"));
			if(myDM.CheckEntry(myDE))
				{
				Variables.getLogger().info("The entry already exists so we do not add it again");
				}
			else
				{
				Variables.getLogger().info("The entry doesn't exist so we add it to the database");
				myDM.writeNewEntry(myDE);
				}
			
			Variables.getLogger().info("End of file processing");
			}
		catch(Exception e)
			{
			Variables.getLogger().error("An error occured while managing the file backup. We will now exit : "+e.getMessage(), e);
			System.exit(0);
			}
		}	
	

	/****************************************/
	public static void main(String[] args)
		{
		new Main(args);
		}
	
	/*2017*//*RATEL Alexandre 8)*/
	}

