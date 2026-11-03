package com.transport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Vehicle<T extends Human> {
    private final int maxSeats;
    private final List<T> passengers;

    public Vehicle(int maxSeats) {
        if (maxSeats <= 0) {
            throw new IllegalArgumentException("Кількість місць повинна бути більшою за 0.");
        }
        this.maxSeats = maxSeats;
        this.passengers = new ArrayList<>();
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public int getOccupiedSeats() {
        return passengers.size();
    }

    public List<T> getPassengers() {
        return Collections.unmodifiableList(passengers);
    }

    // Посадка пасажира
    public void board(T passenger) {
        if (passenger == null) {
            throw new IllegalArgumentException("Неможливо посадити null-об'єкт.");
        }
        if (passengers.size() >= maxSeats) {
            throw new IllegalStateException(
                    "Усі місця (" + maxSeats + ") зайняті у ТЗ: " + getClass().getSimpleName()
            );
        }
        passengers.add(passenger);
    }

    // Висадка пасажира
    public void unboard(T passenger) {
        if (!passengers.contains(passenger)) {
            throw new IllegalStateException(
                    "Пасажир " + passenger.getName() + " відсутній у ТЗ: " + getClass().getSimpleName()
            );
        }
        passengers.remove(passenger);
    }
}
