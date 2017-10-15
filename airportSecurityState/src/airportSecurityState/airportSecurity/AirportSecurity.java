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
    private String result;// final result for output

    
    /**
     *Constructor
     **/
    public AirportSecurity(String inputFileName){
	//---------------------------------------
	setLowAndHighRiskOperations(10);
	setModerateRiskOperations();
	numberOfDays = 0;
	previousDay = 0;
	prevTimeStamp = 0;
	prbItemsCount = 0;
	noOfTravellers = 0;
	result = "";
	prbItems = new String[]{"Gun","NailCutter","Blade","Knife"};
	//---------------------------------------
	lowRiskState = new LowRiskState(this);
	//System.out.println("low risk response - "+lowRiskState.getResponse());
	moderateRiskState = new ModerateRiskState(this);
	//System.out.println("moderate risk response - "+moderateRiskState.getResponse());
	highRiskState = new HighRiskState(this);
	//System.out.println("high risk response - "+highRiskState.getResponse());
	currentState = lowRiskState; //intiitally it is assumed that the airport is at low risk
	//---------------------------------------
	inputFile = new FileProcessor(inputFileName);
	//int flag = 0;
	try{
	    line = inputFile.readLine();
	    while(line != null){
		//flag++;
		//System.out.println(line);
		String[] data = preProcessLine(line);//throws runtime exceptions based on format
		updateParameters(data[0],data[1],data[2]);//updates parameters, throws exceptions
		double avgTraffic = getAvgTrafficPerDay();
		double avgPrbItems = getAvgPrbItemsPerDay();
				System.out.println(" avg traffic : "+avgTraffic+" avg prb items "+avgPrbItems+" noOfdays: "+numberOfDays+" total traffic: "+noOfTravellers+" total prb items "+ prbItemsCount);
		tightenOrLoosenSecurity(avgTraffic,avgPrbItems);
		result += getResponse();
		line = inputFile.readLine();
	    }	    
	}catch(RuntimeException e){
	    e.printStackTrace();
	    System.exit(1);
	}finally{
	    //System.out.println(flag);
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
	return  ((double) noOfTravellers)/((double) numberOfDays );
    }

    /**
     *calculates the average prohibitted items per day
     *@return the average prohibited items per days
     **/
    private double getAvgPrbItemsPerDay(){
	return ((double) prbItemsCount)/((double) numberOfDays );
    }

    /**
     *returns the results stored
     *@return the results
     **/
    public String getResults(){
	return result;
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
		highRiskOperations[count1]=i+2;
		count1++;
	    }else{
		lowRiskOperations[count2]=i;
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
	//	System.out.println(labels[0]);
	labels[1] = input.substring(index[0]+1,index[0]+4);//TOD
	//	System.out.println(labels[1]);
	labels[2] = input.substring(index[1]+1,index[1]+8);//Airline
	//	System.out.println(labels[2]);
	labels[3] = input.substring(index[2]+1,index[2]+5);//Item
	//	System.out.println(labels[3]);
	count = 0;
	for(int i=0;i<4;i++){
	    if(labels[i].equals(list[i])){
		count++;
	    }
	}
	//	System.out.println(count);
	if(count != 4){
	    throw new RuntimeException("Invalid format in text file: incorrect label format");
	}
	temp[0] = input.substring(0,index[0]);//day
	temp[1] = input.substring((index[0]+1),index[1]);//TOD
	temp[2] = input.substring((index[2]+1),input.length());//item
	return temp;
    }

    /**
     *updates parameters of no of travellers, days and prohibitted items
     *@param s1, preprocessed string with Day info
     *@param s2, preprocessed string with TOD info
     *@param s3, preprocessed string with item info
     **/
    private void updateParameters(String s1, String s2, String s3){
	int currentDay = previousDay;
	//-------------update number of days -------------------------------
	String value = s1.substring(4,s1.length());
	if(isNumber(value)){
	    currentDay = convertToInt(value);
	    if(previousDay < currentDay){
		//previousDay = currentDay;
		numberOfDays++;
	    }else if(previousDay > currentDay){
		throw new RuntimeException("invalid format in input text : The days need to be in order");
	    }
	}else{
	    throw new RuntimeException("invalid input for text file: Day can only have numeric values");
	}
	//-----------update no of travellers ------------------------------
	String hour = s2.substring(4,6);
	String min = s2.substring(7,9);
	if(isNumber(hour) && isNumber(min)){
	    int h = convertToInt(hour);
	    int m = convertToInt(min);
	    if(h>24){
		throw new RuntimeException("Invalid input format: Hours cannot exceed 24");
	    }
	    if(m>60){
		throw new RuntimeException("Invalid input format: Minutes cannot exceed 60");
	    }
	    int time = h*60 + m ;
	    // System.out.println("previous time : "+prevTimeStamp+" current time: "+time);
	    if(currentDay == previousDay){
		if(prevTimeStamp <= time){
		    noOfTravellers++;
		    prevTimeStamp = time;
		}else{
		    throw new RuntimeException("Invalid input format: time entries cannot decrease in value for the same day");
		}
	    }else{
		noOfTravellers++;
		prevTimeStamp = time;
	    }
	    previousDay = currentDay ; //update previous day flag
	}else{
	    throw new RuntimeException("Invalid format in text file: hour/minutes must be numbers");
	}
	//-----------update no of prohibitted items -----------------------
	String item = s3.substring(5,s3.length());
	for(int i = 0; i < prbItems.length ; i++){
	    if(prbItems[i].toLowerCase().equals(item.toLowerCase())){
		prbItemsCount++;
	    }
	}
	
    }
    
    /**
     *Converts the string input to an Int
     *@param sIn the String input which is a number
     *@return the number in Int format
     **/
    private int convertToInt(String sIn){
	int number=0;
	for(int i=0;i<sIn.length();i++){
	    number = number*10+sIn.charAt(i)-'0';
	}
	return number;
    }

    /**
     *finds if chars in a string are numeric values
     *@param sIn, the string
     *@return true if a number, else false
     **/
    private boolean isNumber(String sIn){
	String temp = "0123456789";
	int count = 0;
	for(int i=0;i<sIn.length();i++){
	    for(int j=0;j<temp.length();j++){
		if(sIn.charAt(i)==temp.charAt(j)){
		    count++;
		}
	    }
	}
	if(count != sIn.length()){
	    return false;
	}
	return true;
    }
    
}
