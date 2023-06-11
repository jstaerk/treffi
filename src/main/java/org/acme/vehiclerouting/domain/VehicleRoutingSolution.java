package org.acme.vehiclerouting.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.acme.vehiclerouting.bootstrap.DemoDataBuilder;
import org.acme.vehiclerouting.domain.geo.DistanceCalculator;
import org.acme.vehiclerouting.domain.geo.EuclideanDistanceCalculator;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;

@PlanningSolution
public class VehicleRoutingSolution {

    private String name;

    @ProblemFactCollectionProperty
    private List<Location> locationList;

    @ProblemFactCollectionProperty
    private List<Depot> depotList;

    @PlanningEntityCollectionProperty
    private List<Vehicle> vehicleList;

    @ProblemFactCollectionProperty
    @ValueRangeProvider
    private List<Customer> customerList;

    @PlanningScore
    private HardSoftLongScore score;

    private Location southWestCorner;
    private Location northEastCorner;

    public VehicleRoutingSolution() {
    }

    public VehicleRoutingSolution(String name,
            List<Location> locationList, List<Depot> depotList, List<Vehicle> vehicleList, List<Customer> customerList,
            Location southWestCorner, Location northEastCorner) {
        this.name = name;
        this.locationList = locationList;
        this.depotList = depotList;
        this.vehicleList = vehicleList;
        this.customerList = customerList;
        this.southWestCorner = southWestCorner;
        this.northEastCorner = northEastCorner;
    }

    public static VehicleRoutingSolution empty() {
        VehicleRoutingSolution problem = DemoDataBuilder.builder().setMinDemand(1).setMaxDemand(2)
                .setVehicleCapacity(77).setCustomerCount(77).setVehicleCount(7).setDepotCount(1)
                .setSouthWestCorner(new Location(0L, 51.44, -0.16))
                .setNorthEastCorner(new Location(0L, 51.56, -0.01)).build();

        problem.setScore(HardSoftLongScore.ZERO);

        return problem;
    }

    public static VehicleRoutingSolution predef() {

        List<Location> locationList = new ArrayList<Location>(List.of(new Location(3, 2, 3),
                new Location(4, 3, 4),new Location(1, 1.0d, 2.0d ),new Location(2, 2.0, 3.0 ),
                new Location(4, 3, 4)
        ));

        List<Depot> depotList =  new ArrayList<Depot>(List.of(new Depot(1,locationList.get(0)),
                new Depot(2,locationList.get(1))));

        int cap=10;
        List<Vehicle> vehicleList =  new ArrayList<Vehicle>(List.of(new Vehicle(1,cap,depotList.get(0)),
                new Vehicle(2,cap,depotList.get(1))));

        int dem=1;
        List<Customer> customerList = new ArrayList<Customer>(List.of(new Customer(1,locationList.get(2),dem),
                new Customer(2,locationList.get(3),dem)));
        DistanceCalculator distanceCalculator = new EuclideanDistanceCalculator();


        distanceCalculator.initDistanceMaps(locationList);

        Location southWestCorner=new Location(98,999.0,999.0);
        Location  northEastCorner=new Location(99,0.0,0.0);
        for (Location currentLocation:locationList) {
            if (currentLocation.getLatitude()>northEastCorner.getLatitude()) {
                northEastCorner.setLatitude(currentLocation.getLatitude());
            }
            if (currentLocation.getLatitude()>northEastCorner.getLongitude()) {
                northEastCorner.setLongitude(currentLocation.getLongitude());
            }

            if (currentLocation.getLatitude()<southWestCorner.getLatitude()) {
                southWestCorner.setLatitude(currentLocation.getLatitude());
            }
            if (currentLocation.getLatitude()<southWestCorner.getLongitude()) {
                southWestCorner.setLongitude(currentLocation.getLongitude());
            }

        }

        return new VehicleRoutingSolution("Predef", locationList,
                depotList, vehicleList, customerList, southWestCorner, northEastCorner);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    public List<Depot> getDepotList() {
        return depotList;
    }

    public void setDepotList(List<Depot> depotList) {
        this.depotList = depotList;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public HardSoftLongScore getScore() {
        return score;
    }

    public void setScore(HardSoftLongScore score) {
        this.score = score;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    public List<Location> getBounds() {
        return Arrays.asList(southWestCorner, northEastCorner);
    }

    public long getDistanceMeters() {
        return score == null ? 0 : -score.softScore();
    }
}
