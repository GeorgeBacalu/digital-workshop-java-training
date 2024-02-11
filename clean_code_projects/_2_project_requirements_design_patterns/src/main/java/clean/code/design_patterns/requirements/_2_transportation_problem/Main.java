package clean.code.design_patterns.requirements._2_transportation_problem;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * An instance of the Transportation Problem consists of sources and destinations.
 * Each source has a given capacity, i.e. how many units of a commodity it is able to supplies to the destinations, while each destination demands a certain amount of commodities.
 * The cost of transporting a unit of commodity from each source to each destination is given by a cost matrix (or function).
 * We consider the problem of determining the quantities to be transported from sources to destinations, in order to minimize the total transportation cost.
 * The supplies and demands constraints must be satisfied. Consider the following example:
 * ******** D1  D2  D3  Supply
 * S1        2   3   1    10
 * S2        5   4   8    35
 * S3        5   6   8    25
 * Demand   20  25  25
 * A solution may be look like this:
 * S1 -> D3: 10 units * cost 1 = 10
 * S2 -> D2: 25 units * cost 4 = 100
 * S2 -> D3: 10 * 8 = 80
 * S3 -> D1: 20 * 5 = 100
 * S3 -> D3: 5 * 8 = 40
 * Total cost: 330
 */

@Slf4j
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Source> sources = new ArrayList<>();
    private static final List<Destination> destinations = new ArrayList<>();

    public static void main(String[] args) {
        try {
            System.out.print("Enter number of sources: ");
            Integer nrSources = scanner.nextInt();
            System.out.print("Enter number of destinations: ");
            Integer nrDestinations = scanner.nextInt();
            initializeSourcesAndDestinations(nrSources, nrDestinations);
            List<Integer> supplies = sources.stream().map(Source::getSupply).collect(Collectors.toList());
            List<Integer> demands = destinations.stream().map(Destination::getDemand).collect(Collectors.toList());
            List<List<Integer>> costs = readCosts(nrSources, nrDestinations);
            List<List<Integer>> sortedCosts = computeSortedCosts(costs);
            List<List<Integer>> sortedIndexes = computeSortedIndexes(costs, sortedCosts);
            log.info("Total cost: {}", computeTotalCost(costs, sortedIndexes, supplies, demands));
        } catch (Exception exception) {
            log.error("Unexpected error occurred: {}", exception.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void initializeSourcesAndDestinations(Integer nrSources, Integer nrDestinations) {
        int totalSupply = 0, totalDemand = 0;
        for (int i = 0; i < nrSources; ++i) {
            computeLocation(LocationType.SOURCE, i);
            totalSupply += sources.get(i).getSupply();
        }
        for (int i = 0; i < nrDestinations; ++i) {
            computeLocation(LocationType.DESTINATION, i);
            totalDemand += destinations.get(i).getDemand();
        }
        balanceSupplyAndDemand(totalSupply, totalDemand, nrSources, nrDestinations);
    }

    private static void computeLocation(LocationType locationType, Integer i) {
        String location = locationType.name().toLowerCase();
        scanner.nextLine();
        System.out.print("Enter " + location + " " + (i + 1) + ":\nName: ");
        String name = scanner.nextLine();
        while (isDuplicatedName(locationType, name)) {
            System.out.print("This " + location + " already exists. Enter another name: ");
            name = scanner.nextLine();
        }
        System.out.print(locationType.equals(LocationType.SOURCE) ? "Supply: " : "Demand: ");
        Integer supplyOrDemand = scanner.nextInt();
        addLocation(locationType, name, supplyOrDemand);
    }

    private static boolean isDuplicatedName(LocationType locationType, String name) {
        return locationType.equals(LocationType.SOURCE) ?
                sources.stream().anyMatch(source -> source.getName().equalsIgnoreCase(name)) :
                destinations.stream().anyMatch(destination -> destination.getName().equalsIgnoreCase(name));
    }

    private static void addLocation(LocationType locationType, String name, Integer supplyOrDemand) {
        String location = locationType.name().toLowerCase();
        int type;
        do {
            System.out.print("Enter the type of " + location + " to add to the list (1 - Warehouse, 2 - Factory): ");
            type = scanner.nextInt();
        } while (type != 1 && type != 2);
        if (locationType.equals(LocationType.SOURCE)) {
            sources.add(type == 1 ? new WarehouseSource(name, supplyOrDemand) : new FactorySource(name, supplyOrDemand));
        } else {
            destinations.add(type == 1 ? new WarehouseDestination(name, supplyOrDemand) : new FactoryDestination(name, supplyOrDemand));
        }
    }

    private static void balanceSupplyAndDemand(Integer totalSupply, Integer totalDemand, Integer nrSources, Integer nrDestinations) {
        if (totalSupply > totalDemand) {
            destinations.add(new WarehouseDestination("D" + (nrDestinations + 1), totalSupply - totalDemand));
        } else if (totalSupply < totalDemand) {
            sources.add(new WarehouseSource("S" + (nrSources + 1), totalDemand - totalSupply));
        }
    }

    private static List<List<Integer>> readCosts(Integer nrSources, Integer nrDestinations) {
        List<List<Integer>> costs = new ArrayList<>();
        System.out.println("Enter costs matrix: ");
        for (int i = 0; i < nrSources; ++i) {
            costs.add(new ArrayList<>());
            for (int j = 0; j < nrDestinations; ++j) {
                System.out.print("cost[" + i + "][" + j + "]: ");
                costs.get(i).add(scanner.nextInt());
            }
        }
        return costs;
    }

    private static List<List<Integer>> computeSortedCosts(List<List<Integer>> costs) {
        return costs.stream().map(ArrayList::new).peek(Collections::sort).collect(Collectors.toList());
    }

    private static List<List<Integer>> computeSortedIndexes(List<List<Integer>> costs, List<List<Integer>> sortedCosts) {
        List<List<Integer>> sortedIndexes = new ArrayList<>();
        for (int i = 0; i < costs.size(); ++i) {
            List<Integer> indexes = new ArrayList<>();
            for (Integer cost : sortedCosts.get(i)) {
                indexes.add(costs.get(i).indexOf(cost));
            }
            sortedIndexes.add(indexes);
        }
        return sortedIndexes;
    }

    private static Integer computeTotalCost(List<List<Integer>> costs, List<List<Integer>> sortedIndexes, List<Integer> supplies, List<Integer> demands) {
        int totalCost = 0;
        for (int i = 0; i < supplies.size(); ++i) {
            for (int j = 0; j < demands.size() && supplies.get(i) > 0; ++j) {
                int currentSupply = supplies.get(i), currentDemand = demands.get(sortedIndexes.get(i).get(j));
                if (currentDemand > 0) {
                    log.info("supplies[{}] = {}, demands[{}] = {}", i, currentSupply, sortedIndexes.get(i).get(j), currentDemand);
                    int currentCost = costs.get(i).get(sortedIndexes.get(i).get(j)), minSupplyDemand = Math.min(currentSupply, currentDemand), cost = minSupplyDemand * currentCost;
                    log.info("{} -> {}: {} * {} = {}", sources.get(i).getName(), destinations.get(sortedIndexes.get(i).get(j)).getName(), minSupplyDemand, currentCost, cost);
                    log.info("supplies[{}] = {}, demands[{}] = {}", i, currentSupply, sortedIndexes.get(i).get(j), currentDemand);
                    supplies.set(i, currentSupply - minSupplyDemand);
                    demands.set(sortedIndexes.get(i).get(j), currentDemand - minSupplyDemand);
                    totalCost += cost;
                }
            }
        }
        return totalCost;
    }
}
