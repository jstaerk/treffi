import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.util.List;

@PlanningEntity
public class Traveler {

    int fromPlaceId;
    int time;
    @PlanningVariable(valueRangeProviderRefs = "nextPoss")
    Connection c;

    @ValueRangeProvider(id="nextPoss")
    public List<Connection> possibleNext(){
        Timetable t=new Timetable();
        return t.getConnections();
    }
}