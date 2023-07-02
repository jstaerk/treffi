import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
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
    private ArrayList<Connection> journey;


    Traveler(int startPosId) {
        journey=new ArrayList<Connection>();
        t=new Timetable();
        fromPlaceId=startPosId;

    }

    public boolean check() {
        int currentPlace = fromPlaceId;
        int currentTime = 0;
        int i=0;
        System.out.println("Deb checking possible journey");
        for (Connection legU : journey
        ) {
            //Connection legU=journey;
            i++;
            System.out.println("Deb chk "+i+".."+journey);


            if (legU.startPlaceId != currentPlace) {
                return false;
            }
            if (getDestination() == currentPlace) {
                return true;
            }
            if (legU.from < currentTime) {
                return false;
            }
            currentTime = legU.from + legU.duration;
            currentPlace = legU.destinationPlaceId;
        }

        totalTravelTime = currentTime;
        return currentPlace == getDestination();
    }

    public int getTotalTravelTime() {
        if (totalTravelTime == -1) {
            check();
        }
        return totalTravelTime;
    }

    public Traveler() {

    }

    public String getJourneyDescription() {
        String res="";
        for (Connection currentConnection: journey
             ) {

            res+=currentConnection.toString()+"\n";
        }
        return res;
    }


    @ValueRangeProvider(id = "stationRange")
    public List<Connection> getNextPossibleCollection() {
        int start=fromPlaceId;
        int time=0;
        if (journey.size()>0) {


            Connection lastConn=journey.get(journey.size());
            start=lastConn.destinationPlaceId;
            time=journey.get(journey.size()).from+journey.get(journey.size()).duration;
        }

        return t.getFutureConnectionsFrom(start, time);
    }

    public void createResourcesToOptimize() {
        //journey=new ArrayList<UsedConnection>();
        //  journey=new Connection();
        int start=fromPlaceId;
        int time=0;
        if (journey.size()>0) {


            Connection lastConn=journey.get(journey.size());
            start=lastConn.destinationPlaceId;
            time=journey.get(journey.size()).from+journey.get(journey.size()).duration;
        }

        List<Connection> possible=t.getFutureConnectionsFrom(time, start);
        Random rand = new Random();
        Connection randomElement = (Connection) possible.get(rand.nextInt(possible.size()));
        journey.add(randomElement);

    }

    @ValueRangeProvider(id="nextPoss")
    public List<Connection> possibleNext(){
        Timetable t=new Timetable();
        return t.getConnections();
    }
}