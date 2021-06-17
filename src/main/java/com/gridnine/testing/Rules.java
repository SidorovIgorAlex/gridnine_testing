package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public enum Rules {

    RulesDeparture{
        public List<Flight> check(List<Flight> flights) {
            List<Flight> newFlight = new ArrayList<>();

            newFlight = flights.stream()
                    .filter(flight -> {
                        var segments = flight.getSegments().stream()
                                .filter(segment -> segment.getDepartureDate().isAfter(LocalDateTime.now()))
                                .collect(Collectors.toList());
                        return segments.size() == flight.getSegments().size();
                    })
                    .collect(Collectors.toList());

            return newFlight;
        }
    },

    RulesSegment{
        public List<Flight> check(List<Flight> flights) {
            List<Flight> newFlight = new ArrayList<>();
            newFlight = flights.stream()
                    .filter(flight -> {
                        var segments = flight.getSegments().stream()
                                .filter(segment -> segment.getDepartureDate().isBefore(segment.getArrivalDate()))
                                .collect(Collectors.toList());
                        return segments.size() == flight.getSegments().size();
                    })
                    .collect(Collectors.toList());
            return newFlight;
        }
    },

    RulesTimeBetweenSegment {
        public List<Flight> check(List<Flight> flights) {
            List<Flight> newFlight;
            newFlight = flights.stream()
                    .filter(flight -> {
                        long count = 0;
                        List<Segment> segment = flight.getSegments();
                        for (int i = 0; i < segment.size() - 1; i++) {
                            count += ChronoUnit.SECONDS
                                    .between(segment.get(i++).getArrivalDate(), segment.get(i).getDepartureDate());
                        }
                        return count / 3600 <= 2;
                    })
                    .collect(Collectors.toList());
            return newFlight;
        }
    };

    public abstract List<Flight> check(List<Flight> flights);
}
