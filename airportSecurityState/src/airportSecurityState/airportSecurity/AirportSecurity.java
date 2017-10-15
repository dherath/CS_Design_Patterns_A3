package airportSecurityState.airportSecurity;

import airportSecurityState.airportStates.AirportStateInterface;
import airportSecurityState.airportStates.LowRiskState;
import airportSecurityState.airportStates.ModerateRiskState;
import airportSecurityState.airportStates.HighRiskState;
import airportSecurityState.util.FileProcessor;

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
    //------------------------------------------
    private int numberOfDays;//total number of days 
    private int previousDay; // the index of the previous day
    private int prevTimeStamp; // the previous time stamp of a traveller for the same day
    private int prbItemsCount;//total number of prohibited items
    private int noOfTravellers; //total number of travellers
    private String[] prbItems;// list of prohibited items
    //------------------------------------------
    private FileProcessor inputFile;// input file
    private String line;//a single line read from input file

    
    /**
     *Constructor
     **/
    public AirportSecurity(String inputFileName){
	//---------------------------------------
	this.setLowAndHighRiskOperations(10);
	this.setModerateRiskOperations();
	numberOfDays = 0;
	previousDay = 0;
	prevTimeStamp = 0;
	prbItemsCount = 0;
	noOfTravellers = 0;
	prbItems = new String[]{"Gun","NailCutter","Blade","Knife"};
	//---------------------------------------
	lowRiskState = new LowRiskState(this);
	moderateRiskState = new ModerateRiskState(this);
	highRiskState = new HighRiskState(this);
	currentState = lowRiskState; //intiitally it is assumed that the airport is at low risk
	//---------------------------------------
	inputFile = new FileProcessor(inputFileName);
	try{
	    line = inputFile.readLine();
	    String[] data = preProcessLine(line);
	    
	}catch(RuntimeException e){
	    e.printStackTrace();
	    System.exit(1);
	}finally{
	    inputFile.closeAll();
	}	
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
	String temp = currentState.getResponse();
	return temp;
    }

    /**
     *calculates average traffic per days
     *@return the average traffic per day
     **/
    private double getAvgTrafficPerDay(){
	return (double) ( noOfTravellers/numberOfDays );
    }

    /**
     *calculates the average prohibitted items per day
     *@return the average prohibited items per days
     **/
    private double getAvgPrbItemsPerDay(){
	return (double) ( prbItemsCount/numberOfDays );
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

    /**
     *preprocessline into subsections
     *@param input, the read line from file
     *@return the String array with information
     **/
    private String[] preProcessLine(String input){
	String[] temp = new String[4];
	int[] index = new int[3];//index of semicolon in line
	String[] labels = new String[4];
	String[] list = {"Day","TOD","Airline","Item"};
	int count = 0;
	if(input.trim().length()==0){
	    throw new RuntimeException("Invalid format in text file: empty line");
	}
	//-------to find indices---------------
	for(int i=0;i<input.length();i++){
	    if(input.charAt(i)==';'){
		index[count]=i;
		count++;
	    }
	    if(count>3){
		throw new RuntimeException("Invalid format in text file");
	    }
	}
	//------ check label format------------
	labels[0] = input.substring(0,3);//Day
	labels[1] = input.substring(index[0]+1,index[0]+4);//TOD
	labels[2] = input.substring(index[1]+1,index[1]+8);//Airline
	labels[4] = input.substring(index[2]+1,index[2]+5);//Item
	count = 0;
	for(int i=0;i<4;i++){
	    if(labels[i].equals(list[0])){
		count++;
	    }
	}
	if(count != 4){
	    throw new RuntimeException("Invalid format in text file: incorrect label format");
	}
	temp[0] = input.substring(0,index[0]);//day
	temp[1] = input.substring((index[0]+1),index[1]);//TOD
	temp[2] = input.substring((index[2]+1),input.length());//item
	return temp;
    }
    
}
