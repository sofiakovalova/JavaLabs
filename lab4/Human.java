package com.transport;

public abstract class Human {
    private final String name;

    public Human(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Ім'я пасажира не може бути порожнім.");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{name='" + name + "'}";
    }
}
