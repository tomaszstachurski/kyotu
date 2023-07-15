package com.kyotu.kyotu;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

import java.time.LocalDate;

public class ReadData {

    @CsvBindByPosition(position = 0)
    private String cityName;

    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByPosition(position = 1)
    private LocalDate date;

    @CsvBindByPosition(position = 2)
    private Double temperature;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
}
