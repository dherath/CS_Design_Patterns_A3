package airportSecurityState.airportSecurity;

import airportSecurityState.airportStates.AirportStateInterface;
import airportSecurityState.airportStates.LowRiskState;
import airportSecurityState.airportStates.ModerateRiskState;
import airportSecurityState.airportStates.HighRiskState;

public class AirportSecurity
{
    //-------- operations ----------------------
    private String[] lowRiskOperations;
    private String[] moderateRiskOperations;
    private String[] highRiskOperations;
    //------- states ---------------------------
    private AirportStateInterface currentState;
    private AirportStateInterface lowRiskState;
    private AirportStateInterface moderateRiskState;
    private AirportStateInterface highRiskState;
    
    /**
     *Constructor
     **/
    public AirportSecurity(){
	//---------------------------------------
	this.setLowAndHighRiskOperations(10);
	this.setModerateRiskOperations();
	//---------------------------------------
	lowRiskState = new LowRiskState(this);
	moderateRiskState = new ModerateRiskState(this);
	highRiskState = new HighRiskState(this);

	currentState = lowRiskState; //intiitally it is assumed that the airport is at low risk
    }

    /**
     *changes state of aiport security
     *@param avgTraffic, the average traffic per day
     *@param avgProhibteditems, the average prohibitted items per day
     **/
    public void tightenOrLoosenSecurity(double avgTraffic, double avgProhibtedItems){
	currentState.tightenOrLoosenSecurity(avgTraffic,avgProhibtedItems);
    }

    //-------------------------------------------
    // getter methods for Operations
    //-------------------------------------------
    /**
     *getter for low risk operations
     *@return the string operations for low risk
     **/
    public String[] getLowRiskOperations(){
	return lowRiskOperations;
    }
    

    /**
     *getter for moderate risk operations
     *@return the string operations for moderate risk
     **/
    public String[] getModerateRiskOperations(){
	return moderateRiskOperations;
    }

    
    /**
     *getter for high risk operations
     *@return the string operations for high risk
     **/
    public String[] getHighRiskOperations(){
	return highRiskOperations;
    }

    //------------------------------------------
    // getter methods for states
    //------------------------------------------
    


    //-----------------------------------------
    // setter methods for states
    //-----------------------------------------
    
    
    //-------------------------------------------
    // private  Helper functions
    //--------------------------------------------

    
    //the agency determines what actions to take depending on the state
    //therefore inorder to ensure that each state is closed for modification
    //the operation Ids themselves where set here
    
    /**
     *helper function that sets operations for Low Risk & HIgh Risk
     *@param the size of all the operations
     **/
    private void setLowAndHighRiskOperations(int size){
	lowRiskOperations = new String[5];
	highRiskOperations = new String[5];	
	int count1=0;
	int count2=0;
	//----------------------------------------
	for(int i=0;i<size;i++){
	    if(i%2==0){
		this.lowRiskOperations[count1]=i;
		count1++;
	    }else{
		this.highRiskOperations[count2]=i;
		count2++;
	    }
	}
    }

    /**
     *helper function to set moderate risk operations
     **/
    private void setModerateRiskOperations(){
	moderateRiskOperations = {'2','3','5','8','9'};
    }
    
}
