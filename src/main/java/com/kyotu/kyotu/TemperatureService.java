package com.kyotu.kyotu;

import com.kyotu.kyotu.model.AverageTemperature;
import com.kyotu.kyotu.model.City;
import com.kyotu.kyotu.model.TemperatureByYear;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.math3.util.Precision;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class TemperatureService {

    private final Map<String, City> cities = new HashMap<>();

    private long last_modified = 0;

    public List<AverageTemperature> getAverageTemperature(String cityName, File file) {
        if (isFileUpdated(file)) {
            processFile(file);
        }

        List<AverageTemperature> averateTemperatures = new ArrayList<>();

        if (!cities.containsKey(cityName)) {
            return null;
        }

        TreeMap<Integer, TemperatureByYear> temperatures = cities.get(cityName).getTemperatures();

        temperatures.forEach((key, value) -> {
            AverageTemperature averageTemperature = new AverageTemperature(value.getYear(),
                    Precision.round(value.getSumOfTemperatures() / value.getNumberOfDays(), 1));
            averateTemperatures.add(averageTemperature);
        });

        return averateTemperatures;
    }

    public void processFile(File file) {
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

            Iterator<ReadData> iterator = csvReader.iterator();
            while(iterator.hasNext()) {
                ReadData data = iterator.next();

                City city;
                if (cities.containsKey(data.getCityName().toLowerCase())) {
                    city = cities.get(data.getCityName().toLowerCase());
                } else {
                    city = new City(data.getCityName().toLowerCase());
                }

                TemperatureByYear temperature;
                if(city.getTemperatures().containsKey(data.getDate().getYear())) {
                    temperature = city.getTemperatures().get(data.getDate().getYear());
                } else {
                    temperature = new TemperatureByYear(data.getDate().getYear());

                }
                temperature.setNumberOfDays(temperature.getNumberOfDays() + 1);
                temperature.setSumOfTemperatures(temperature.getSumOfTemperatures() + data.getTemperature());
                city.getTemperatures().put(temperature.getYear(), temperature);
                cities.put(city.getName(), city);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public City getCity(String cityName) {
        return cities.get(cityName);
    }

    private boolean isFileUpdated(File file) {
        return file.lastModified() > last_modified;
    }

}
