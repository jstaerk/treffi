import org.optaplanner.core.api.domain.entity.PlanningEntity;

@PlanningEntity

public class Connection {

    int start;
    int dest;
    int from;
    int duration;

    public Connection(int start, int dest, int from, int duration) {
        this.start = start;
        this.dest = dest;
        this.from = from;
        this.duration = duration;
    }
}