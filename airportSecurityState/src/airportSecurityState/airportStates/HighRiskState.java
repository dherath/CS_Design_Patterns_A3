package airportSecurityState.airportStates;

import airportSecurityState.airportSecurity.AirportSecurity;
import airportSecurityState.airportStates.StateHelper;
import airportSecurityState.util.MyLogger;

public class HighRiskState implements AirportStateInterface
{
    private int[] operations;//the operations for high risk state
    private AirportSecurity airportSecurity;
    private MyLogger logger;//reference to logger
    private StateHelper stateHelper;//stat helper

    /**
     *Constructor
     *@param the airport security 
     **/
    public HighRiskState(AirportSecurity airportSecurity,MyLogger loggerIn,StateHelper stateHelperIn){
	this.airportSecurity = airportSecurity;
	this.operations = new int[]{2,4,6,8,10};
	this.logger = loggerIn;
	this.stateHelper = stateHelperIn;
	logger.writeMessage("constructed HighRiskstate class",logger.converToDebugVal(4));
    }

    //-------------- state Interface Implementations -------------
    /**
     *changes the state from high risk to moderate or low if needed
     *@param the parameters
     **/
    public void tightenOrLoosenSecurity(String lineIn){
	int flag = 0;
	String[] data = stateHelper.preProcessLine(lineIn);
	stateHelper.updateParameters(data[0],data[1],data[2]);
	double avgTraffic = stateHelper.getAvgTrafficPerDay();
	double avgProhibtedItems = stateHelper.getAvgPrbItemsPerDay();
	String loggerMessage = "average Traffic per Day: "+avgTraffic+" average prohibitted iterms per Day: "+avgProhibtedItems;
	logger.writeMessage(loggerMessage,logger.converToDebugVal(2));	
	if( avgTraffic < 8.0 || avgProhibtedItems < 2.0){
	    if( avgTraffic < 4.0 || avgProhibtedItems < 1.0){
		airportSecurity.setState(airportSecurity.getLowRiskState());
		flag = 1;
	    }
	    if( avgTraffic >= 4.0 || avgProhibtedItems >= 1.0 ){
		airportSecurity.setState(airportSecurity.getModerateRiskState());
		flag = 2;
	    }
	}
	if(flag==1){
	    logger.writeMessage("high risk state -> low risk state",logger.converToDebugVal(3));	    
	}else if(flag==2){
	    logger.writeMessage("high risk state -> moderate risk state",logger.converToDebugVal(3));
	}
    }

    /**
     *gets the list of operations as a one line string
     *@return the set of operations for high risk
     **/
    public String getResponse(){
	String temp = "";
	for(int i=0;i<operations.length-1;i++){
	    temp += operations[i]+" ";
	}
	temp += operations[operations.length-1] + "\n";
	return temp;
    }
        
}
