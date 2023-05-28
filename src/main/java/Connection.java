import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.util.List;

@PlanningEntity

public class Connection {

    int startPlaceId;
    int destinationPlaceId;
    int from;
    int duration;

    /***
     * this @planningvariable along with its valueRangeProvider would not be needed by non-optimizable code
     */
    @PlanningVariable(valueRangeProviderRefs = "timetableRange", nullable = true)
    Itinerary usedIn;


    @ValueRangeProvider(id = "timetableRange")
    public List<Connection> getJourney() {
        Timetable t = new Timetable();
        return t.getFutureConnectionsFrom(from, startPlaceId);
    }

    public String toString() {
        return "@" + this.from + " take from " + this.from + " to " + this.destinationPlaceId;
    }

    public Connection(int startPlaceId, int destinationPlaceId, int from, int duration) {
        this.startPlaceId = startPlaceId;
        this.destinationPlaceId = destinationPlaceId;
        this.from = from;
        this.duration = duration;
    }

    public void setUse(Itinerary itinerary) {
        this.usedIn=itinerary;
    }
}