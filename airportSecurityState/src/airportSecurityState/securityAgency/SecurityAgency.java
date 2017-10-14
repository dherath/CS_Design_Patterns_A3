package airportSecurityState.securityAgency;


public class AirportSecurityAgency
{
    private String[] lowRiskOperations;
    private String[] moderateRiskOperations;
    private String[] highRiskOperations;
    

    public AirportSecurityAgency(){
	//---------------------------------------
	this.setLowAndHighRiskOperations(10);
	this.setModerateRiskOperations();
	//---------------------------------------
    }

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
    
    
    
    //-------------------------------------------
    // Helper functions
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
