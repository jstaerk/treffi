import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.util.ArrayList;
import java.util.List;

@PlanningEntity

public class UsedConnection {
    Connection whichConnection;
    List<Itinerary> possibleItineraries;

    /***
     * this @planningvariable along with its valueRangeProvider would not be needed by non-optimizable code
     */
    @PlanningVariable(valueRangeProviderRefs = "timetableRange", nullable = true)
    Itinerary usedIn;

    UsedConnection(Connection c) {
        possibleItineraries=new ArrayList<>();
        whichConnection=c;

    }


    @Override
    public String toString() {
        return whichConnection.toString();
    }

    UsedConnection() {

        possibleItineraries=new ArrayList<>();
    }

    @ValueRangeProvider(id = "timetableRange")
    public List<Itinerary> getPossibleItineraries() {
        return possibleItineraries;
    }

    public Connection getConnection() {
        return whichConnection;
    }

    public void setItinerary(Itinerary itinerary) {
        usedIn=itinerary;
    }
}
