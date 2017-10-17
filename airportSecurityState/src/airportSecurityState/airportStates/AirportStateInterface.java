package airportSecurityState.airportStates;

public interface AirportStateInterface{
    void tightenOrLoosenSecurity(int[] parameters);//per day
    String getResponse();//returns the set of operations as response per threat level
    double getAvgTrafficPerDay(int noOfTravellers, int numberOfDays);
    double getAvgPrbItemsPerDay(int prbItemsCount, int numberOfDays);
}
