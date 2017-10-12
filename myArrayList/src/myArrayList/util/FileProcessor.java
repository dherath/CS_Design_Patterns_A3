package studentCoursesBackup.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileProcessor
{
    private String inputFileName=null;
    private File inputFile=null;
    private FileReader fileReader=null;
    private BufferedReader bufferedReader=null;
    
    /**
     * constructor
     *@param s, the absolute file path
     **/
    public FileProcessor(String s){
	this.inputFileName = s;
	try{
	    inputFile = new File(inputFileName);
	    fileReader = new FileReader(inputFile);
	    bufferedReader = new BufferedReader(fileReader);
	}catch(IOException e){
	    e.printStackTrace();
	    System.exit(1);
	}finally{ 
	}
    }

    /**
     *reads a line from an opened text file
     *@return  returns the line as a String
     **/
    public String readLine() {
	try{
	    return getBufferedReader().readLine();
	} catch (IOException e) {
	    return null;
	} finally {
		    
	}
    }

    /**
     *returns the current bufferedReader
     *@return the BufferedReader
     **/
    private BufferedReader getBufferedReader(){
	return bufferedReader;
    }

    /**
     *closes all opened instances
     **/
    public void closeAll(){
    	try{
	    this.bufferedReader.close();
	    this.fileReader.close();
    	}catch(IOException e){
	    e.printStackTrace();
    	}
    }
}
