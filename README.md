# treffi
treffi for trufi

when building in intellij run with --info then --stacktrace and then look at to
> Process 'command 'C:/Program Files/Zulu/zulu-11/bin/java.exe'' finished with non-zero exit value 1
> 
> Exception in thread "main" java.lang.IllegalArgumentException: The entityClass (class Connection) has a @PlanningVariable property (usedIn) with a valueRangeProviderRef (namedRange) that does not exist in a @ValueRangeProvider on the solution class (Itinerary) or on that entityClass.


1) write classes
2) write optimizer
3) write solution @planningsolution with a @planningscore, a @planningvariable and PlanningEntityCollectionProperty 
4) add planninngvariables with valuerangeproviders
 which conn is used where 