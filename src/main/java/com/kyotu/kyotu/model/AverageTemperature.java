package com.kyotu.kyotu.model;

public class AverageTemperature {

    private int year;

    private double averageTemperature;

    public AverageTemperature(int year, double averageTemperature) {
        this.year = year;
        this.averageTemperature = averageTemperature;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getAverageTemperature() {
        return averageTemperature;
    }

    public void setAverageTemperature(double averageTemperature) {
        this.averageTemperature = averageTemperature;
    }
}
