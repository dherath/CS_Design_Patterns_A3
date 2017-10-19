package airportSecurityState.airportStates;

import airportSecurityState.util.MyLogger;

public class StateHelper 
{
    private MyLogger logger;//reference to logger
    private int numberOfDays;//total number of days 
    private int previousDay; // the index of the previous day
    private int prevTimeStamp; // the previous time stamp of a traveller for the same day
    private int prbItemsCount;//total number of prohibited items
    private int noOfTravellers; //total number of travellers
    private String[] prbItems;// list of prohibited items

    /**
     *Constructor
     *@param the logger
     **/
    public StateHelper(MyLogger loggerIn){
	numberOfDays = 0;
	previousDay = 0;
	prevTimeStamp = 0;
	prbItemsCount = 0;
	noOfTravellers = 0;
	prbItems = new String[]{"Gun","NailCutter","Blade","Knife"};
	logger = loggerIn;
	logger.writeMessage("constructed StateHelper class",logger.converToDebugVal(4));	
    }

    /**
     *calculates average traffic per days
     *@return the average traffic per day
     **/
    public double getAvgTrafficPerDay(){
	return  ((double) noOfTravellers)/((double) numberOfDays );
    }

    /**
     *calculates the average prohibitted items per day
     *@return the average prohibited items per days
     **/
    public double getAvgPrbItemsPerDay(){
	return ((double) prbItemsCount)/((double) numberOfDays );
    }

        /**
     *gets the updated parameters
     *@return the updated parameters for no.of travellers, prohibited items & days
     */
    public int[] getAllParameters(){
	int[] temp = new int[3];
	temp[0] = noOfTravellers;
	temp[1] = prbItemsCount;
	temp[2] = numberOfDays;
	return temp;
    }
        /**
     *preprocessline into subsections
     *@param input, the read line from file
     *@return the String array with information
     **/
    public String[] preProcessLine(String input){
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
    public void updateParameters(String s1, String s2, String s3){
	int currentDay = previousDay;
	String loggerMessage = "";
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
	loggerMessage += "No.of days: "+numberOfDays+" ";
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
	loggerMessage += "No.of Travellers: "+noOfTravellers+" ";
	//-----------update no of prohibitted items -----------------------
	String item = s3.substring(5,s3.length());
	for(int i = 0; i < prbItems.length ; i++){
	    if(prbItems[i].toLowerCase().equals(item.toLowerCase())){
		prbItemsCount++;
	    }
	}
	loggerMessage += "No.of prohibitted Items: "+prbItemsCount;
	logger.writeMessage(loggerMessage,logger.converToDebugVal(1));
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
