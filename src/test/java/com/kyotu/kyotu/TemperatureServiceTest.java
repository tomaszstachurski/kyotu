package com.kyotu.kyotu;

import com.kyotu.kyotu.model.AverageTemperature;
import com.kyotu.kyotu.model.City;
import com.kyotu.kyotu.model.TemperatureByYear;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class TemperatureServiceTest {

    TemperatureService service;

    @BeforeEach
    void beforeEach() {
        service = new TemperatureService();
    }

    @Test
    void testGetAverageTemperatures() {
        // given
        City city = new City("warszawa");
        TemperatureByYear temperatureByYear = new TemperatureByYear(2023);
        temperatureByYear.setNumberOfDays(10);
        temperatureByYear.setSumOfTemperatures(123.58);
        city.addTemperatures(temperatureByYear);
        service.addCity(city);
        double expectedTemperature = 12.4;

        // when
        List<AverageTemperature> result = service.getAverageTemperature("warszawa", new File("path"));

        // then
        assertEquals(result.get(0).getAverageTemperature(), expectedTemperature);
    }

}