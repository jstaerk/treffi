import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.util.List;


public class Connection {

    int startPlaceId;
    int destinationPlaceId;
    int from;
    int duration;

    @Override
    public String toString() {
        return "@" + this.from + " take from " + this.startPlaceId + " to " + this.destinationPlaceId;
    }

    public Connection(int startPlaceId, int destinationPlaceId, int from, int duration) {
        this.startPlaceId = startPlaceId;
        this.destinationPlaceId = destinationPlaceId;
        this.from = from;
        this.duration = duration;
    }
}