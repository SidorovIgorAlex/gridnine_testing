package com.gridnine.testing;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Tests {
    private static List<Flight> flightList;

    @BeforeClass
    public static void beforeTestMethod() {
        flightList = FlightBuilder.createFlights();
        System.out.println("Starting JUnit Test");
    }

    @AfterClass
    public static void afterTestMethod() {
        System.out.println("End JUnit Test");
    }

    @Test
    public void whenWeChoiceRulesDeparture() {
        var newFlight = Rules.RulesDeparture.check(flightList);
        for (int i = 0; i < newFlight.size(); i++) {
            var segments = newFlight.get(i).getSegments();
            for (int j = 0; j < segments.size(); j++) {
                var segment = segments.get(j);
                var departure = segment.getDepartureDate();
                Assertions.assertTrue(departure.isAfter(LocalDateTime.now()));
            }
        }
    }

    @Test
    public void whenWeChoiceRulesSegment() {
        var newFlight = Rules.RulesSegment.check(flightList);
        for (int i = 0; i < newFlight.size(); i++) {
            var segments = newFlight.get(i).getSegments();
            for (int j = 0; j < segments.size(); j++) {
                var segment = segments.get(j);
                var departure = segment.getDepartureDate();
                var arrival = segment.getArrivalDate();
                Assertions.assertTrue(departure.isBefore(arrival));
            }
        }
    }

    @Test
    public void whenWeChoiceRulesTimeBetweenSegment() {
        var newFlight = Rules.RulesTimeBetweenSegment.check(flightList);
        for (int i = 0; i < newFlight.size(); i++) {
            long fullTime = 0;
            var segments = newFlight.get(i).getSegments();
            for (int j = 0; j < segments.size() - 1; j++) {
                var departure = segments.get(j++).getArrivalDate();
                var arrival = segments.get(j).getDepartureDate();
                fullTime += ChronoUnit.SECONDS.between(departure, arrival);
            }
            Assertions.assertTrue(fullTime / 3600 <= 2);
        }
    }
}
