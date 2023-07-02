
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.optaplanner.core.api.domain.solution.*;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

@PlanningSolution
public class Itinerary {

    private long Id;
    private int totalTravelTime = -1;
    @PlanningEntityCollectionProperty
    private ArrayList <Connection> journey;
    Timetable t;
    private HardSoftScore score;

    protected int getStart() {
        return 1;
    }

    protected int getDestination() {
        return 3;
    }

    public Itinerary() {

        t=new Timetable();

        journey=new ArrayList<Connection>();

    }

    public boolean check() {
        int currentPlace = getStart();
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


    @ValueRangeProvider(id = "stationRange")
    public List<Connection> getNextPossibleCollection() {
        int start=getStart();
        int time=0;
        if (journey.size()>0) {


            Connection lastConn=journey.get(journey.size());
            start=lastConn.destinationPlaceId;
            time=journey.get(journey.size()).from+journey.get(journey.size()).duration;
        }

        return t.getFutureConnectionsFrom(start, time);
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
     //journey=new ArrayList<UsedConnection>();
      //  journey=new Connection();
        int start=getStart();
        int time=0;
        if (journey.size()>0) {


            Connection lastConn=journey.get(journey.size());
            start=lastConn.destinationPlaceId;
            time=journey.get(journey.size()).from+journey.get(journey.size()).duration;
        }

        List<Connection> possible=t.getFutureConnectionsFrom(start, time);
        Random rand = new Random();
        Connection randomElement = (Connection) possible.get(rand.nextInt(possible.size()));
        journey.add(randomElement);

    }
}