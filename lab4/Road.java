package com.transport;

import java.util.ArrayList;
import java.util.List;

public class Road {
    private final List<Vehicle<? extends Human>> carsInRoad = new ArrayList<>();

    public void addCarToRoad(Vehicle<? extends Human> vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Транспортний засіб не може бути null.");
        }
        carsInRoad.add(vehicle);
    }

    public int getCountOfHumans() {
        int count = 0;
        for (Vehicle<? extends Human> vehicle : carsInRoad) {
            count += vehicle.getOccupiedSeats();
        }
        return count;
    }

    public List<Vehicle<? extends Human>> getCarsInRoad() {
        return carsInRoad;
    }
}
