package com.kyotu.kyotu;

import com.kyotu.kyotu.model.AverageTemperature;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class TemperaturesController {

    String RESOURCE = "data/example_file.csv";

    @Autowired
    TemperatureService temperatureService;

    @GetMapping("/temperatures/{cityName}")
    ResponseEntity getAverageTemperatures(@PathVariable String cityName) {
        URL resource = getClass().getClassLoader().getResource(RESOURCE);
        try {
            File file = Paths.get(resource.toURI()).toFile();
            List<AverageTemperature> averageTemperatures = temperatureService.getAverageTemperature(StringUtils.capitalize(cityName), file);
            return averageTemperatures != null ? ResponseEntity.ok(averageTemperatures) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("City not found!");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found!");
        }
    }
}
