package src.main;

import java.util.HashSet;
import java.util.Set;

public class Place {
    private int id;
    private int x;
    private int y;
    private Set<String> services;

    public Place(int id, int x, int y, Set<String> services) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.services = new HashSet<>(services);
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Set<String> getServices() {
        return new HashSet<>(services);
    }

    public void setServices(Set<String> services) {
        this.services = new HashSet<>(services);
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", services=" + services +
                '}';
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, x, y, services);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return id == place.id && x == place.x && y == place.y && services.equals(place.services);
    }
}