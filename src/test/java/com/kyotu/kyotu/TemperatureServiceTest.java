package com.kyotu.kyotu;

import com.kyotu.kyotu.model.AverageTemperature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TemperatureServiceTest {

    private TemperatureService service;

    private File file;

    @BeforeEach
    void beforeEach() {
        service = new TemperatureService();
        file = new File("X:\\workspace\\kyotu\\src\\test\\resources\\testdata.csv");
    }

    @Test
    void testGetAverageTemperatures() {
        // given
        int expectedYear = 2023;
        double expectedTemperature = 21.5;

        // when
        List<AverageTemperature> result = service.getAverageTemperature("warszawa", file);

        // then
        assertEquals(1, result.size());
        assertEquals(result.get(0).getYear(), expectedYear);
        assertEquals(result.get(0).getAverageTemperature(), expectedTemperature);
    }

}