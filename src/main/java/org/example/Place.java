package org.example;
import java.util.Set;

public class Place {
    private int x; // The X coordinate
    private int y; // The Y coordinate
    private Set<String> services; // The services offered at this place

    public Place(int x, int y, Set<String> services) {
        this.x = x;
        this.y = y;
        this.services = services;
    }

    // Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Set<String> getServices() {
        return services;
    }

    // Optional: toString() for easy printing of Place details
    @Override
    public String toString() {
        return "Place{" +
                "x=" + x +
                ", y=" + y +
                ", services=" + services +
                '}';
    }

    // Additional methods like setters or other business logic could be added here
}
