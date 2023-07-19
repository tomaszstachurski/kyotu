package com.kyotu.kyotu;

import com.kyotu.kyotu.model.AverageTemperature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class TemperaturesControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    TemperatureService temperatureService;

    @Test
    void testGetCorrectTemperatures() throws Exception {
        // given
        AverageTemperature averageTemperature = new AverageTemperature(2023, 15.15);
        List<AverageTemperature> averageTemperatures = List.of(averageTemperature);
        when(temperatureService.getAverageTemperature(any(), any())).thenReturn(averageTemperatures);

        // when then
        mvc.perform(MockMvcRequestBuilders
                        .get("/temperatures/" + "city"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));

    }

    @Test
    void testNoCityFound() throws Exception {
        // given
        when(temperatureService.getAverageTemperature(any(), any())).thenReturn(null);

        // when then
        mvc.perform(MockMvcRequestBuilders
                        .get("/temperatures/" + "city"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("City not found!"));

    }

}