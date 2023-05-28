import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.score.director.ScoreDirectorFactoryConfig;
import org.optaplanner.core.config.solver.SolverConfig;
import org.optaplanner.core.config.solver.termination.TerminationConfig;

public class Main {
    public static void main(String[] args) {
        System.out.println("The best route is:");

        ScoreDirectorFactoryConfig sdc = new ScoreDirectorFactoryConfig();

        sdc.setEasyScoreCalculatorClass(Optimizer.class);

        TerminationConfig tc = new TerminationConfig();
        tc.setUnimprovedMillisecondsSpentLimit(1000l);

        SolverFactory<Itinerary> solverFactory = SolverFactory.create(new SolverConfig()
                .withSolutionClass(Itinerary.class)
                .withEntityClasses(UsedConnection.class)
                .withScoreDirectorFactory(sdc)
                .withTerminationConfig(tc));
        Solver<Itinerary> solver = solverFactory.buildSolver();


        Itinerary possibleJourneys = new Itinerary();
        possibleJourneys.createResourcesToOptimize();
        // Solve the problem

        solver.solve(possibleJourneys);
        Itinerary bestJourney = solver.getBestSolution();
        bestJourney.print();

    }
}
