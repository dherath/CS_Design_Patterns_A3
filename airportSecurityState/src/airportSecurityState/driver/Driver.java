package airportSecurityState.driver;

import airportSecurityState.airportSecurity.AirportSecurity;
import airportSecurityState.util.Results;
import airportSecurityState.util.Driver;

public class Driver 
{
    public static void main(String[] args){
	try{
	    for(int i=0;i<args.length;i++){
		if(args[i].equals("${arg"+i+"}") || args.length!=3){
		    throw new RuntimeException("number of arguments not sufficant.\nRetry with exactly 3 arguments using the following format with absolute paths for files:\n-Darg0=target for input file\n-Darg1=target for output file\n-Darg2= logger level\n");		    
		}
	    }
	    //System.out.println("1-> "+args[0]+" 2-> "+args[1]+" 3-> "+args[2]);
	    AirportSecurity airport = new AirportSecurity(args[0]);
	    Results output = new Results(args[1]);
	    int loggerLevel = Integer.parseInt(args[2]);
	    output.storeNewResult(airport.getResults());
	    output.writeToFile();
	}catch(RuntimeException e){
	    e.printStackTrace();
	    System.exit(2);
	}finally{}
    }
	
}
