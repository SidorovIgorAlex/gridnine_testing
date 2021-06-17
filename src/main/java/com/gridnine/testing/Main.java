package com.gridnine.testing;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();

        System.out.println("1 - RulesDeparture");
        System.out.println("2 - RulesSegment");
        System.out.println("3 - RulesTimeBetweenSegment");

        String rules = new Scanner(System.in).nextLine();

        List<Flight> changedFlight = switch (rules) {
            case "1" -> Rules.RulesDeparture.check(flights);
            case "2" -> Rules.RulesSegment.check(flights);
            case "3" -> Rules.RulesTimeBetweenSegment.check(flights);
            default -> throw new IllegalArgumentException("Wrong number rules");
        };

        for (Flight flight : changedFlight) {
            System.out.println(flight);
        }
    }
}
