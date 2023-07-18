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

    private Journey bestJourney;



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
        totalTravelTime=Integer.MAX_VALUE;
        boolean chk=false;
        for (Journey j : journey
        ) {
            if (j.check()) {
                chk=true;
                if (j.getTotalTravelTime()<totalTravelTime) {
                    totalTravelTime=j.getTotalTravelTime();
                    bestJourney=j;
                }

            }
        }
        return chk;
    }


    public String getJourneyDescription() {
        return bestJourney.getJourneyDescription();
    }

    public void createResourcesToOptimize() {
        //journey=new ArrayList<UsedConnection>();
        //  journey=new Connection();
        int start = fromPlaceId;
        int time = 0;

        Journey j = new Journey(fromPlaceId, getDestination());
        for (int i = 0; i < 10000; i++) {
            Journey jr=j.getRandomJourney();

            if (jr!=null) {
                journey.add(jr);
            }
        }

    }

    @ValueRangeProvider(id="nextPoss")
    public List<Connection> possibleNext(){
        return t.getConnections();
    }

}