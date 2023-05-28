import java.util.ArrayList;
import java.util.List;

public class Timetable {
    private List<Connection> allConnections = new ArrayList<Connection>() {
        {
            add(new Connection(1, 2, 0, 10));
            add(new Connection(2, 3, 15, 20));
            add(new Connection(2, 3, 9, 10));
            add(new Connection(6, 7, 3, 5));
        }
    };

    public List<Connection> getConnections() {
        return allConnections;
    }

    public List<Connection> getFutureConnectionsFrom(int from, int startPlaceId) {
        List<Connection> eligible = new ArrayList<Connection>();
        for (Connection theConnection : allConnections) {
            if ((theConnection.from >= from) && (theConnection.startPlaceId == startPlaceId)) {
                eligible.add(theConnection);
            }
        }
        return eligible;

    }

}
