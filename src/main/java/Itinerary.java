
import java.util.ArrayList;
import java.util.List;

import org.optaplanner.core.api.domain.solution.*;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

@PlanningSolution
public class Itinerary {

    private long Id;
    private int totalTravelTime = -1;
    private List<UsedConnection> journey;
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
        System.out.println("Deb checking possible journey");
        for (UsedConnection legU : journey
        ) {
            i++;
            System.out.println("Deb chk "+i+".."+journey);

            Connection leg=legU.getConnection();
            if (leg.startPlaceId != currentPlace) {
                return false;
            }
            if (getDestination() == currentPlace) {
                return true;
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
       // for (Connection leg : journey
        //) {

            System.out.println("R:"+journey);
        //}
    }

    public int getTotalTravelTime() {
        if (totalTravelTime == -1) {
            check();
        }
        return totalTravelTime;
    }


    @PlanningEntityCollectionProperty
    @ValueRangeProvider(id = "stationRange")
    public List<UsedConnection> getJourney() {
        return journey;
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
     journey=new ArrayList<UsedConnection>();
     Timetable t=new Timetable();
        for (Connection c :t.getConnections()
             ) {
            UsedConnection u=new UsedConnection(c);
            u.setItinerary(this);
            journey.add(u);

        }
    }
}