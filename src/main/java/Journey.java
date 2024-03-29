import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Journey {

    int startPlaceId;
    int destinationPlaceId;
    private ArrayList<Connection> journey;
    Timetable t;
    int totalTravelTime = -1;

    private static Logger logger = LoggerFactory.getLogger(Journey.class);

    public Journey(int startPlaceId, int destinationPlaceId) {
        this.startPlaceId = startPlaceId;
        this.destinationPlaceId = destinationPlaceId;
        journey = new ArrayList<Connection>();
        t = new Timetable();
    }


    public String getJourneyDescription() {
        String res = "";
        for (Connection currentConnection : journey
        ) {

            res += currentConnection.toString() + "\n";
        }
        return res;
    }

    public Journey getRandomJourney() {
        Journey res = new Journey(startPlaceId, destinationPlaceId);
        int pos = startPlaceId;
        int from = 0;
        int maxSize = 10000;
        int i = 0;
        int destination = 3;
        while ((pos != destination) && (i < maxSize)) {
            i++;
            List<Connection> possibleConn =
                    t.getFutureConnectionsFrom(from, pos);
            if (possibleConn.size() == 0) {
                return null;//deadlocked, no trains from here, cant return this
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
        int start = startPlaceId;
        int time = 0;
        if (journey.size() > 0) {


            Connection lastConn = journey.get(journey.size());
            start = lastConn.destinationPlaceId;
            time = journey.get(journey.size()).from + journey.get(journey.size()).duration;
        }

        return t.getFutureConnectionsFrom(start, time);
    }

    public boolean check() {
        int currentPlace = startPlaceId;
        int currentTime = 0;
        int i = 0;
        logger.debug("checking possible journey");
        for (Connection legU : journey
        ) {
            //Connection legU=journey;
            i++;

            logger.debug("  checking " + i + ".." + journey);


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
        return totalTravelTime;
    }

    protected int getDestination() {
        return 3;
    }


    @ValueRangeProvider(id = "nextPoss")
    public List<Connection> possibleNext() {
        return t.getConnections();
    }

}