package airportSecurityState.airportStates;


public class AirportSecurityLevel
{
    private String[] lowRiskOperations;
    private String[] moderateRiskOperations;
    private String[] highRiskOperations;
    

    public AirportSecurityLevel(){
	//---------------------------------------
	this.setLowAndHighRiskOperations(10);
	this.setModerateRiskOperations();
	//---------------------------------------
    }

    //--------------------------------------------
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
	//----------------------------------------
    }

    /**
     *helper function to set moderate risk operations
     **/
    private void setModerateRiskOperations(){
	moderateRiskOperations = {'2','3','5','8','9'};
    }
    
}
