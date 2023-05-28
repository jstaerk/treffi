
import java.util.ArrayList;
import java.util.List;

import org.optaplanner.core.api.domain.solution.*;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

@PlanningSolution
public class Itinerary {

    private long Id;
    private List<Connection> journey;
    private int totalTravelTime = -1;

    private HardSoftScore score;

    protected int getStart() {
        return 1;
    }

    protected int getDestination() {
        return 3;
    }

    public Itinerary() {


    }

    public boolean check() {
        int currentPlace = getStart();
        int currentTime = 0;
        int i=0;
        for (Connection leg : journey
        ) {
            i++;
            leg.setUse(this);
            System.out.println("Deb chk "+i+".."+leg);

            if (leg.startPlaceId != currentPlace) {
                return false;
            }
            if (leg.from < currentTime) {
                return false;
            }
            currentTime = leg.from + leg.duration;
            currentPlace = leg.destinationPlaceId;
        }

        totalTravelTime = currentTime;
        return currentPlace == getDestination();
    }

    public void print() {
        System.out.println("Score:"+getScore().getHardScore()+"H "+getScore().getSoftScore()+" \nRoute::");
        for (Connection leg : journey
        ) {

            System.out.println("R:"+leg);
        }
    }

    public int getTotalTravelTime() {
        if (totalTravelTime == -1) {
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

    public void createResourcesToOptimize() {
        journey = new ArrayList<Connection>();

    }
}