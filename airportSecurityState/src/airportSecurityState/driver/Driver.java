package airportSecurityState.driver;

import airportSecurityState.airportSecurity.AirportSecurity;
import airportSecurityState.util.Results;
import airportSecurityState.util.Driver;

public class Driver 
{
    public static void main(String[] args){
	try{
	    if(!checkArgs()){
		throw new RuntimeException("number of arguments not sufficant.\nRetry with exactly 3 arguments using the following format with absolute paths for files:\n-Darg0=target for input file\n-Darg1=target for output file\n-Darg2= logger level between 0 & 4.\n(0-REALESE,1-INPUTS,2-PARAMETERS,3-STATE_CHANGE,4-CONSTRUCTOR)\n");		    
	    }
	    int loggerLevel = Integer.parseInt(args[2]);
	    AirportSecurity airport = new AirportSecurity(args[0],loggerLevel);
	    Results output = new Results(args[1],loggerLevel);
	    output.storeNewResult(airport.getResults());
	    output.writeToFile();
	}catch(RuntimeException e){
	    e.printStackTrace();
	    System.exit(2);
	}finally{}
    }

    /**
     *function to check if args format is correct
     *@param the string inputs
     *@return true if format is correct,false otherwise
     **/
    private boolean checkArgs(String[] args){
	if(args.length != 3){
	    return false;
	}
	for(int i=0;i<args.length;i++){
	    if(args[i].equals("${arg"+i+"}")){
		return false;
	    }
	}
	int temp = Integer.pareInt(args[2]);
	if(temp>=5 || temp<0){
	    return false;
	}
	return true;
    }
	
}
