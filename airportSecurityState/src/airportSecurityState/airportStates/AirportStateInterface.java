package airportSecurityState.airportStates;

public interface AirportStateInterface{
    void tightenOrLoosenSecurity(String lineIn);//per day
    String getResponse();//returns the set of operations as response per threat level
}
