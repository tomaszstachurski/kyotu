package com.kyotu.kyotu;

import com.kyotu.kyotu.model.AverageTemperature;
import com.kyotu.kyotu.model.City;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.math3.util.Precision;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class TemperatureService {

    Map<String, City> cities = new HashMap<>();

    public List<AverageTemperature> getAverageTemperature(String cityName, File file) {
        processFile(file);

        List<AverageTemperature> averateTemperature = new ArrayList<>();
        if (!cities.containsKey(cityName)) {
            return null;
        } else {
            TreeMap<LocalDate, Double> temperatures = cities.get(cityName).getTemperatures();

            List<List<Map.Entry<LocalDate, Double>>> collect = temperatures.entrySet()
                    .stream()
                    .collect(Collectors
                            .groupingBy(e -> e.getKey().getYear())).values().stream().toList();

            collect.forEach(year -> averateTemperature
                    .add(
                            new AverageTemperature(
                                    year.get(0).getKey().getYear(),
                                    Precision.round(year.stream().mapToDouble(Map.Entry::getValue).average().orElse(Double.NaN), 1))));

        }

        return averateTemperature;
    }

    public void processFile(File file) {
        try {
            Reader reader = new BufferedReader(new FileReader(file));
            CsvToBean<ReadData> csvReader = new CsvToBeanBuilder<ReadData>(reader)
                    .withType(ReadData.class)
                    .withSeparator(';')
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .withThrowExceptions(false)
                    .build();

            List<ReadData> data = csvReader.parse();
            for(ReadData d : data) {
                if (cities.containsKey(d.getCityName())) {
                    City city = cities.get(d.getCityName());
                    city.getTemperatures().put(d.getDate(), d.getTemperature());
                }
                if (!cities.containsKey(d.getCityName())) {
                    City city = new City(d.getCityName());
                    city.getTemperatures().put(d.getDate(), d.getTemperature());
                    cities.put(city.getName(), city);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public City getCity(String cityName) {
        return cities.get(cityName);
    }

}
