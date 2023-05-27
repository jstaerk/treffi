import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.util.List;

@PlanningEntity

public class Connection {

    int start;
    int dest;
    int from;
    int duration;

    /***
     * this @planningvariable along with its valueRangeProvider would not be needed by non-optimizable code
     */
    @PlanningVariable(valueRangeProviderRefs = "timetableRange", nullable = true)
    Connection possibleContinuations;


    @ValueRangeProvider(id = "timetableRange")
    public List<Connection> getJourney() {
        Timetable t = new Timetable();
        return t.getFutureConnectionsFrom(from,start);
    }


    public Connection(int start, int dest, int from, int duration) {
        this.start = start;
        this.dest = dest;
        this.from = from;
        this.duration = duration;
    }
}