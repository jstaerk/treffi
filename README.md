# treffi

A friend co-authored a public transportation planner 
called trufi to get from place A to place B.

This is a proof of concept if it is possible
to find optimized meeting points and itiniaries
between two *persons* in public transport, 
in case it does not matter where they meet.

To meet is "treffen" in german this is why the project title is 
treffi for trufi.

Trufi uses OpenTripPlanner, another project, 
Openrouteservice, uses vroom. However, since I did 
another optimization project (https://github.com/jstaerk/testessenplanner) 
with a library called,
optaplanner which can also optimize vehicle routing (e.g. 
https://www.optaplanner.org/learn/useCases/vehicleRoutingProblem.html).

For this proof of concept I'm going for that. 
Actually this is self written and only inspired by the vehicle routing problem example.

Fundamental idea is we have integers with IDs of the stations,
and integers (e.g. the number of seconds), to indicate time.

Then we have a small schedule when which station is connected 
with which other station and how long that connection takes.

First part, and so far everything that's implemented: 
Find the shortest route between a start and a end point.

So I

1) wrote the classes (e.g. Timetale.java stores when which train...)
2) wrote the optimizer, i.e. the score calculator, 
which has a hard score (if the route does not include the destination)
and a soft score, the duration of the journey (multiplied by minus one 
so that a shorter journey gives a better solution).
3) I then wrote the @planningsolution, the Itinerary with a @planningscore, a @planningvariable and PlanningEntityCollectionProperty 
4) I added planningvariables with valuerangeproviders
5) The planningsolution can cache the score 
6) the planningsolution als has the state of valuerangeprovider in its own state
7) the planningentity has (planningvariables) and is assignable to PlanningEntityCollectionProperty 
8) the planningentity is a link what is optimized where, a kind of a n:m table

Since working with annotations some builds failed with sth like
> Process 'command 'C:/Program Files/Zulu/zulu-11/bin/java.exe'' finished with non-zero exit value 1
>
> Exception in thread "main" java.lang.IllegalArgumentException: The entityClass (class Connection) has a @PlanningVariable property (usedIn) with a valueRangeProviderRef (namedRange) that does not exist in a @ValueRangeProvider on the solution class (Itinerary) or on that entityClass.

and when building in intellij I occasionally found it useful tp 
run with --info then --stacktrace and then look at to.

Will this work with bigger and more complicated data sets?
Do I need generators like they used in the vehicle routing problem?
