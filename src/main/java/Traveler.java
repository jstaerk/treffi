import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningEntityProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@PlanningEntity
public class Traveler {

    int fromPlaceId;
    int time;
    @PlanningVariable(valueRangeProviderRefs = "nextPoss")
    Connection c;
    Timetable t;


    private int totalTravelTime = -1;

    protected int getDestination() {
        return 3;
    }


    @PlanningEntityCollectionProperty
    private ArrayList<Journey> journey;


    Traveler(int startPosId) {
        journey = new ArrayList<Journey>();
        t = new Timetable();
        fromPlaceId = startPosId;

    }

    public int getTotalTravelTime() {
        if (totalTravelTime == -1) {
            check();
        }
        return totalTravelTime;
    }

    public Traveler() {

    }


    public boolean check() {
        for (Journey j : journey
        ) {
            if (j.check()) {
                return true;
            }
        }
        return false;
    }


    public String getJourneyDescription() {
        String res = "";
        for (Journey j : journey
        ) {
            res = res + j.getJourneyDescription() + "\nor\n";
        }
        return res;

    }

    public void createResourcesToOptimize() {
        //journey=new ArrayList<UsedConnection>();
        //  journey=new Connection();
        int start = fromPlaceId;
        int time = 0;

        for (int i = 0; i < 10000; i++) {
            Journey j = new Journey(fromPlaceId, getDestination());
            journey.add(j.getRandomJourney());
        }

    }

    @ValueRangeProvider(id="nextPoss")
    public List<Connection> possibleNext(){
        return t.getConnections();
    }

}