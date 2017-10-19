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
    //-------- operations ----------------------
    //    private int[] lowRiskOperations;
    // private int[] moderateRiskOperations;
    //private int[] highRiskOperations;
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
	//---------------------------------------
	//setLowAndHighRiskOperations(10);
	//setModerateRiskOperations();

	result = "";
	logger = loggerIn;
	logger.writeMessage("constructed AirportSecurity class",logger.converToDebugVal(4));
	stateHelper = new StateHelper(logger);
	//---------------------------------------
	lowRiskState = new LowRiskState(this,logger);
	//System.out.println("low risk response - "+lowRiskState.getResponse());
	moderateRiskState = new ModerateRiskState(this,logger);
	//System.out.println("moderate risk response - "+moderateRiskState.getResponse());
	highRiskState = new HighRiskState(this,logger);
	//System.out.println("high risk response - "+highRiskState.getResponse());
	currentState = lowRiskState; //intiitally it is assumed that the airport is at low risk
	//---------------------------------------
	inputFile = new FileProcessor(inputFileName);
	//int flag = 0;
	try{
	    line = inputFile.readLine();
	    while(line != null){
		//flag++;
		//System.out.println(line);
		//	String[] data = preProcessLine(line);//throws runtime exceptions based on format
		//	updateParameters(data[0],data[1],data[2]);//updates parameters, throws exceptions
		//	double avgTraffic = getAvgTrafficPerDay();
		//double avgPrbItems = getAvgPrbItemsPerDay();
		//System.out.println(" avg traffic : "+avgTraffic+" avg prb items "+avgPrbItems+" noOfdays: "+numberOfDays+" total traffic: "+noOfTravellers+" total prb items "+ prbItemsCount);
		//String loggerMessage = "average Traffic per Day: "+avgTraffic+" average prohibitted iterms per Day: "+avgPrbItems;
		//logger.writeMessage(loggerMessage,logger.converToDebugVal(2));
		tightenOrLoosenSecurity(getLine());
		result += getResponse();
		line = inputFile.readLine();
	    }	    
	}catch(RuntimeException e){
	    e.printStackTrace();
	    System.exit(1);
	}finally{
	    //System.out.println(flag);
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
     *gets the updated parameters
     *@return the updated parameters for no.of travellers, prohibited items & days
     */
    private int[] getAllParameters(){
	int[] temp = new int[3];
	temp[0] = noOfTravellers;
	temp[1] = prbItemsCount;
	temp[2] = numberOfDays;
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
     *sets current state to low risk
     *@param the new state
     **/
    public void setState(AirportStateInterface newState){
	this.currentState = newState;
    }
    
}
