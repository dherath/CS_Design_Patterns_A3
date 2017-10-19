package airportSecurityState.util;

import airportSecurityState.util.Results;

public class MyLogger{

    /*DEBUG_VALUE=4 [Print to stdout everytime a constructor is called]
      DEBUG_VALUE=3 [Print to stdout everytime the state is changed]
      DEBUG_VALUE=2 [Prints the calculated values for every read  line]
      DEBUG_VALUE=1 [Prints the read parameters for each line]
      DEBUG_VALUE=0 [No output is printed to stdout, but output is sent to file ]
    */

    public static enum DebugLevel {RELEASE, INPUTS, PARAMETERS, STATE_CHANGE, CONSTRUCTOR
    };

    private static DebugLevel debugLevel;
    private static Results output;// reference to results

    /**
     *Constructor
     *@param the debug level
     **/
    public MyLogger(int levelIn){
	this.setDebugValue(levelIn);
	output = new Results(this);
    }

    /**
     *defines the debug level
     *@param the debug level
     **/
    public static void setDebugValue (int levelIn) {
	switch (levelIn){
	case 4: debugLevel = DebugLevel.CONSTRUCTOR; break;
	case 3: debugLevel = DebugLevel.STATE_CHANGE; break;
	case 2: debugLevel = DebugLevel.PARAMETERS; break;
	case 1: debugLevel = DebugLevel.INPUTS; break;
	case 0: debugLevel = DebugLevel.RELEASE; break;
	}
    }

    /**
     *sets the debug level
     *@param the debug level 
     **/
    public static void setDebugValue (DebugLevel levelIn) {
	debugLevel = levelIn;
    }

    // @return None
    /**
     *writes thespecified message
     *@param message, the string message
     *@param levelIn, the debug level
     **/
    public static void writeMessage (String message, DebugLevel levelIn ) {
	if (levelIn == debugLevel)
	    output.writeToStdout(message);
    }

    /**
     *converts in level to debug value
     *@param the numeric input level
     *@return the debug level
     **/
    public static DebugLevel converToDebugVal(int levelIn){
	DebugLevel temp = null;
	try{
	    if(levelIn >=5 || levelIn < 0){
		throw new RuntimeException("Invalid myLogger level");
	    }
	    switch (levelIn){
	    case 4: temp = DebugLevel.CONSTRUCTOR; break;
	    case 3: temp = DebugLevel.STATE_CHANGE; break;
	    case 2: temp = DebugLevel.PARAMETERS; break;
	    case 1: temp = DebugLevel.INPUTS; break;
	    case 0: temp = DebugLevel.RELEASE; break;
	    }
	}catch(RuntimeException e){
	    e.printStackTrace();
	    System.exit(3);
	}finally{}
	return temp;
    }

    /**
     * @return String
     */
    public String toString() {
	return "Debug Level is " + debugLevel;
    }
}
