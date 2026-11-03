package com.transport;

public class Main {
    public static void main(String[] args) {
        // створення пасажирів
        Passenger passenger = new Passenger("Олена");
        PoliceOfficer officer1 = new PoliceOfficer("Максим");
        PoliceOfficer officer2 = new PoliceOfficer("Андрій");
        Firefighter firefighter = new Firefighter("Тарас");

        // створення транспортних засобів
        Bus bus = new Bus(2);
        Taxi taxi = new Taxi(3);
        PoliceCar policeCar = new PoliceCar(2);
        FireEngine fireEngine = new FireEngine(2);

        // посадка у загальний транспорт
        bus.board(passenger);
        bus.board(officer1);
        System.out.println("Автобус заповнено. Зайнято місць: " + bus.getOccupiedSeats() + "/" + bus.getMaxSeats());

        taxi.board(firefighter);

        // посадка у спеціалізований транспорт
        policeCar.board(officer1);
        policeCar.board(officer2);
        fireEngine.board(firefighter);

        // переповнення
        try {
            bus.board(firefighter);
        } catch (IllegalStateException e) {
            System.out.println("Переповнення: " + e.getMessage());
        }

        // відсутність пасажира під час висадки
        try {
            Passenger unknownPassenger = new Passenger("Олег");
            bus.unboard(unknownPassenger);
        } catch (IllegalStateException e) {
            System.out.println("Висадка ппасажира, якого не було: " + e.getMessage());
        }

        // висадка пасажира
        bus.unboard(passenger);
        System.out.println("Після висадки Олени в автобусі місць: " + bus.getOccupiedSeats());

        // підрахунок людей на дорозі
        Road road = new Road();
        road.addCarToRoad(bus);
        road.addCarToRoad(taxi);
        road.addCarToRoad(policeCar);
        road.addCarToRoad(fireEngine);

        System.out.println("\n");
        System.out.println("Загальна кількість машин на дорозі: " + road.getCarsInRoad().size());
        System.out.println("Загальна кількість пасажирів у всіх авто: " + road.getCountOfHumans());
    }
}
