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

    //-------------- Sttae INterface Implementations -------------

    public void tightenOrLoosenSecurity(double avgTraffic, double avgProhibtedItems){
	
    }
    
    
    
}
