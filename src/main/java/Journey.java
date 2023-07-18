import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Journey {

    int startPlaceId;
    int destinationPlaceId;
    private ArrayList<Connection> journey;
    Timetable t;
    int totalTravelTime=-1;


    public Journey(int startPlaceId, int destinationPlaceId) {
        this.startPlaceId = startPlaceId;
        this.destinationPlaceId = destinationPlaceId;
        journey=new ArrayList<Connection>();
        t=new Timetable();
    }


    public String getJourneyDescription() {
        String res="";
        for (Connection currentConnection: journey
        ) {

            res+=currentConnection.toString()+"\n";
        }
        return res;
    }

    public  Journey getRandomJourney() {
        Journey res=new Journey(startPlaceId, destinationPlaceId);
        int pos=startPlaceId;
        int from=0;
        int maxSize=10000;
        int i=0;
        int destination=3;
        boolean deadlock=false;
        while ((pos!=destination)&&(i<maxSize)&&(!deadlock)) {
            i++;
                    List<Connection> possibleConn=
            t.getFutureConnectionsFrom(from, pos);
                    if (possibleConn.size()==0) {
                        deadlock=true;

                    } else {
                        Random rand = new Random();
                        Connection randomElement = possibleConn.get(rand.nextInt(possibleConn.size()));

                        res.journey.add(randomElement);
                        pos = randomElement.destinationPlaceId;
                        from = from + randomElement.duration;
                    }
        }
        return res;
    }

    @ValueRangeProvider(id = "stationRange")
    public List<Connection> getNextPossibleCollection() {
        int start=startPlaceId;
        int time=0;
        if (journey.size()>0) {


            Connection lastConn=journey.get(journey.size());
            start=lastConn.destinationPlaceId;
            time=journey.get(journey.size()).from+journey.get(journey.size()).duration;
        }

        return t.getFutureConnectionsFrom(start, time);
    }

    public boolean check() {
        int currentPlace = startPlaceId;
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

    protected int getDestination() {
        return 3;
    }


    @ValueRangeProvider(id="nextPoss")
    public List<Connection> possibleNext(){
        return t.getConnections();
    }

}