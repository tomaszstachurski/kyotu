package com.kyotu.kyotu.model;

import java.util.Objects;
import java.util.TreeMap;

public class City {

    private final String name;

    private final TreeMap<Integer, TemperatureByYear> temperatures;

    public City(String name) {
        this.name = name;
        this.temperatures = new TreeMap<>();
    }

    public String getName() {
        return name;
    }

    public TreeMap<Integer, TemperatureByYear> getTemperatures() {
        return temperatures;
    }

    public void addTemperatures(TemperatureByYear temperature) {
        this.temperatures.put(temperature.getYear(), temperature);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return name.equals(city.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
