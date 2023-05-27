
import java.util.List;

import org.optaplanner.core.api.domain.solution.*;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

@PlanningSolution
public class Itinerary {

    private long Id;
    @PlanningVariable(valueRangeProviderRefs = "stationRange", nullable = true)
    private List<Connection> journey;
    private int totalTravelTime =-1;

    private HardSoftScore score;

    protected int getStart() {
        return 1;
    }

    protected int getDestination() {
        return 3;
    }

    public boolean check() {
        int currentPlace = getStart();
        int currentTime = 0;
        for (Connection leg : journey
        ) {
            if (leg.start != currentPlace) {
                return false;
            }
            if (leg.from < currentTime) {
                return false;
            }
            currentTime = leg.from + leg.duration;
            currentPlace = leg.dest;
        }
        totalTravelTime =currentTime;
        return currentPlace == getDestination();
    }

    public void print() {
        for (Connection leg : journey
        ) {

            System.out.println("@"+leg.from+" take from "+leg.from+" to "+leg.dest);
        }
    }

    public int getTotalTravelTime() {
        if (totalTravelTime==-1) {
            check();
        }
        return totalTravelTime;
    }

    @ValueRangeProvider(id = "stationRange")
    public List<Connection> getJourney() {
        return journey;
    }

    @PlanningEntityCollectionProperty
    public List<Connection> getTimeTable() {
        Timetable t = new Timetable();
        return t.getConnections();
    }

    @PlanningScore
    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

}