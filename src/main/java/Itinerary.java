
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.optaplanner.core.api.domain.solution.*;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

@PlanningSolution
public class Itinerary {

    private long Id;
    @PlanningEntityProperty
    Traveler TravelerA;

    private HardSoftScore score;

    protected int getStart() {
        return 1;
    }

    public Itinerary() {
        TravelerA = new Traveler(getStart());

    }

    public void createResourcesToOptimize() {
        TravelerA.createResourcesToOptimize();
    }


    public boolean check() {
        return TravelerA.check();
    }


    public int getTotalTravelTime() {
        return TravelerA.getTotalTravelTime();
    }

    public void print() {
        System.out.println("Score:" + getScore().getHardScore() + "H " + getScore().getSoftScore() + " \nRoute::");
        // for (Connection leg : journey
        //) {

        System.out.println("R:" + TravelerA.getJourneyDescription());
        //}
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

}