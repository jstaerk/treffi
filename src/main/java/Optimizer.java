

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.calculator.EasyScoreCalculator;

//determines how good a testplan is
public class Optimizer  implements EasyScoreCalculator<Itinerary, HardSoftScore> {


    public HardSoftScore calculateScore(Itinerary theJourney) {

        int hardScore = 0;

        int softScore = 0;

        if (!theJourney.check()) {
            hardScore=-1;
        }
        softScore=-theJourney.getTotalTravelTime();


        return HardSoftScore.of(hardScore, softScore);

    }
}