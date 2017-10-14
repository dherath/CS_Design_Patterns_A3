package airportSecurityState.airportSecurity;

import airportSecurityState.airportStates.AirportStateInterface;
import airportSecurityState.airportStates.LowRiskState;
import airportSecurityState.airportStates.ModerateRiskState;
import airportSecurityState.airportStates.HighRiskState;

public class AirportSecurity
{
    //-------- operations ----------------------
    private int[] lowRiskOperations;
    private int[] moderateRiskOperations;
    private int[] highRiskOperations;
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
     *computes changes to aiport security state
     *@param avgTraffic, the average traffic per day
     *@param avgProhibteditems, the average prohibitted items per day
     **/
    public void tightenOrLoosenSecurity(double avgTraffic, double avgProhibtedItems){
	currentState.tightenOrLoosenSecurity(avgTraffic,avgProhibtedItems);
    }

    /**
     *returns the required operations for current state asa a string
     *@return the operation Ids
     **/
    public String getResponse(){
	currentState.getResponse();
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
    
 
    //-------------------------------------------
    // getter methods for Operations
    //-------------------------------------------
    /**
     *getter for low risk operations
     *@return the operations for low risk
     **/
    public int[] getLowRiskOperations(){
    return lowRiskOperations;
    }
    

    /**
     *getter for moderate risk operations
     *@return the operations for moderate risk
     **/
    public int[] getModerateRiskOperations(){
    return moderateRiskOperations;
    }

    
    /**
     *getter for high risk operations
     *@return the operations for high risk
     **/
    public int[] getHighRiskOperations(){
    return highRiskOperations;
    }
    
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
	lowRiskOperations = new int[5];
	highRiskOperations = new int[5];	
	int count1=0;
	int count2=0;
	//----------------------------------------
	for(int i=0;i<size;i++){
	    if(i%2==0){
		lowRiskOperations[count1]=i;
		count1++;
	    }else{
		highRiskOperations[count2]=i;
		count2++;
	    }
	}
    }

    /**
     *helper function to set moderate risk operations
     **/
    private void setModerateRiskOperations(){
	moderateRiskOperations = new int[]{2,3,5,8,9};
    }
    
}
