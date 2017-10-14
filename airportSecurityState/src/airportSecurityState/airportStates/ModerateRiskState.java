package airportSecurityState.airportStates;

import airportSecurityState.airportSecurity.AirportSecurity;

public class ModerateRiskState implements AirportStateInterface
{
    private int[] operations;//the operations for moderate risk state
    private AirportSecurity airportSecurity;


    /**
     *Constructor
     *@param the airport security 
     **/
    public LowRiskState(AirportSecurity airportSecurity){
	this.airportSecurity = airportSecurity;
	this.operations = airportSecurity.getModerateRiskOperations();
    }

    //-------------- state Interface Implementations -------------
    /**
     *changes the state from moderate risk to low or high risk if needed
     *@param avgTraffic, the average traffic per day
     *@param avgProhibteditems, the average Prohibited items per day
     **/
    public void tightenOrLoosenSecurity(double avgTraffic, double avgProhibtedItems){
	if( avgTraffic < 4.0 || avgProhibtedItems < 1.0 ){
	    airportSecurity.setState(airportSecurity.getLowRiskState());
	}
	if( avgTraffic >= 8.0 || avgProhibtedItems >= 2.0){
	    airportSecurity.setState(airportSecurity.getHighRiskState());
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
        
}
