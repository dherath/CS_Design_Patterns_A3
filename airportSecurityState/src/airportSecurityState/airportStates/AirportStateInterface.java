package airportSecurityState.airportStates;

public interface AirportStateInterface{
    void tightenOrLoosenSecurity(double avgTraffic, double avgProhibtedItems);//per day
    String getResponse();//returns the set of operations as response per threat level
}
