package com.transport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleTest {

    private Bus bus;
    private PoliceCar policeCar;
    private FireEngine fireEngine;

    private Passenger passenger;
    private PoliceOfficer officer;
    private Firefighter firefighter;

    @BeforeEach
    void setUp() {
        bus = new Bus(2);
        policeCar = new PoliceCar(1);
        fireEngine = new FireEngine(1);

        passenger = new Passenger("Іван");
        officer = new PoliceOfficer("Петро");
        firefighter = new Firefighter("Сергій");
    }

    @Test
    @DisplayName("Перевірка успішної посадки та висадки пасажирів")
    void testBoardingAndUnboarding() {
        bus.board(passenger);
        assertEquals(1, bus.getOccupiedSeats());

        bus.board(officer);
        assertEquals(2, bus.getOccupiedSeats());

        bus.unboard(passenger);
        assertEquals(1, bus.getOccupiedSeats());
    }

    @Test
    @DisplayName("Виняток при перевищенні кількості місць у ТЗ")
    void testOvercapacityException() {
        policeCar.board(officer);
        assertEquals(1, policeCar.getOccupiedSeats());

        PoliceOfficer officer2 = new PoliceOfficer("Олексій");
        assertThrows(IllegalStateException.class, () -> policeCar.board(officer2));
    }

    @Test
    @DisplayName("Виняток при спробі висадити пасажира, якого немає у ТЗ")
    void testUnboardNonExistentPassengerException() {
        assertThrows(IllegalStateException.class, () -> bus.unboard(passenger));
    }

    @Test
    @DisplayName("Перевірка роботи підрахунку людей на дорозі (Road)")
    void testRoadHumanCounting() {
        bus.board(passenger);
        fireEngine.board(firefighter);

        Road road = new Road();
        road.addCarToRoad(bus);
        road.addCarToRoad(fireEngine);

        assertEquals(2, road.getCountOfHumans());
    }
}
