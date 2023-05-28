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
   https://www.optaplanner.org/docs/optaplanner/latest/use-cases-and-examples/cloud-balancing/cloud-balancing.html

         pklanningsolution: die klasse, die optimierungspotential
         valuerangeprovider hat und deren PlanningEntityCollectionProperty die aktuell zu rechnende lösung bezeichnet
         und die mit EasyScoreCalculator implementiernde klasse berechnet wird#
5) planningsolution muss score cachen können 
6) planningsolution muss auch zustand der valuerangrprovider im zustand haben
7) planningentity muss (planningvariable haben) und auf PlanningEntityCollectionProperty zuornenbar sein
8) planningentity beschreibt die zuordnung was wo verwendet wird , eine art n:m tabelle