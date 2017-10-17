package airportSecurityState.airportStates;

import airportSecurityState.airportSecurity.AirportSecurity;
import airportSecurityState.util.MyLogger;

public class ModerateRiskState implements AirportStateInterface
{
    private int[] operations;//the operations for moderate risk state
    private AirportSecurity airportSecurity;
    private MyLogger logger;

    /**
     *Constructor
     *@param the airport security 
     **/
    public ModerateRiskState(AirportSecurity airportSecurity,MyLogger loggerIn){
	this.airportSecurity = airportSecurity;
	this.operations = airportSecurity.getModerateRiskOperations();
	this.logger = loggerIn;
	logger.writeMessage("constructed ModerateRiskState class",logger.converToDebugVal(4));
    }

    //-------------- state Interface Implementations -------------
    /**
     *changes the state from moderate risk to low or high risk if needed
     *@param the parameters
     **/
    public void tightenOrLoosenSecurity(int[] parameters){
	int flag = 0;
	int travelers = parameters[0];
	int items = parameters[1];
	int days = parameters[2];
	double avgTraffic = getAvgTrafficPerDay(travelers,days);
	double avgProhibtedItems = getAvgPrbItemsPerDay(items,days);
	String loggerMessage = "average Traffic per Day: "+avgTraffic+" average prohibitted iterms per Day: "+avgProhibtedItems;
	logger.writeMessage(loggerMessage,logger.converToDebugVal(2));		
	if( avgTraffic < 4.0 || avgProhibtedItems < 1.0 ){
	    airportSecurity.setState(airportSecurity.getLowRiskState());
	    flag = 1;
	}
	if( avgTraffic >= 8.0 || avgProhibtedItems >= 2.0){
	    airportSecurity.setState(airportSecurity.getHighRiskState());
	    flag = 2;
	}
	if(flag==1){
	    logger.writeMessage("moderate risk state -> low risk state",logger.converToDebugVal(3));	    
	}else if(flag==2){
	    logger.writeMessage("moderate risk state -> high risk state",logger.converToDebugVal(3));	    
	}
    }

    /**
     *gets the list of operations as a one line string
     *@return the set of operations for moderate risk
     **/
    public String getResponse(){
	String temp = "";
	for(int i=0;i<operations.length-1;i++){
	    temp += operations[i]+" ";
	}
	temp += operations[operations.length-1] + "\n";
	return temp;
    }  

    /**
     *calculates average traffic per days
     *@return the average traffic per day
     **/
    public double getAvgTrafficPerDay(int noOfTravellers, int numberOfDays){
	return  ((double) noOfTravellers)/((double) numberOfDays );
    }

    /**
     *calculates the average prohibitted items per day
     *@return the average prohibited items per days
     **/
    public double getAvgPrbItemsPerDay(int prbItemsCount, int numberOfDays){
	return ((double) prbItemsCount)/((double) numberOfDays );
    }
}
