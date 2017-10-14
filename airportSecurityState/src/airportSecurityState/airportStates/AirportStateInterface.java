package airportSecurityState.airportStates;

public interface AirportStateInterface{
    void tightenOrLoosenSecurity(double avgTraffic, double avgProhibtedItems);//per day
    String getOperations();//returns the set of operations per threat level
}
