package com.kyotu.kyotu.model;

public class TemperatureByYear {

    private final int year;

    private int numberOfDays;

    private double sumOfTemperatures;

    public TemperatureByYear(int year) {
        this.year = year;
        this.numberOfDays = 0;
        this.sumOfTemperatures = 0;
    }

    public int getYear() {
        return year;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public double getSumOfTemperatures() {
        return sumOfTemperatures;
    }

    public void setSumOfTemperatures(double sumOfTemperatures) {
        this.sumOfTemperatures = sumOfTemperatures;
    }

    public void incrementNumberOfDays() {
        this.numberOfDays++;
    }

    public void addTemperature(double temperature) {
        this.sumOfTemperatures += temperature;
    }
}
