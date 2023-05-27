import java.util.ArrayList;
import java.util.List;

public class Timetable {
    public List<Connection> getConnections() {
        List<Connection> res=new ArrayList<Connection>();
        res.add ( new Connection(1,2,0,10));
        res.add (new Connection(2,3,15,20));
        res.add (new Connection(2,3,9,10));
        res.add (new Connection(6,7,3,5));
                return res;
    }

}
