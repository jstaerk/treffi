import org.acme.vehiclerouting.bootstrap.DemoDataBuilder;
import org.acme.vehiclerouting.domain.Location;
import org.acme.vehiclerouting.domain.Vehicle;
import org.acme.vehiclerouting.domain.VehicleRoutingSolution;
import org.acme.vehiclerouting.persistence.VehicleRoutingSolutionRepository;
import org.acme.vehiclerouting.solver.VehicleRoutingConstraintProvider;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import org.optaplanner.core.api.solver.*;
import org.optaplanner.core.api.solver.change.ProblemChange;
import org.optaplanner.core.config.score.director.ScoreDirectorFactoryConfig;
import org.optaplanner.core.config.solver.SolverConfig;
import org.optaplanner.core.config.solver.termination.TerminationConfig;
import org.optaplanner.core.impl.solver.DefaultSolverManager;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    private VehicleRoutingSolutionRepository repository;
    private SolverManager<VehicleRoutingSolution, Long> solverManager;
    private SolutionManager<VehicleRoutingSolution, HardSoftLongScore> solutionManager;

    private final AtomicReference<Throwable> solverError = new AtomicReference<>();

    private static final long PROBLEM_ID = 0L;

    public void go() {
        repository = new VehicleRoutingSolutionRepository();
        VehicleRoutingSolution problem = VehicleRoutingSolution.predef();
        repository.update(problem);

        ScoreDirectorFactoryConfig sdc = new ScoreDirectorFactoryConfig();

        sdc.setConstraintProviderClass(VehicleRoutingConstraintProvider.class);
        TerminationConfig tc = new TerminationConfig();
        tc.setUnimprovedMillisecondsSpentLimit(10000l);


        SolverFactory<VehicleRoutingSolution> solverFactory = SolverFactory.create(new SolverConfig()
                .withSolutionClass(VehicleRoutingSolution.class)
                .withEntityClasses(Vehicle.class)
                .withScoreDirectorFactory(sdc)
                .withTerminationConfig(tc));

        Solver<VehicleRoutingSolution> solver = solverFactory.buildSolver();


        VehicleRoutingSolution bestJourney = solver.solve(problem);
        HardSoftLongScore hss=problem.getScore();
        System.out.println("hs:"+hss.hardScore()+" soft:"+hss.softScore());
        printSolution(bestJourney);

    }

    static void printSolution(VehicleRoutingSolution solution) {
        solution.getVehicleList().forEach(vehicle -> System.out.printf("%s: %s%n", vehicle,
                vehicle.getRoute().stream().map(Location::getId).collect(Collectors.toList())));
    }

    public static void main(String[] args) {
        System.out.println("The best route is:");
        Main unstatic = new Main();
        unstatic.go();


/*
        ScoreDirectorFactoryConfig sdc = new ScoreDirectorFactoryConfig();

        sdc.setEasyScoreCalculatorClass(Optimizer.class);

        TerminationConfig tc = new TerminationConfig();
        tc.setUnimprovedMillisecondsSpentLimit(10000l);

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
*/
    }
}
