package airportSecurityState.airportStates;

import airportSecurityState.securityAgency.AirportSecurityAgency;

public class LowRiskState implements AirportStateInterface
{
    private String operations;//the operations for Low risk state
    private AirportSecurityAgency securityAgency;

    /**
     *Constructor
     *@param the airport security agency
     **/
    public LowRiskState(AirportSecurityAgency securityAgency){
	this.securityAgency = securityAgency;
	this.operations = securityAgency.getLowRiskOperations();
    }


    
    
    
}
