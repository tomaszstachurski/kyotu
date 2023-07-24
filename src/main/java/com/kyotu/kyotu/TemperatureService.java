package com.kyotu.kyotu;

import com.kyotu.kyotu.model.AverageTemperature;
import com.kyotu.kyotu.model.City;
import com.kyotu.kyotu.model.TemperatureByYear;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.math3.util.Precision;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TemperatureService {

    private final Map<String, City> cities = new HashMap<>();

    private long last_modified = 0;

    public List<AverageTemperature> getAverageTemperature(String cityName, File file) {
        if (isFileUpdated(file)) {
            processFile(file);
        }
        if (!cities.containsKey(cityName)) {
            return null;
        }

        TreeMap<Integer, TemperatureByYear> temperatures = cities.get(cityName).getTemperatures();

        return temperatures.values().stream()
                .map(temp -> new AverageTemperature(temp.getYear(),
                    Precision.round(temp.getSumOfTemperatures() / temp.getNumberOfDays(), 1)))
                .collect(Collectors.toList());
    }

    private boolean isFileUpdated(File file) {
        return file.lastModified() > last_modified;
    }

    private void processFile(File file) {
        try {
            last_modified = file.lastModified();
            cities.clear();

            Reader reader = new BufferedReader(new FileReader(file));
            CsvToBean<ReadData> csvReader = new CsvToBeanBuilder<ReadData>(reader)
                    .withType(ReadData.class)
                    .withSeparator(';')
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .withThrowExceptions(false)
                    .build();

            csvReader.iterator().forEachRemaining(csvRow -> {
                City city = cities.getOrDefault(csvRow.getCityName().toLowerCase(), new City(csvRow.getCityName().toLowerCase()));

                TemperatureByYear temperature = getTemperatureByYear(csvRow, city);
                city.getTemperatures().put(temperature.getYear(), temperature);
                cities.putIfAbsent(city.getName(), city);
            });

        } catch (IOException e) {
            LoggerFactory.getLogger(TemperatureService.class).error("Data not found!", e);
        }
    }

    private TemperatureByYear getTemperatureByYear(ReadData csvRow, City city) {
        TemperatureByYear temperature = city.getTemperatures().getOrDefault(csvRow.getDate().getYear(),
                new TemperatureByYear(csvRow.getDate().getYear()));

        temperature.incrementNumberOfDays();
        temperature.addTemperature(csvRow.getTemperature());
        return temperature;
    }

}
