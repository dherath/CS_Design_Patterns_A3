package airportSecurityState.airportStates;

import airportSecurityState.airportSecurity.AirportSecurity;

public class LowRiskState implements AirportStateInterface
{
    private String operations;//the operations for Low risk state
    private AirportSecurity airportSecurity;


    /**
     *Constructor
     *@param the airport security agency
     **/
    public LowRiskState(AirportSecurity airportSecurity){
	this.airportSecurity = airportSecurity;
	this.operations = airportSecurity.getLowRiskOperations();
    }

    //-------------- state Interface Implementations -------------
    /**
     *changes the state from low risk to moderate or high if needed
     *@param avgTraffic, the average traffic per day
     *@param avgProhibteditems, the average Prohibited items per day
     **/
    public void tightenOrLoosenSecurity(double avgTraffic, double avgProhibtedItems){
	if(avgTraffic >= 4.0 || avgProhibtedItems >= 1.0 ){
	    if(avgTraffic < 8.0 || avgProhibtedItems < 2.0){
		airportSecurity.setState(airportSecurity.getModerateRiskState());
	    }
	    if(avgTraffic >= 8 || avgProhibtedItems >= 2 ){
		airportSecurity.setState(airportSecurity.getHighRiskState());
	    }
	}
    }
    
    
    
}
