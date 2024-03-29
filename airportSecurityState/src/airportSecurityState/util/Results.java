package airportSecurityState.util;

import airportSecurityState.util.FileDisplayInterface;
import airportSecurityState.util.StdoutDisplayInterface;
import airportSecurityState.util.MyLogger;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Results implements StdoutDisplayInterface, FileDisplayInterface
{
    private String text;
    private String outputFileName = null;
    private FileWriter fileWriter = null;
    private BufferedWriter bufferedWriter = null;

    /**
     *the temp constructor for logger output
     **/
    public Results(MyLogger logger){
	text = null;
	this.outputFileName = null;
    }
    
    /**
     *constructor
     *@param the name of the output file
     **/
    public Results(String name,MyLogger logger){
	text = "";
	this.outputFileName = name;
	logger.writeMessage("constructed Results class",logger.converToDebugVal(4));
    }
    
    /**
     *Displays the text in terminal
     *@param the string to be displayed
     **/ 
    public void writeToStdout(String s) {
	System.out.println(s);
    }

    /**
     *prints output to output.txt
     **/
    public void writeToFile(){
	try{
	    fileWriter = new FileWriter(outputFileName);
	    bufferedWriter = new BufferedWriter(fileWriter);
	    bufferedWriter.write(this.getResults());
	}catch(IOException e){
	    e.printStackTrace();
	}finally{
	    try{
		bufferedWriter.close();
		fileWriter.close();
	    }catch(IOException f){
		f.printStackTrace();
	    }
	}
    }

    /**
     *method to get results
     *@return the results text
     **/
    public String getResults(){
	return this.text;
    }

    /**
     *method to set Results
     *@param the new resutls string
     **/
    public void storeNewResult(String s){
	this.text += s;
    }
}
