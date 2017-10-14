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
	if(avgTraffic >=4 || avgProhibtedItems >=1 ){
	    
	}
    }
    
    
    
}
