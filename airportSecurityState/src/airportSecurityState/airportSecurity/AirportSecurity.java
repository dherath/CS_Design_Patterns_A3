package airportSecurityState.airportSecurity;

import airportSecurityState.airportStates.AirportStateInterface;
import airportSecurityState.airportStates.LowRiskState;
import airportSecurityState.airportStates.ModerateRiskState;
import airportSecurityState.airportStates.HighRiskState;
import airportSecurityState.airportStates.StateHelper;
import airportSecurityState.util.FileProcessor;
import airportSecurityState.util.MyLogger;

public class AirportSecurity
{

    //------- states ---------------------------
    private AirportStateInterface currentState;
    private AirportStateInterface lowRiskState;
    private AirportStateInterface moderateRiskState;
    private AirportStateInterface highRiskState;
    //------------------------------------------
    private StateHelper stateHelper;
    //------------------------------------------
    private FileProcessor inputFile;// input file
    private String line;//a single line read from input file
    private String result;// final result for output
    private MyLogger logger;

    
    /**
     *Constructor
     **/
    public AirportSecurity(String inputFileName,MyLogger loggerIn){
	result = "";
	logger = loggerIn;
	logger.writeMessage("constructed AirportSecurity class",logger.converToDebugVal(4));
	stateHelper = new StateHelper(logger);
	//---------------------------------------
	lowRiskState = new LowRiskState(this,logger,stateHelper);
	moderateRiskState = new ModerateRiskState(this,logger,stateHelper);
	highRiskState = new HighRiskState(this,logger,stateHelper);
	currentState = lowRiskState; //intiitally it is assumed that the airport is at low risk
	//---------------------------------------
	inputFile = new FileProcessor(inputFileName,logger);
	try{
	    line = inputFile.readLine();
	    while(line != null){
		tightenOrLoosenSecurity(getLine());
		result += getResponse();
		line = inputFile.readLine();
	    }	    
	}catch(RuntimeException e){
	    e.printStackTrace();
	    System.exit(1);
	}finally{
	    inputFile.closeAll();
	}	
    }

    /**
     *computes changes to aiport security state
     *@param lineIn, the line read from file
     **/
    public void tightenOrLoosenSecurity(String lineIn){
	currentState.tightenOrLoosenSecurity(lineIn);
    }

    /**
     *returns the required operations for current state asa a string
     *@return the operation Ids
     **/
    public String getResponse(){
	String temp = currentState.getResponse();
	return temp;
    }

    
    /**
     *returns the results stored
     *@return the results
     **/
    public String getResults(){
	return result;
    }

    /**
     *returns the read line
     *@return the string line from file
     **/
    public String getLine(){
	return line;
    }

    //------------------------------------------
    // getter methods for states
    //------------------------------------------

    /**
     *returns the low risk state
     *@return the low risk state
     **/
    public AirportStateInterface getLowRiskState(){
	return lowRiskState;
    }

    /**
     *returns the moderate risk state
     *@return the moderate risk state
     **/
    public AirportStateInterface getModerateRiskState(){
	return moderateRiskState;
    }
    
    /**
     *returns the high risk state
     *@return the high risk state
     **/
    public AirportStateInterface getHighRiskState(){
	return highRiskState;
    }
    
    //-----------------------------------------
    // setter methods for states
    //-----------------------------------------

    /**
     *sets current state to new state
     *@param the new state
     **/
    public void setState(AirportStateInterface newState){
	this.currentState = newState;
    }
    
}
