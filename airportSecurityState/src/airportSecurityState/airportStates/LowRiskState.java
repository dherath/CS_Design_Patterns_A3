package airportSecurityState.airportStates;

import airportSecurityState.airportSecurity.AirportSecurity;
import airportSecurityState.util.MyLogger;

public class LowRiskState implements AirportStateInterface
{
    private int[] operations;//the operations for Low risk state
    private AirportSecurity airportSecurity;
    private MyLogger logger;

    /**
     *Constructor
     *@param the airport security 
     **/
    public LowRiskState(AirportSecurity airportSecurity,MyLogger loggerIn){
	this.airportSecurity = airportSecurity;
	this.operations = airportSecurity.getLowRiskOperations();
	this.logger = loggerIn;
	logger.writeMessage("constructed LowRiskState class",logger.converToDebugVal(4));
	//	System.out.println("created low risk state");
    }

    //-------------- state Interface Implementations -------------
    /**
     *changes the state from low risk to moderate or high if needed
     *@param avgTraffic, the average traffic per day
     *@param avgProhibteditems, the average Prohibited items per day
     **/
    public void tightenOrLoosenSecurity(double avgTraffic, double avgProhibtedItems){
	int flag = 0;
	if(avgTraffic >= 4.0 || avgProhibtedItems >= 1.0 ){
	    if(avgTraffic < 8.0 || avgProhibtedItems < 2.0){
		airportSecurity.setState(airportSecurity.getModerateRiskState());
		flag = 1;
	    }
	    if(avgTraffic >= 8.0 || avgProhibtedItems >= 2.0 ){
		airportSecurity.setState(airportSecurity.getHighRiskState());
		flag = 2;
	    }
	}
	if(flag==1){
	    logger.writeMessage("low risk state -> moderate risk state",logger.converToDebugVal(3));	    
	}else if(flag==2){
	    logger.writeMessage("low risk state -> high risk state",logger.converToDebugVal(3));
	}
    }

    /**
     *gets the list of operations as a one line string
     *@return the set of operations for low risk
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
