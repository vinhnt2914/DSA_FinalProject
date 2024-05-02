package com.example.testapi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class POI {
    private int x;
    private int y;
    private List<String> services;
    public POI(int x, int y, String... services) {
        this.x = x;
        this.y = y;
        this.services = new ArrayList<>();
        Collections.addAll(this.services, services);
    }

    public POI(int x, int y, List<String> services) {
        this.x = x;
        this.y = y;
        this.services = services;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "POI{" +
                "x=" + x +
                ", y=" + y +
                ", services=" + services +
                '}';
    }
}
