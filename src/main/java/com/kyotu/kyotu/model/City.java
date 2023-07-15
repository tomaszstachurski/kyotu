package com.kyotu.kyotu.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.TreeMap;

public class City {

    private String name;

    private TreeMap<LocalDate, Double> temperatures;

    public City (String name) {
        this.name = name;
        temperatures = new TreeMap<>();
    }

    public String getName() {
        return name;
    }

    public TreeMap<LocalDate, Double> getTemperatures() {
        return temperatures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return name.equals(city.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
